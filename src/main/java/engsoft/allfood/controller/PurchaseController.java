package engsoft.allfood.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.ClientDAO;
import engsoft.allfood.dao.ProductDAO;
import engsoft.allfood.dao.ProductHistoryDAO;
import engsoft.allfood.dao.PurchaseDAO;
import engsoft.allfood.dao.PurchaseItemDAO;
import engsoft.allfood.dao.RevenueHistoryDAO;
import engsoft.allfood.enums.PurchaseStatus;
import engsoft.allfood.model.Client;
import engsoft.allfood.model.Product;
import engsoft.allfood.model.ProductHistory;
import engsoft.allfood.model.Purchase;
import engsoft.allfood.model.PurchaseItem;
import engsoft.allfood.model.RevenueHistory;
import engsoft.allfood.util.EnumUtils;
import engsoft.allfood.util.PurchaseListTemplate;
import engsoft.allfood.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	private ClientDAO cDao = new ClientDAO();
	private PurchaseDAO pDao = new PurchaseDAO();
	private ProductDAO prDao = new ProductDAO();
	private ProductHistoryDAO phDao = new ProductHistoryDAO();
	private PurchaseItemDAO piDao = new PurchaseItemDAO();
	private RevenueHistoryDAO rhDao = new RevenueHistoryDAO();

	@RequestMapping("/insert")
	public ReturnMessage insert(@RequestParam(value = "clientId", defaultValue = "-1") long clientId,
			@RequestParam(value = "deliveryType") int deliveryType) {
		if (clientId > 0) {
			Client client = cDao.getById(clientId);
			boolean ableToInsert = pDao.ableToCreatePurchase(clientId);
			if (ableToInsert) {
				Purchase purchase = new Purchase(client, EnumUtils.getDeliveryType(deliveryType));
				purchase.setStatus(PurchaseStatus.AT_CART);
				purchase.setDate(new Date());
				pDao.save(purchase);
				return new ReturnMessage(true, purchase);
			} else
				return new ReturnMessage(false, "Purchase with status IN_CART already exists");
		} else {
			Purchase purchase = new Purchase(EnumUtils.getDeliveryType(deliveryType));
			purchase.setStatus(PurchaseStatus.AT_CART);
			purchase.setDate(new Date());
			pDao.save(purchase);
			return new ReturnMessage(true, purchase);
		}
	}

	@RequestMapping("/setClient")
	public Purchase setClientToPurchase(@RequestParam(value = "purchaseId") long purchaseId,
			@RequestParam(value = "clientId") long clientId) {
		Purchase purchase = pDao.getById(purchaseId);
		Client client = cDao.getById(clientId);
		purchase.setClient(client);
		pDao.update(purchase);
		return purchase;
	}

	@RequestMapping("/processpurchase")
	public ReturnMessage processPurchase(@RequestParam(value = "purchaseId") long purchaseId,
			@RequestParam(value = "deliveryType", defaultValue = "") Integer deliveryType) {
		Purchase purchase = pDao.getById(purchaseId);
		if (purchase.getClient() == null)
			return new ReturnMessage(false, "Purchase cant be processes without client");
		if (!purchase.getStatus().equals(PurchaseStatus.AT_CART))
			return new ReturnMessage(false, "Purchase with status " + purchase.getStatus() + ": cannot be processed");
		List<PurchaseItem> itens = piDao.getItemsByPurchase(purchaseId);
		if (itens.isEmpty())
			return new ReturnMessage(false, "Purchase has 0 purchase itens");
		if (deliveryType != null && !deliveryType.toString().trim().equals(""))
			purchase.setDeliveryType(EnumUtils.getDeliveryType(deliveryType));
		boolean validPurchase = true;
		Product failedProduct = null;
		for (PurchaseItem purchaseItem : itens) {
			Product product = prDao.getById(purchaseItem.getProduct().getId());
			if (product.getQuantityInStock() < purchaseItem.getQuantity()) {
				validPurchase = false;
				failedProduct = product;
				break;
			}
		}
		if (validPurchase) {
			purchase.setStatus(PurchaseStatus.WAITING_FOR_PAYMENT);
			pDao.update(purchase);
			for (PurchaseItem purchaseItem : itens) {
				Product product = prDao.getById(purchaseItem.getProduct().getId());
				product.setQuantityInStock(product.getQuantityInStock() - purchaseItem.getQuantity());
				prDao.update(product);
				ProductHistory pHistory = phDao.getHistoryByProductId(product.getId());
				pHistory.setPendingQuantity(pHistory.getPendingQuantity() + purchaseItem.getQuantity());
				phDao.updateHistory(pHistory);
			}
			return new ReturnMessage(true, "Purchase is waiting for payment");
		} else {
			return new ReturnMessage(false, "Not enough stock for product " + failedProduct.getName());
		}
	}

	@RequestMapping("/finishpurchase")
	public ReturnMessage finish(@RequestParam(value = "purchaseId") long purchaseId) {
		Purchase purchase = pDao.getById(purchaseId);
		if (!purchase.getStatus().equals(PurchaseStatus.WAITING_FOR_PAYMENT))
			return new ReturnMessage(false, "Purchase with status " + purchase.getStatus() + ": cannot be finished");
		List<PurchaseItem> itens = piDao.getItemsByPurchase(purchaseId);
		if (itens.isEmpty())
			return new ReturnMessage(false, "Purchase has 0 purchase itens");
		purchase.setStatus(PurchaseStatus.FINISHED);
		pDao.update(purchase);
		for (PurchaseItem purchaseItem : itens) {
			Product product = prDao.getById(purchaseItem.getProduct().getId());
			ProductHistory pHistory = phDao.getHistoryByProductId(product.getId());
			pHistory.setPendingQuantity(pHistory.getPendingQuantity() - purchaseItem.getQuantity());
			pHistory.setTotalSold(pHistory.getTotalSold() + purchaseItem.getQuantity());
			phDao.updateHistory(pHistory);
		}

		// atualiza o histórico de faturamento
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String date = df.format(purchase.getDate());
		RevenueHistory revenue = rhDao.getByDate(date);
		revenue.setRevenue(revenue.getRevenue() + purchase.totalPrice());
		rhDao.update(revenue);

		return new ReturnMessage(true, "Purchase completed");
	}

	@RequestMapping("/cancelpurchase")
	public ReturnMessage cancel(@RequestParam(value = "purchaseId") long purchaseId) {
		Purchase purchase = pDao.getById(purchaseId);
		if (purchase.getStatus().equals(PurchaseStatus.FINISHED))
			return new ReturnMessage(false, "Purchase with status " + purchase.getStatus() + ": cannot be canceled");
		List<PurchaseItem> itens = piDao.getItemsByPurchase(purchaseId);
		for (PurchaseItem purchaseItem : itens) {
			if (purchase.getStatus().equals(PurchaseStatus.WAITING_FOR_PAYMENT)) {
				Product product = prDao.getById(purchaseItem.getProduct().getId());
				product.setQuantityInStock(product.getQuantityInStock() + purchaseItem.getQuantity());
				prDao.update(product);
				ProductHistory pHistory = phDao.getHistoryByProductId(product.getId());
				pHistory.setPendingQuantity(pHistory.getPendingQuantity() - purchaseItem.getQuantity());
				phDao.updateHistory(pHistory);
			}
		}
		purchase.setStatus(PurchaseStatus.CANCELED);
		pDao.update(purchase);
		return new ReturnMessage(true, "Purchase canceled");
	}

	@RequestMapping("/getitemsbypurchase")
	public List<PurchaseItem> getPurchaseItens(@RequestParam(value = "purchaseId") long purchaseId) {
		return piDao.getItemsByPurchase(purchaseId);
	}

	@RequestMapping("/getByClientId")
	public List<Purchase> getPurchaseByClientId(@RequestParam(value = "clientId") long clientId) {
		List<Purchase> purchases = pDao.getByClientId(clientId);
		return purchases;
	}

	@RequestMapping("/delete")
	public ReturnMessage delete(@RequestParam(value = "id") long id) {
		Purchase purchase = pDao.getById(id);
		if (purchase != null) {
			if (purchase.getStatus() != PurchaseStatus.AT_CART)
				return new ReturnMessage(false, "Cannot delete purchase with status " + purchase.getStatus());
			else {
				int numOfItens = 0;
				if (purchase.getItems() != null) {
					numOfItens = purchase.getItems().size();
					for (PurchaseItem item : purchase.getItems())
						piDao.delete(item);
				}
				pDao.delete(purchase);
				return new ReturnMessage(true, "Purchase and " + numOfItens + " itens deleted");
			}
		} else
			return new ReturnMessage(false, "There is no purchase with id " + id);
	}

	@RequestMapping("/getall")
	public List<PurchaseListTemplate> getAll() {
		List<Purchase> purchases = pDao.getAll();
		List<PurchaseListTemplate> list = new ArrayList<>();
		for (Purchase purchase : purchases) {
			if (purchase.getStatus() != PurchaseStatus.AT_CART) {
				if (purchase.getClient() != null)
					purchase.getClient().setAddress(null);
				list.add(new PurchaseListTemplate(purchase));
			}
		}
		return list;
	}

	@RequestMapping("/alreadyBought")
	public boolean alreadyBought(@RequestParam(value = "productId") long productId,
			@RequestParam(value = "clientId") long clientId) {
		List<Purchase> purchases = pDao.getByClientId(clientId);
		boolean alreadyBought = false;
		if (purchases != null & !purchases.isEmpty())
			for (Purchase purchase : purchases)
				if (purchase.getItems() != null & !purchase.getItems().isEmpty())
					for (PurchaseItem item : purchase.getItems())
						if (item.getProduct().getId() == productId && item.getPrice() > 0.0f
								&& purchase.getStatus() == PurchaseStatus.FINISHED)
							alreadyBought = true;
		return alreadyBought;
	}

}

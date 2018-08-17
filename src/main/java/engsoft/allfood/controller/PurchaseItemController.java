package engsoft.allfood.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.ProductDAO;
import engsoft.allfood.dao.PurchaseDAO;
import engsoft.allfood.dao.PurchaseItemDAO;
import engsoft.allfood.enums.PurchaseStatus;
import engsoft.allfood.model.Product;
import engsoft.allfood.model.Purchase;
import engsoft.allfood.model.PurchaseItem;
import engsoft.allfood.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/purchaseitem")
public class PurchaseItemController {

	private ProductDAO pDao = new ProductDAO();
	private PurchaseDAO pcDao = new PurchaseDAO();
	private PurchaseItemDAO piDao = new PurchaseItemDAO();

	@RequestMapping("/insert")
	public ReturnMessage insert(@RequestParam(value = "productId") long productId,
			@RequestParam(value = "quantity") float quantity, @RequestParam(value = "purchaseId") long purchaseId) {
		Product product = pDao.getById(productId);
		Purchase purchase = pcDao.getById(purchaseId);
		if (purchase.getStatus().equals(PurchaseStatus.AT_CART)) {
			PurchaseItem purchaseItem = new PurchaseItem(product, quantity, purchase);
			purchaseItem.setPrice(product.getPrice() * quantity);
			piDao.save(purchaseItem);
			return new ReturnMessage(true, purchaseItem);
		} else {
			return new ReturnMessage(false, "Cannot add item to " + purchase.getStatus() + " purchase");
		}
	}

	@RequestMapping("/updatequantity")
	public PurchaseItem update(@RequestParam(value = "purchaseitemid") long purchaseItemId,
			@RequestParam(value = "quantity") float quantity) {
		PurchaseItem purchaseItem = piDao.getById(purchaseItemId);
		purchaseItem.setQuantity(quantity);
		piDao.update(purchaseItem);
		return purchaseItem;
	}

	@RequestMapping("/delete")
	public PurchaseItem delete(@RequestParam(value = "purchaseitemid") long purchaseItemId) {
		PurchaseItem purchaseItem = piDao.getById(purchaseItemId);
		piDao.delete(purchaseItem);
		return purchaseItem;
	}

}

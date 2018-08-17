package engsoft.allfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.CategoryDAO;
import engsoft.allfood.dao.ProductDAO;
import engsoft.allfood.dao.ProductHistoryDAO;
import engsoft.allfood.dao.PurchaseDAO;
import engsoft.allfood.dao.RestrictionDAO;
import engsoft.allfood.enums.PurchaseStatus;
import engsoft.allfood.model.Category;
import engsoft.allfood.model.Product;
import engsoft.allfood.model.ProductHistory;
import engsoft.allfood.model.Purchase;
import engsoft.allfood.model.PurchaseItem;
import engsoft.allfood.model.Restriction;
import engsoft.allfood.util.ProductAndHistory;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductDAO pDao = new ProductDAO();
	private ProductHistoryDAO phDao = new ProductHistoryDAO();
	private CategoryDAO cDao = new CategoryDAO();
	private RestrictionDAO rDao = new RestrictionDAO();
	private PurchaseDAO prDao = new PurchaseDAO();

	@RequestMapping("/insert")
	public Product insert(@RequestParam(value = "name") String name,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "price") float price, @RequestParam(value = "quantityInStock") float quantityInStock,
			@RequestParam(value = "categoryId") long categoryId) {
		Product product = new Product(name, description, price, quantityInStock);
		Category category = cDao.getById(categoryId);
		product.setCategory(category);
		pDao.save(product);
		ProductHistory pHistory = new ProductHistory(product);
		phDao.saveHistory(pHistory);
		return product;
	}

	@RequestMapping("/update")
	public Product update(@RequestParam(value = "productId") long productId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "price", defaultValue = "") Float price,
			@RequestParam(value = "quantityInStock", defaultValue = "") Float quantityInStock,
			@RequestParam(value = "categoryId", defaultValue = "") Long categoryId) {
		Product product = pDao.getById(productId);

		if (name != null && !name.toString().trim().equals(""))
			product.setName(name);
		if (price != null && !price.toString().trim().equals(""))
			product.setPrice(price);
		if (description != null && !description.toString().trim().equals(""))
			product.setDescription(description);
		if (quantityInStock != null && !quantityInStock.toString().trim().equals(""))
			product.setQuantityInStock(quantityInStock);
		if (categoryId != null && !categoryId.toString().trim().equals("")) {
			Category category = cDao.getById(categoryId);
			product.setCategory(category);
		}
		pDao.update(product);
		return product;
	}

	@RequestMapping("/getbyid")
	public Product getById(@RequestParam(value = "id") long id) {
		Product product = pDao.getById(id);
		return product;
	}

	@RequestMapping("/search")
	public List<ProductAndHistory> search(@RequestParam(value = "categoryId", defaultValue = "0") long categoryId,
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "orderField", defaultValue = "") String orderField,
			@RequestParam(value = "restrictions", defaultValue = "0") Long[] restrictions,
			@RequestParam(value = "isDesc", defaultValue = "false") boolean isDesc) {
		// restrictions = areRestrictionsValid(restrictions) ? restrictions :
		// "-1";
		// System.out.println(restrictions);
		return pDao.searchProducts(categoryId, text, orderField, restrictions, isDesc);
	}

	@RequestMapping("/delete")
	public Product delete(@RequestParam(value = "productId") long productId) {
		ProductHistory history = phDao.getHistoryByProductId(productId);
		Product product = pDao.getById(productId);
		phDao.deleteHistory(history);
		pDao.delete(product);
		return product;
	}

	@RequestMapping("/addRestriction")
	public Product addRestriction(@RequestParam(value = "id") long id,
			@RequestParam(value = "restrictionid") long restrictionId) {
		Restriction restriction = rDao.getById(restrictionId);
		Product product = pDao.getById(id);
		product.addRestriction(restriction);
		pDao.update(product);
		return product;
	}

	@RequestMapping("/getHistory")
	public List<Purchase> getHistory(@RequestParam(value = "productId") long id) {
		List<Purchase> purchases = prDao.getAll();
		List<Purchase> productPurchases = new ArrayList<>();
		for (Purchase purchase : purchases)
			if (purchase.getItems() != null && !purchase.getItems().isEmpty())
				if (purchase.getStatus() == PurchaseStatus.FINISHED)
					for (PurchaseItem item : purchase.getItems())
						if (item.getProduct().getId() == id && item.getPrice() > 0.0f)
							productPurchases.add(purchase);
		return productPurchases;
	}

}

package engsoft.allfood.util;

import engsoft.allfood.model.Product;
import engsoft.allfood.model.ProductHistory;

public class ProductAndHistory {

	Product product;
	ProductHistory productHistory;

	public ProductAndHistory() {
		super();
	}

	public ProductAndHistory(Product product, ProductHistory productHistory) {
		super();
		this.product = product;
		this.productHistory = productHistory;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductHistory getProductHistory() {
		return productHistory;
	}

	public void setProductHistory(ProductHistory productHistory) {
		this.productHistory = productHistory;
	}

}

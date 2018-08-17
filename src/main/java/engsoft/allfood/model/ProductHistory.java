package engsoft.allfood.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProductHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	private float totalSold;
	private float pendingQuantity;

	public ProductHistory() {
		super();
	}

	public ProductHistory(Product product, float totalSold, float pendingQuantity) {
		super();
		this.product = product;
		this.totalSold = totalSold;
		this.pendingQuantity = pendingQuantity;
	}
	
	public ProductHistory(Product product) {
		this(product, 0, 0);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public float getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(float totalSold) {
		this.totalSold = totalSold;
	}

	public float getPendingQuantity() {
		return pendingQuantity;
	}

	public void setPendingQuantity(float pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}

}

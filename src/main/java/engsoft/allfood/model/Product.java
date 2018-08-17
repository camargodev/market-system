package engsoft.allfood.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
// @Table(name="Product", schema = "allfood")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Column(nullable = true)
	private String description;
	private float price;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	private float quantityInStock;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RestrictionsByProduct", joinColumns = {
			@JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "restriction_id") })
	private List<Restriction> restrictions;

	public Product() {
	}

	public Product(String name, float price, float quantityInStock) {
		super();
		this.name = name;
		this.price = price;
		this.quantityInStock = quantityInStock;
	}

	public Product(String name, String description, float price, float quantityInStock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantityInStock = quantityInStock;
	}

	public Product(String name, String description, float price, float quantityInStock, Category category,
			List<Restriction> restrictions) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.category = category;
		this.restrictions = restrictions;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public float getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(float quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public void addRestriction(Restriction restriction) {
		if (this.restrictions == null)
			restrictions = new ArrayList<>();
		restrictions.add(restriction);
	}

}

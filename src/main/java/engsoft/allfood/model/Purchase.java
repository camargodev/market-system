package engsoft.allfood.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import engsoft.allfood.enums.DeliveryType;
import engsoft.allfood.enums.PurchaseStatus;


@Entity
public class Purchase {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JsonIgnore
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PurchaseItem> items;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", nullable = true)
	private Client client;
	private Date date;
	@Enumerated(EnumType.ORDINAL)
	private DeliveryType deliveryType;
	@Enumerated(EnumType.ORDINAL)
	private PurchaseStatus status;

	public Purchase() {
		super();
	}
	
	public Purchase(DeliveryType deliveryType) {
		super();
		this.deliveryType = deliveryType;
	}

	public Purchase(Client client, Date date, DeliveryType deliveryType, PurchaseStatus status) {
		super();
		this.client = client;
		this.date = date;
		this.deliveryType = deliveryType;
		this.status = status;
	}

	public Purchase(Client client, DeliveryType deliveryType) {
		super();
		this.client = client;
		this.deliveryType = deliveryType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<PurchaseItem> getItems() {
		return items;
	}

	public void setItems(List<PurchaseItem> items) {
		this.items = items;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public void setStatus(PurchaseStatus status) {
		this.status = status;
	}

	public float totalPrice() {
		float totalPrice = 0;
		for (PurchaseItem item : this.getItems()) {
			totalPrice += item.getQuantity() * item.getPrice();
		}
		if (this.deliveryType == DeliveryType.AT_HOME)
			return totalPrice * 1.1f;
		return totalPrice;
	}
}

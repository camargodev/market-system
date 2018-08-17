package engsoft.allfood.util;

import java.util.Date;

import engsoft.allfood.enums.DeliveryType;
import engsoft.allfood.enums.PurchaseStatus;
import engsoft.allfood.model.Client;
import engsoft.allfood.model.Purchase;

public class PurchaseListTemplate {

	private long id;
	private Client client;
	private Date date;
	private DeliveryType deliveryType;
	private PurchaseStatus status;
	private Float totalPrice;

	public PurchaseListTemplate() {
		super();
	}

	public PurchaseListTemplate(Purchase purchase) {
		super();
		this.id = purchase.getId();
		this.client = purchase.getClient();
		this.date = purchase.getDate();
		this.deliveryType = purchase.getDeliveryType();
		this.status = purchase.getStatus();
		this.totalPrice = purchase.totalPrice();
	}

	public long getId() {
		return id;
	}

	public Client getClient() {
		return client;
	}

	public Date getDate() {
		return date;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	
}

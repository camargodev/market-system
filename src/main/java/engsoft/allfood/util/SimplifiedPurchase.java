package engsoft.allfood.util;

import engsoft.allfood.enums.PurchaseStatus;

public class SimplifiedPurchase {

	private long id;
	private PurchaseStatus status;

	public SimplifiedPurchase() {
		super();
	}

	public SimplifiedPurchase(long id, PurchaseStatus status) {
		super();
		this.id = id;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public void setStatus(PurchaseStatus status) {
		this.status = status;
	}

}

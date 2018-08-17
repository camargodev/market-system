package engsoft.allfood.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import engsoft.allfood.enums.PaymentType;

@Entity
@PrimaryKeyJoinColumn(name="person_id")
public class Client extends Person {

	private boolean autenticated;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;
	@Enumerated(EnumType.ORDINAL)
	private PaymentType paymentType;
	
	public Client() {}

	public Client(String name, String CPF, String email, String password, boolean autenticated) {
		super(name, CPF, email, password);
		this.autenticated = autenticated;
	}
	
	public Client(String name, String CPF, String email, String password, Address address, PaymentType type) {
		super(name, CPF, email, password);
		this.autenticated = false;
		this.address = address;
		this.paymentType = type;
	}

	public boolean isAutenticated() {
		return autenticated;
	}

	public void setAutenticated(boolean autenticated) {
		this.autenticated = autenticated;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

}

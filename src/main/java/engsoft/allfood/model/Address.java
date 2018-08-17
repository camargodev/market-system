package engsoft.allfood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String country;
	private String state;
	private String city;
	private String CEP;
	private String street;
	private String neighborhood;
	private String complement;
	private int houseNumber;

	public Address() {
	}

	public Address(String country, String state, String city, String CEP, String street, String neighborhood,
			String complement, int houseNumber) {
		super();
		this.country = country;
		this.state = state;
		this.city = city;
		this.CEP = CEP;
		this.street = street;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.houseNumber = houseNumber;
	}

	public Address(String country, String state, String city, String CEP, String street, String neighborhood,
			int houseNumber) {
		this(country, state, city, CEP, street, neighborhood, null, houseNumber);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String CEP) {
		this.CEP = CEP;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

}

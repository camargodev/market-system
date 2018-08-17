package engsoft.allfood.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.AddressDAO;
import engsoft.allfood.model.Address;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {
	
	private AddressDAO aDao = new AddressDAO();

	@RequestMapping("/insert")
	public Address insert(
			@RequestParam(value = "country") String country,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "city") String city,
			@RequestParam(value = "street") String street,
			@RequestParam(value = "neighborhood") String neighborhood,
			@RequestParam(value = "houseNumber") int houseNumber,
			@RequestParam(value = "cep") String CEP,
			@RequestParam(value = "complement", defaultValue = "") String complement) {
		Address address = new Address(country, state, city, CEP, street, neighborhood, complement, houseNumber);
		aDao.save(address);
		return address;
	}
	
	@RequestMapping("/update")
	public Address update(
			@RequestParam(value = "id") long id,
			@RequestParam(value = "country", defaultValue = "") String country,
			@RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "street", defaultValue = "") String street,
			@RequestParam(value = "neighborhood", defaultValue = "") String neighborhood,
			@RequestParam(value = "houseNumber", defaultValue = "") Integer houseNumber,
			@RequestParam(value = "cep", defaultValue = "") String CEP,
			@RequestParam(value = "complement", defaultValue = "") String complement) {
		Address address = aDao.getById(id);
		
		if (country != null && !country.trim().equals(""))
			address.setCountry(country);
		if (state != null && !state.trim().equals(""))
			address.setState(state);
		if (city != null && !city.trim().equals(""))
			address.setCity(city);
		if (street != null && !street.trim().equals(""))
			address.setStreet(street);
		if (neighborhood != null && !neighborhood.trim().equals(""))
			address.setNeighborhood(neighborhood);
		if (houseNumber != null && !houseNumber.toString().trim().equals(""))
			address.setHouseNumber(houseNumber);
		if (CEP != null && !CEP.trim().equals(""))
			address.setCEP(CEP);
		if (complement != null && !complement.trim().equals(""))
			address.setComplement(complement);
		
		aDao.update(address);
		return address;
	}
	
	@RequestMapping("/getbyid")
	public Address getById(@RequestParam(value = "id") long id) {
		Address address = aDao.getById(id);
		return address;
	}

	@RequestMapping("/getbyclientid")
	public Address getByClientId(@RequestParam(value = "clientId") long id) {
		return aDao.getAddressByClientId(id);
	}
	
}

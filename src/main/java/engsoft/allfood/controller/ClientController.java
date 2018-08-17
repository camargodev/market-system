package engsoft.allfood.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.AddressDAO;
import engsoft.allfood.dao.ClientDAO;
import engsoft.allfood.model.Address;
import engsoft.allfood.model.Client;
import engsoft.allfood.util.EnumUtils;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

	private AddressDAO aDao = new AddressDAO();
	private ClientDAO cDao = new ClientDAO();
	
	@RequestMapping("/insert")
	public Client insert(@RequestParam(value = "name") String name,
			@RequestParam(value = "cpf") String CPF,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "autenticated") boolean autenticated,
			@RequestParam(value = "paymentType") int paymentType,
			@RequestParam(value = "addressId") long addressId) {
		Client client = new Client(name, CPF, email, password, autenticated);
		Address address = aDao.getById(addressId);
		client.setAddress(address);
		client.setPaymentType(EnumUtils.getPaymentType(paymentType));
		cDao.save(client);
		return client;
	}
	
	@RequestMapping("/update")
	public Client update(
			@RequestParam(value = "id") long id,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "cpf", defaultValue = "") String CPF,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "autenticated", defaultValue = "") Boolean autenticated,
			@RequestParam(value = "paymentType", defaultValue = "") Integer paymentType,
			@RequestParam(value = "addressId", defaultValue = "") Long addressId) {
		
		Client client = cDao.getById(id);
		
		if (name != null && !name.trim().equals(""))
			client.setName(name);
		if (CPF != null && !CPF.trim().equals(""))
			client.setCPF(CPF);
		if (email != null && !email.trim().equals(""))
			client.setEmail(email);
		if (autenticated != null && !autenticated.toString().trim().equals(""))
			client.setAutenticated(autenticated);
		if (paymentType != null && !paymentType.toString().trim().equals("")) 
			client.setPaymentType(EnumUtils.getPaymentType(paymentType));
		if (addressId != null && !addressId.toString().trim().equals("")) {
			Address address = aDao.getById(addressId);
			client.setAddress(address);
		}
		
		cDao.update(client);
		return client;
	}
	@RequestMapping("/getbyid")
	public Client getById(@RequestParam(value = "id") long id) {
		Client client = cDao.getById(id);
		return client;
	}

	@RequestMapping("/search")
	public List<Client> search(
			@RequestParam(value = "text", defaultValue = "") String text) {
		return cDao.searchClients(text);
	}
	
	@RequestMapping("/delete")
	public Client delete(@RequestParam(value = "id") long id) {
		Client client = cDao.getById(id);
		Address address = client.getAddress();
		//Precisa que cada cliente possua uma entrada de endereço diferente no BD, assim dois cliente poderiam ter o mesmo 
		//endereço desde que entradas diferentes no BD assim cada um poderia atualizar o seu independentemente
		cDao.delete(client);
		aDao.delete(address);
		
		return client;
	}
}

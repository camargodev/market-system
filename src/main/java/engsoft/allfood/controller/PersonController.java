package engsoft.allfood.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.AdminDAO;
import engsoft.allfood.dao.ClientDAO;
import engsoft.allfood.dao.PersonDAO;
import engsoft.allfood.model.Admin;
import engsoft.allfood.model.Client;
import engsoft.allfood.model.Person;
import engsoft.allfood.util.LoginReturnMessage;
import engsoft.allfood.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/person")
public class PersonController {

	private PersonDAO pDao = new PersonDAO();
	private ClientDAO cDao = new ClientDAO();
	private AdminDAO aDao = new AdminDAO();

	@RequestMapping("/login")
	public ReturnMessage login(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		Person person = pDao.getByEmail(email);
		if (person == null)
			return new ReturnMessage(false, "Email not found");
		if (!password.equals(person.getPassword()))
			return new ReturnMessage(false, "Wrong password");
		else {
			Client client = cDao.getById(person.getId());
			if (client != null)
				return new LoginReturnMessage(true, false, client);
			Admin admin = aDao.getById(person.getId());
			if (admin != null)
				return new LoginReturnMessage(true, true, admin);
			return new ReturnMessage(true, "URGENTE: Shit happened");
		}
	}

}

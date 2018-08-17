package engsoft.allfood.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.AdminDAO;
import engsoft.allfood.model.Admin;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

	private AdminDAO aDao = new AdminDAO();
	
	@RequestMapping("/insert")
	public Admin insert(@RequestParam(value = "name") String name,
			@RequestParam(value = "cpf") String CPF,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		Admin admin = new Admin(name, CPF, email, password);
		aDao.save(admin);
		return admin;
	}
	
	@RequestMapping("/update")
	public Admin update(
			@RequestParam(value = "id") long id,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "cpf", defaultValue = "") String CPF,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password", defaultValue = "") String password) {
		
		Admin admin = aDao.getById(id);
		
		if (name != null && !name.trim().equals(""))
			admin.setName(name);
		if (CPF != null && !CPF.trim().equals(""))
			admin.setCPF(CPF);
		if (email != null && !email.trim().equals(""))
			admin.setEmail(email);
		
		aDao.update(admin);
		return admin;
	}
	@RequestMapping("/getbyid")
	public Admin getById(@RequestParam(value = "id") long id) {
		Admin admin = aDao.getById(id);
		return admin;
	}
	@RequestMapping("/delete")
	public Admin delete(@RequestParam(value = "id") long id) {
		Admin admin = aDao.getById(id);
		aDao.delete(admin);
		return admin;
	}
	
}

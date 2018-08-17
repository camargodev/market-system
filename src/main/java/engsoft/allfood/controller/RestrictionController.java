package engsoft.allfood.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.RestrictionDAO;
import engsoft.allfood.model.Restriction;

@CrossOrigin
@RestController
@RequestMapping("/restriction")
public class RestrictionController {

	private RestrictionDAO rDao = new RestrictionDAO();

	@RequestMapping("/insert")
	public Restriction insert(@RequestParam(value = "name") String name) {
		Restriction restriction = new Restriction(name);
		rDao.save(restriction);
		return restriction;
	}

	@RequestMapping("/update")
	public Restriction update(@RequestParam(value = "id") long id,
			@RequestParam(value = "name", defaultValue = "") String name) {
		Restriction restriction = rDao.getById(id);
		if (name != null && !name.trim().equals(""))
			restriction.setName(name);
		rDao.update(restriction);
		return restriction;
	}

	@RequestMapping("/list")
	public List<Restriction> getAll() {
		return rDao.getAll();
	}

}

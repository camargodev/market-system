package engsoft.allfood.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.CategoryDAO;
import engsoft.allfood.model.Category;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

	private CategoryDAO cDao = new CategoryDAO();

	@RequestMapping("/insert")
	public Category insert(@RequestParam(value = "name") String name) {
		Category category = new Category(name);
		cDao.save(category);
		return category;
	}

	@RequestMapping("/update")
	public Category update(@RequestParam(value = "id") long id,
			@RequestParam(value = "name", defaultValue = "") String name) {
		Category category = cDao.getById(id);
		if (name != null && !name.trim().equals(""))
			category.setName(name);
		cDao.update(category);
		return category;
	}

	@RequestMapping("/list")
	public List<Category> getAll() {
		return cDao.getAll();
	}

}

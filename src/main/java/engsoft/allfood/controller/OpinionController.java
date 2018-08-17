package engsoft.allfood.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.allfood.dao.ClientDAO;
import engsoft.allfood.dao.OpinionDAO;
import engsoft.allfood.dao.ProductDAO;
import engsoft.allfood.model.Client;
import engsoft.allfood.model.Opinion;
import engsoft.allfood.model.Product;

@CrossOrigin
@RestController
@RequestMapping("/opinion")
public class OpinionController {

	private OpinionDAO oDao = new OpinionDAO();
	private ClientDAO cDao = new ClientDAO();
	private ProductDAO pDao = new ProductDAO();

	@RequestMapping("/insert")
	public Opinion insert(@RequestParam(value = "clientid") long clientId,
			@RequestParam(value = "productid") long productId, @RequestParam(value = "title") String title,
			@RequestParam(value = "text") String text, @RequestParam(value = "score") int score) {
		Client client = cDao.getById(clientId);
		Product product = pDao.getById(productId);
		Opinion opinion = new Opinion(client, product, title, text, score);
		oDao.save(opinion);
		return opinion;
	}

	@RequestMapping("/update")
	public Opinion update(@RequestParam(value = "opinionid") long opinionId,
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "score", defaultValue = "") Integer score) {
		Opinion opinion = oDao.getById(opinionId);
		if (title != null && !title.toString().trim().equals(""))
			opinion.setTitle(title);
		if (text != null && !text.toString().trim().equals(""))
			opinion.setText(text);
		if (score != null && !score.toString().trim().equals(""))
			opinion.setScore(score);
		oDao.save(opinion);
		return opinion;
	}

	@RequestMapping("/delete")
	public Opinion delete(@RequestParam(value = "opinionid") long opinionId) {
		Opinion opinion = oDao.getById(opinionId);
		oDao.delete(opinion);
		return opinion;
	}
	
	@RequestMapping("/getbyclient")
	public List<Opinion> getByClient(@RequestParam(value = "id") long clientId) {
		return oDao.getOpinionsByClient(clientId);
	}
	
	@RequestMapping("/getbyproduct")
	public List<Opinion> getByProduct(@RequestParam(value = "id") long productId) {
		return oDao.getOpinionsByProduct(productId);
	}

}

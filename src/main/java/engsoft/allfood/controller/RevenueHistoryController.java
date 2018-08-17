package engsoft.allfood.controller;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import engsoft.allfood.dao.RevenueHistoryDAO;
import engsoft.allfood.model.RevenueHistory;

@CrossOrigin
@RestController
@RequestMapping("/revenuehistory")
public class RevenueHistoryController {
	
	private RevenueHistoryDAO rhDao = new RevenueHistoryDAO();
	
	//esse insert deve ser retirado no produto final, o próprio sistema deve criar os históricos de faturamento
	@RequestMapping("/insert")
	public RevenueHistory insert(@RequestParam(value = "date") String date,
			@RequestParam(value = "revenue") float revenue) {
		RevenueHistory revenueHistory = new RevenueHistory(date, revenue);
		rhDao.save(revenueHistory);
		return revenueHistory;
	}
	
	
	@RequestMapping("/getByDate")
	public RevenueHistory getByDate(@RequestParam(value = "date") String date) {
		RevenueHistory revenue =  rhDao.getByDate(date);
		return revenue;
	}
	
	@RequestMapping("/getAll")
	public List<RevenueHistory> getEntireRevenueHistory() {
		List<RevenueHistory> revenues =  rhDao.getEntireRevenueHistory();
		return revenues;
	}

}
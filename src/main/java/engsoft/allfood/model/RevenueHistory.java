package engsoft.allfood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class RevenueHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String date;

    private float revenue;
    
	public RevenueHistory(){
		
	}
	
	public RevenueHistory(String date, float revenue){
		this.date = date;
		this.revenue = revenue;		
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public float getRevenue(){
		return this.revenue;
	}
	
	public void setRevenue(float revenue){
		this.revenue = revenue;
	}

}

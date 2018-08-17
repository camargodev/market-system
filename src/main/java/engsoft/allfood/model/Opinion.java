package engsoft.allfood.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Opinion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="client_id", nullable=false)
	private Client client;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	private String title;
	private String text;
	private int score; // SHOULD BE SHOWN AS STARS 1 TO 5
	
	public Opinion(){
	}
	
	public Opinion(Client client,Product product, String title, String text, int score){
		super();
		this.client=client;
		this.product=product;
		this.title=title;
		this.text=text;
		this.score=score;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	
}

package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Opinion;
import engsoft.allfood.util.HibernateUtil;
import engsoft.allfood.util.QueryGenerator;

public class OpinionDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Opinion address) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(address);
		tx.commit();
		session.close();
	}
	
	public void update(Opinion address) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(address);
		tx.commit();
		session.close();
	}
	
	public void delete(Opinion address) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(address);
		tx.commit();
		session.close();
	}
	
	public Opinion getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Opinion address = (Opinion) session.get(Opinion.class, id);
		tx.commit();
		session.close();
		return address;
	}
	
	@SuppressWarnings("unchecked")
	public List<Opinion> getOpinionsByClient(long clientId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Opinion> query = session.createQuery(generateOpinionsByClientQuery());
		query.setParameter("id", clientId);
		List<Opinion> opinions = query.getResultList();
		session.close();
		return opinions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Opinion> getOpinionsByProduct(long productId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Opinion> query = session.createQuery(generateOpinionsByProductQuery());
		query.setParameter("id", productId);
		List<Opinion> opinions = query.getResultList();
		session.close();
		return opinions;
	}

	private String generateOpinionsByProductQuery() {
		QueryGenerator queryGen = new QueryGenerator();
		queryGen.from("Opinion").as("o");
		queryGen.equalsTo("o.product.id", ":id");
		return queryGen.select("o");
	}
	
	private String generateOpinionsByClientQuery() {
		QueryGenerator queryGen = new QueryGenerator();
		queryGen.from("Opinion").as("o");
		queryGen.equalsTo("o.client.id", ":id");
		return queryGen.select("o");
	}

}

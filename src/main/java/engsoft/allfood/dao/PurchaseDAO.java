package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Purchase;
import engsoft.allfood.util.HibernateUtil;
import engsoft.allfood.util.QueryGenerator;

@SuppressWarnings("unchecked")
public class PurchaseDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Purchase purchase) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(purchase);
		tx.commit();
		session.close();
	}

	public void update(Purchase purchase) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(purchase);
		tx.commit();
		session.close();
	}

	public void delete(Purchase purchase) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(purchase);
		tx.commit();
		session.close();
	}

	public boolean ableToCreatePurchase(long clientId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Purchase> query = session.createQuery(purchaseAtCartQuery());
		query.setParameter("clientId", clientId);
		List<Purchase> result = query.getResultList();
		session.close();
		// return result;
		return (result == null || result.isEmpty());
	}

	public Purchase getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Purchase purchase = (Purchase) session.get(Purchase.class, id);
		tx.commit();
		session.close();
		return purchase;
	}

	public List<Purchase> getByClientId(long clientId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Purchase> query = session.createQuery("FROM Purchase WHERE client.id = " + clientId);
		List<Purchase> result = query.getResultList();
		session.close();
		return result;
	}
	
	public List<Purchase> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Purchase> query = session.createQuery("FROM Purchase p ORDER BY p.date DESC");
		List<Purchase> result = query.getResultList();
		session.close();
		return result;
	}

	private String purchaseAtCartQuery() {
		QueryGenerator queryGen = new QueryGenerator();
		queryGen.from("Purchase").as("p");
		queryGen.equalsTo("p.client.id", ":clientId");
		queryGen.equalsTo("p.status", "0");
		return queryGen.select("p");
	}

	
	
}

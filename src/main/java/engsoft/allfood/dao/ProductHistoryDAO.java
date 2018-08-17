package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.ProductHistory;
import engsoft.allfood.util.HibernateUtil;

public class ProductHistoryDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public void saveHistory(ProductHistory history) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(history);
		tx.commit();
		session.close();
	}
	
	public void deleteHistory(ProductHistory history) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(history);
		tx.commit();
		session.close();
	}

	public void updateHistory(ProductHistory history) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(history);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ProductHistory getHistoryByProductId(long productId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<ProductHistory> query = session.createQuery("FROM ProductHistory WHERE product_id = " + productId);
		List<ProductHistory> result = query.getResultList();
		session.close();
		return result != null ? result.get(0) : null;
	}

}

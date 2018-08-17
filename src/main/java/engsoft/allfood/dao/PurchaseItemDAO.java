package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.PurchaseItem;
import engsoft.allfood.util.HibernateUtil;

public class PurchaseItemDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(PurchaseItem purchaseItem) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(purchaseItem);
		tx.commit();
		session.close();
	}

	public void update(PurchaseItem purchaseItem) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(purchaseItem);
		tx.commit();
		session.close();
	}

	public void delete(PurchaseItem purchaseItem) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(purchaseItem);
		tx.commit();
		session.close();
	}

	public PurchaseItem getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		PurchaseItem purchaseItem = (PurchaseItem) session.get(PurchaseItem.class, id);
		tx.commit();
		session.close();
		return purchaseItem;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseItem> getItemsByPurchase(long purchaseId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<PurchaseItem> query = session.createQuery("FROM PurchaseItem WHERE purchase_id = " + purchaseId);
		List<PurchaseItem> result = query.getResultList();
		session.close();
		return result;
	}

}

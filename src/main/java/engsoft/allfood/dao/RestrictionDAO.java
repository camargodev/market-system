package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Restriction;
import engsoft.allfood.util.HibernateUtil;

public class RestrictionDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Restriction restriction) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(restriction);
		tx.commit();
		session.close();
	}
	
	public Restriction getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Restriction restriction = (Restriction) session.get(Restriction.class, id);
		tx.commit();
		session.close();
		return restriction;
	}
	
	public void update(Restriction restriction) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(restriction);
		tx.commit();
		session.close();
	}
	
	public void delete(Restriction restriction) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(restriction);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Restriction> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Restriction> query = session.createQuery("FROM Restriction");
		List<Restriction> result = query.getResultList();
		session.close();
		return result;
	}

}

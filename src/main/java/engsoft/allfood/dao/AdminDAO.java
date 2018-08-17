package engsoft.allfood.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Admin;
import engsoft.allfood.util.HibernateUtil;

public class AdminDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Admin admin) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(admin);
		tx.commit();
		session.close();
	}
	
	public void update(Admin admin) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(admin);
		tx.commit();
		session.close();
	}
	
	public Admin getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Admin client = (Admin) session.get(Admin.class, id);
		tx.commit();
		session.close();
		return client;
	}
	public void delete(Admin admin) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(admin);
		tx.commit();
		session.close();
	}

}
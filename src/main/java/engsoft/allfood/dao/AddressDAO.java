package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Address;
import engsoft.allfood.util.HibernateUtil;

public class AddressDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Address address) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(address);
		tx.commit();
		session.close();
	}
	
	public void update(Address address) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(address);
		tx.commit();
		session.close();
	}
	
	public Address getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Address address = (Address) session.get(Address.class, id);
		tx.commit();
		session.close();
		return address;
	}
	public void delete(Address address) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(address);
		tx.commit();
		session.close();
	}
	@SuppressWarnings("unchecked")
	public Address getAddressByClientId(long clientId) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Address> query = session.createQuery("FROM Address WHERE client_id = " + clientId);
		List<Address> result = query.getResultList();
		session.close();
		return result != null ? result.get(0) : null;
	}

}

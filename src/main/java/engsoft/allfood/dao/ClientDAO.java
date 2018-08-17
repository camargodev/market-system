package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Client;
import engsoft.allfood.util.HibernateUtil;
import engsoft.allfood.util.QueryGenerator;

public class ClientDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Client client) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(client);
		tx.commit();
		session.close();
	}

	public void update(Client client) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(client);
		tx.commit();
		session.close();
	}

	public Client getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Client client = (Client) session.get(Client.class, id);
		tx.commit();
		session.close();
		return client;
	}

	public void delete(Client client) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(client);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Client> searchClients(String text) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Client> query = session.createQuery(generateClientListQuery());
		query.setParameter("text", text.toLowerCase() + "%");
		List<Client> clients = query.getResultList();
		session.close();
		return clients;
	}

	private String generateClientListQuery() {
		QueryGenerator queryGen = new QueryGenerator();
		queryGen.from("Client").as("c");
		queryGen.like("c.name", ":text");
		return queryGen.select("c");
	}

}

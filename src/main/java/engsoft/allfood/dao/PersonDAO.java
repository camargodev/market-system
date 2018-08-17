package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import engsoft.allfood.model.Person;
import engsoft.allfood.util.HibernateUtil;
import engsoft.allfood.util.QueryGenerator;

public class PersonDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@SuppressWarnings("unchecked")
	public Person getByEmail(String email) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Person> query = session.createQuery(generateEmailQuery(email));
		List<Person> result = query.getResultList();
		session.close();
		if (result != null && !result.isEmpty())
			return result.get(0);
		else
			return null;
	}

	private String generateEmailQuery(String email) {
		QueryGenerator queryGen = new QueryGenerator();
		queryGen.from("Person").as("p");
		queryGen.equalsToText("p.email", email);
		return queryGen.select("p");
	}

}

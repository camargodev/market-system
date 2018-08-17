package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Category;
import engsoft.allfood.util.HibernateUtil;

public class CategoryDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Category category) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(category);
		tx.commit();
		session.close();
	}
	
	public Category getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Category category = (Category) session.get(Category.class, id);
		tx.commit();
		session.close();
		return category;
	}
	
	public void update(Category category) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(category);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Category> query = session.createQuery("FROM Category");
		List<Category> result = query.getResultList();
		session.close();
		return result;
	}

}

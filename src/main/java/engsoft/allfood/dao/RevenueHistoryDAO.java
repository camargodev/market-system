package engsoft.allfood.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.RevenueHistory;
import engsoft.allfood.util.HibernateUtil;

public class RevenueHistoryDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public void save(RevenueHistory revenueHistory){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(revenueHistory);
		tx.commit();
		session.close();
	}
	
	public void update(RevenueHistory revenueHistory) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(revenueHistory);
		tx.commit();
		session.close();
	}
	
	
	@SuppressWarnings("unchecked")
	public RevenueHistory getByDate(String date){
		Session session = this.sessionFactory.openSession();
		String dateString = "'"+date+"'";
		RevenueHistory result;
		//se o histórico para a data passada já existe, simplesmente retornamos ele
		try{
			TypedQuery<RevenueHistory> query = session.createQuery("FROM RevenueHistory WHERE date = " + dateString);
			result = query.getSingleResult();
		}
		//caso o histórico pra data pedida não existe, o criamos, salvamos no BD e o retornamos
		catch(NoResultException e){
			RevenueHistory revenueHistory = new RevenueHistory(date, 0);
			this.save(revenueHistory);
			TypedQuery<RevenueHistory> query = session.createQuery("FROM RevenueHistory WHERE date = " + dateString);
			result = query.getSingleResult();
		}
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<RevenueHistory> getEntireRevenueHistory(){
		Session session = this.sessionFactory.openSession();
		TypedQuery<RevenueHistory> query = session.createQuery("FROM RevenueHistory rh ORDER BY rh.date desc");
		List<RevenueHistory> result = query.getResultList();
		session.close();
		return result;
	}
}

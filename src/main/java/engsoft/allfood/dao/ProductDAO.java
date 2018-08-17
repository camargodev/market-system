package engsoft.allfood.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.allfood.model.Product;
import engsoft.allfood.model.ProductHistory;
import engsoft.allfood.model.Restriction;
import engsoft.allfood.util.HibernateUtil;
import engsoft.allfood.util.ProductAndHistory;
import engsoft.allfood.util.QueryGenerator;

public class ProductDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Product product) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(product);
		tx.commit();
		session.close();
	}

	public void update(Product product) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(product);
		tx.commit();
		session.close();
	}

	public void delete(Product product) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(product);
		tx.commit();
		session.close();
	}

	public Product getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Product product = (Product) session.get(Product.class, id);
		tx.commit();
		session.close();
		return product;
	}

	@SuppressWarnings("unchecked")
	public List<ProductAndHistory> searchProducts(long categoryId, String text, String orderField, Long[] restrictions,
			boolean isDesc) {
		Session session = this.sessionFactory.openSession();
		boolean isCategoryUsed = categoryId > 0;
		orderField = !orderField.trim().equals("") ? orderField : "p.price";
		TypedQuery<Object[]> query = session
				.createQuery(generateProductListQuery(isCategoryUsed, restrictions, orderField, isDesc));
		query.setParameter("text", "%".concat(text.toLowerCase()).concat("%"));
		if (isCategoryUsed)
			query.setParameter("categoryId", categoryId);
		System.out.println("\n\n" + query.toString());
		List<Object[]> result = query.getResultList();
		List<ProductAndHistory> prodsAndHists = new ArrayList<>();
		for (Object[] object : result) {
			Product product = (Product) object[0];
			boolean addProduct = true;
			for (Restriction restriction : product.getRestrictions()) {
				for (long id : restrictions) {
					if (restriction.getId() == id)
						addProduct = false;
				}
			}
			ProductHistory pHistory = (ProductHistory) object[1];
			if (addProduct)
				prodsAndHists.add(new ProductAndHistory(product, pHistory));
		}
		session.close();
		return prodsAndHists;
	}

	private String generateProductListQuery(boolean isCategoryUsed, Long[] restrictions, String orderField, boolean isDesc) {
		QueryGenerator queryGen = new QueryGenerator();
		queryGen.from("Product").as("p");
		queryGen.inner("ProductHistory").as("ph").on("p.id", "ph.product.id");
		// queryGen.join("p.restrictions").as("r");
		queryGen.like("p.name", ":text");
		// queryGen.notInList("r.id", restrictions);
		if (isCategoryUsed)
			queryGen.equalsTo("p.category.id", ":categoryId");
		queryGen.orderBy(orderField, isDesc);
		return queryGen.select("p, ph");
	}

}

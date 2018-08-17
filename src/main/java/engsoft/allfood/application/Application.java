package engsoft.allfood.application;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import engsoft.allfood.dao.AdminDAO;
import engsoft.allfood.dao.CategoryDAO;
import engsoft.allfood.dao.ProductDAO;
import engsoft.allfood.dao.ProductHistoryDAO;
import engsoft.allfood.dao.RestrictionDAO;
import engsoft.allfood.model.Admin;
import engsoft.allfood.model.Category;
import engsoft.allfood.model.Product;
import engsoft.allfood.model.ProductHistory;
import engsoft.allfood.model.Restriction;

@SpringBootApplication
@ComponentScan(basePackages = { "engsoft.allfood.controller" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		generateSamples();
	}

	private static void generateSamples() {
		
		/** CATEGORIAS */
		CategoryDAO cDao = new CategoryDAO();
		ArrayList<Category> categories = new ArrayList<>();
		categories.add(new Category("Laticinio")); 	// index = 0
		categories.add(new Category("Frutas")); 	// index = 1
		categories.add(new Category("Congelados")); // index = 2
		categories.add(new Category("Carnes")); 	// index = 3
		for (Category c : categories)
			cDao.save(c);
		
		/** RESTRIÇÕES */
		RestrictionDAO rDao = new RestrictionDAO();
		ArrayList<Restriction> restrictions = new ArrayList<>();
		restrictions.add(new Restriction("Contém Lactose"));	// index = 0
		restrictions.add(new Restriction("Contém Gluten"));		// index = 1
		restrictions.add(new Restriction("Contém Soja"));		// index = 2
		for (Restriction r : restrictions)
			rDao.save(r);
		
		/** PRODUTOS */
		ProductDAO pDao = new ProductDAO();
		ProductHistoryDAO phDao = new ProductHistoryDAO();
		ArrayList<Product> products = new ArrayList<>();
		ArrayList<Restriction> tempRestrictions = new ArrayList<>();
		tempRestrictions.add(restrictions.get(0));
		products.add(new Product("Leite 1L", "Leite de Vaca", 2.70f, 50, categories.get(0), tempRestrictions)); 					// index = 0
		tempRestrictions = new ArrayList<>();
		tempRestrictions.add(restrictions.get(0));					
		tempRestrictions.add(restrictions.get(1));
		products.add(new Product("Hot Pocket", "Hamburguer de Carne e Queijo", 6.60f, 30, categories.get(2), tempRestrictions)); 	// index = 1
		tempRestrictions = new ArrayList<>();
		tempRestrictions.add(restrictions.get(0));					
		tempRestrictions.add(restrictions.get(1));
		tempRestrictions.add(restrictions.get(2));
		products.add(new Product("Hamburguer de Carne e Soja", "Nem existe", 3.60f, 40, categories.get(2), tempRestrictions)); 		// index = 2
		products.add(new Product("Banana Prata", "Banana da Boa", 1.3f, 80, categories.get(1), new ArrayList<>())); 				// index = 3
		products.add(new Product("Maminha", "Feito de Gado", 9.5f, 20, categories.get(3), new ArrayList<>())); 						// index = 4
		for (Product p : products) {
			pDao.save(p);
			ProductHistory pHistory = new ProductHistory(p);
			phDao.saveHistory(pHistory);
		}
//		/** ENDEREÇOS */
//		AddressDAO aDao = new AddressDAO();
//		ArrayList<Address> addresses = new ArrayList<>();
//		addresses.add(new Address("Brazil", "RS", "Porto Alegre", "12345-678", "Rua 1", "Jardins", "Apto. 203", 1024));
//		addresses.add(new Address("Brazil", "RS", "Porto Alegre", "14051-997", "Rua 2", "Montanhas", 1405));
//		addresses.add(new Address("Brazil", "RS", "Porto Alegre", "51997-710", "Rua 3", "Ceu Azul", "Apto. 7", 64));
//		for (Address a : addresses)
//			aDao.save(a);
//		
//		/** CLIENTES */
//		ClientDAO clDao = new ClientDAO();
//		ArrayList<Client> clients = new ArrayList<>();
//		clients.add(new Client("Marquinhos Honesto", "123.456.789-10", "mark@honest.com", "123456", addresses.get(0), PaymentType.CARD));
//		clients.add(new Client("Carla Feliz", "321.654.987-01", "carla@happy.com", "123456", addresses.get(1), PaymentType.MONEY));
//		clients.add(new Client("Tony Incrivel", "111.222.333-44", "tony@incredible.com", "123456", addresses.get(2), PaymentType.TICKET));
//		for (Client c : clients)
//			clDao.save(c);
//		
		/** ADMIN */
		AdminDAO admDao = new AdminDAO();
		ArrayList<Admin> admins = new ArrayList<>();
		admins.add(new Admin("Root", "000.000.000-00", "root", "root"));
		for (Admin a : admins)
			admDao.save(a);
//		
//		/** COMPRAS */
//		PurchaseDAO prDao = new PurchaseDAO();
//		ArrayList<Purchase> purchases = new ArrayList<>();
//		purchases.add(new Purchase(clients.get(0), new Date(), DeliveryType.AT_HOME, PurchaseStatus.AT_CART));
//		purchases.add(new Purchase(clients.get(1), new Date(), DeliveryType.AT_MARKET, PurchaseStatus.AT_CART));
//		for (Purchase p : purchases)
//			prDao.save(p);
//		
//		/** ITENS DE COMPRA */
//		PurchaseItemDAO piDao = new PurchaseItemDAO();
//		ArrayList<PurchaseItem> itens = new ArrayList<>();
//		itens.add(new PurchaseItem(products.get(0), 5, purchases.get(0)));
//		itens.add(new PurchaseItem(products.get(1), 3, purchases.get(0)));
//		itens.add(new PurchaseItem(products.get(2), 2, purchases.get(1)));
//		itens.add(new PurchaseItem(products.get(3), 1, purchases.get(1)));
//		itens.add(new PurchaseItem(products.get(4), 3, purchases.get(1)));
//		for (PurchaseItem pi : itens)
//			piDao.save(pi);
//		
//		/** FEEDBACK DO CLIENTE */
//		OpinionDAO oDao = new OpinionDAO();
//		ArrayList<Opinion> opinions = new ArrayList<>();
//		opinions.add(new Opinion(clients.get(0), products.get(1), "Muuuito bom!", "Esse hot pocket deveria se chamar top pocket", 5));
//		opinions.add(new Opinion(clients.get(0), products.get(0), "Não gostei!", "Achei esse leite muito palido", 3));
//		opinions.add(new Opinion(clients.get(1), products.get(3), "Incrível!", "Essa banana é wonderful, como diriam os britanicos", 4));
//		for (Opinion o : opinions)
//			oDao.save(o);
		
		
	}
}

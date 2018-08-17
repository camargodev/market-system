package engsoft.allfood.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

//A entidade vai ser como a entidade pessoa, a utilidade de separar é que Admin vai precisar
//de um gerenciamento de seção mais complexo
@Entity
@PrimaryKeyJoinColumn(name="person_id")
public class Admin extends Person {

	
	public Admin() {}

	public Admin(String name, String CPF, String email, String password) {
		super(name, CPF, email, password);
	}

	
}

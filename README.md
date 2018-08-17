# Engenharia de Software 18/2 - AllFood
## Bruno Santana, Felipe Zorzo, João Vitor de Camargo, Rafa Longo e William Wilbert 

Tecnologias usadas:
- Java 1.8
- Maven
- RESTful API
- Hibernate
- PostgreSQL (para o BD)
O único necessário instalar é o Postgres. O resto é gerenciado pelo pom.xml (explicado abaixo).
Para importar esse projeto para o Eclipse, é necessário importar como Maven Project.

Primeiramente, explicando alguns conceitos:
 
- JSON: formato simples de representar um objeto. Uma pessoa chamada Carlos de 20 anos vira {"name":"Carlos","age":20}.
- Servidor RESTful: muito resumidamente, um servidor que recebe requisições web (através de uma URL) e retorna um JSON.
- Persistência: é um termo meio genérico, mas nesse contexto, é basicamente uma "automatização" da comunicação com o banco de dados. Ao invés de escrever toda a query e receber um texto de volta como resposta de um select, por exemplo, a gente mapeia as classes (entidades) do projeto para tabelas e insere/recebe objetos. Aqui, estamos usando uma biblioteca de persistência chamada Hibernate, que provavelmente é a mais consolidada do mercado.
- Maven: tecnologia usada para gerenciar as dependências do projeto. Ao invés de baixar 15 JARS pro projeto, basta declarar eles no arquivo de configuração do Maven (pom.xml).



Agora detalhando:

- Cada modelo (bean, POJO, entidade ou como preferir chamar) criado deve estar no pacote engsoft.allfood.model.
Para o Hibernate entender que essa classe deve ser uma tabela, a anotação @​Entity deve estar na declaração. Existem várias anotações que permitem que todo o necessário seja feito. Em qualquer classe do pacote dito acima, é possível ver as anotações necessárias para se declarar uma chave primária, por exemplo. Na classe Product, é possível ver uma das formas de declarar uma chave estrangeira (nesse caso, um ou mais produtos possuem uma categoria), então no atributo Category (na classe Product) foi usada a anotação @​ManyToOne. Também tem a @​OneToOne, @​OneToMany, anotação pra editar nome da coluna (se for diferente do atributo), anotação pra editar nome da tabela, etc. Enfim, cada modelo com @​Entity vai virar uma tabela.

- Cada modelo cujas informações devem ser acessadas diretamente por um agente externo (como nosso front-end) deve ter um controller. Todos os controller devem estar no pacote engsoft.allfood.controller. É bastante fácil ver como esses controller funcionam. Basta colocar @​RequestMapping em cima da classe e de métodos para determinar o caminho da URL respectiva. Na CategoryController, por exemplo, tem um @​RequestMapping("/category") em cima da declaração da classe e um @​RequestMapping("/insert") em cima do método insert, então pra acessar esse método de fora, basta acessar, por ex, localhost:8080/category/insert/... Para ver como passar parâmetros, basta apenas ver o método dessa mesma classe. Um exemplo de URL para inserir uma categoria é: http://localhost:8080/category/insert?name=Alimento. Para passar mais parâmetros, como por exemplo na inserção de um produto, basta usar: localhost:8080/product/insert?name=Bola&price=50&categorId=1. Ambas as URLs mostradas retornação o objeto inserido, com o ID. O ID é recebido após inserção no banco de dados.

- Cada modelo também deve ser uma classe DAO (Data Access Objects). Essas são classes que servem para persistir os modelos, ou seja, inserir/ler/atualizar/excluir do banco de dados (ou seja, são classes que servem como CRUDs [Create/Read/Update/Delete]). Basta acessar a CategoryDAO, por exemplo, pra ver como é feita uma inserção simples. Na ProductDAO, é possível ver dois Selects, um que seria com 'Where Id = X" e outro sem Where.



Explicando os arquivos:

- pom.xml: arquivo do Maven que tem as dependências do projeto. Lá vão ter dependências relacionadas ao Spring, ao uso de JSON, ao Hibernate, etc.
- hibernate.cfg.xml: arquivo de configuração do Hibernate. Aqui é onde é colocado o banco de dados onde as coisas serão criadas. ATENÇÃO: não é necessário criar as tabelas na mão com SQLs. Os modelos persistidos virarão as tabelas. É necessário apenas criar um banco de dados PostgreSQL. O que DEVE ser alterado nesse arquivo é o usuário/senha do Postgres e a URL do banco de dados (provavelmente só o nome, que o meu eu criei como allfood). Lá também devem ser adicionadas os modelos que virarão tabelas.
- HibernateUtil: classe que informa que usa os dados do hibernate.cfg.xml pra criar sessões com BD. Nas classes DAO criadas, a HibernateUtil foi usada pra criar sessões.:

# GLHF 


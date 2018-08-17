package engsoft.allfood.util;

public class QueryGenerator {

	private String query;

	public QueryGenerator() {
	}

	public QueryGenerator from(String table) {
		this.query = " FROM ".concat(table);
		return this;
	}

	public QueryGenerator as(String alias) {
		this.query = query.concat(" AS ").concat(alias);
		return this;
	}

	public QueryGenerator join(String table1) {
		this.query = query.concat(" JOIN ").concat(table1);
		return this;
	}
	
	public QueryGenerator inner(String table1) {
		this.query = query.concat(" INNER JOIN ").concat(table1);
		return this;
	}

	public QueryGenerator on(String field1, String field2) {
		this.query = query.concat(" ON ").concat(field1).concat(" = ").concat(field2);
		return this;
	}
	
	private String where() {
		this.query = query.concat(!query.contains("WHERE") ? " WHERE " : " AND ");
		return this.query;
	}
	
	public QueryGenerator equalsToText(String field, String value) {
		return this.equalsTo(field, "'".concat(value).concat("'"));
	}

	public QueryGenerator equalsTo(String field, String value) {
		this.query = this.where().concat(field).concat(" = ").concat(value);
		return this;
	}
	
	public QueryGenerator differentFrom(String field, String value) {
		this.query = this.where().concat(field).concat(" != ").concat(value);
		return this;
	}
	
	private QueryGenerator in(String field, String list, boolean not) {
		if (!list.startsWith("(")) list = "(".concat(list);
		if (!list.startsWith(")")) list = list.concat(")");
		this.query = this.where().concat(field).concat(not ? " NOT IN" : " IN ").concat(list);
		return this;
	}
	
	public QueryGenerator in(String field, String list) {
		return this.in(field, list, false);
	}
	
	public QueryGenerator notInList(String field, Long[] ids) {
		for (Long id : ids)
			this.differentFrom(field, id.toString());
		return this;
	}
	
	public QueryGenerator notIn(String field, String list) {
		return this.in(field, list, true);
	}

	public QueryGenerator like(String field, String value) {
		this.query = this.where().concat("LOWER(").concat(field).concat(") LIKE ").concat(value);
		return this;
	}

	public QueryGenerator orderBy(String field, boolean isDesc) {
		this.query = query.concat(" ORDER BY ").concat(field);
		this.query = query.concat(" ").concat(isDesc ? "DESC" : "ASC");
		return this;
	}

	public String select(String returnableTable) {
		String auxQuery = "SELECT ".concat(returnableTable.toLowerCase());
		System.out.println("GENERATED QUERY: " + auxQuery.concat(this.query));
		return auxQuery.concat(this.query);
	}
	
	public String selectDistinct(String returnableTable) {
		String auxQuery = "SELECT DISTINCT ".concat(returnableTable.toLowerCase());
		System.out.println("GENERATED QUERY: " + auxQuery.concat(this.query));
		return auxQuery.concat(this.query);
	}

}

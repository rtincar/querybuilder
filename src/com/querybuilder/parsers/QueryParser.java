package com.querybuilder.parsers;

import com.querybuilder.QueryBuilder;
import com.querybuilder.clausules.Order;

public class QueryParser extends AbstractQueryParser<QueryBuilder> {

	private QueryParser(QueryBuilder query) {
		super(query);
	}
	
	public static QueryParser get(QueryBuilder query) {
		return new QueryParser(query);
	}

	public void parse() {
		super.parse();
		parseOrder();
	}

	private void parseOrder() {
		Order order = query.getOrder();
		if (order != null) {
			OrderParser orderParser = OrderParser.get(order);
			orderParser.parse();
			sb.append(orderParser.getParsedString());
		} 
	}

}

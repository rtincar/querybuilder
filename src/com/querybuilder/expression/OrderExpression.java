package com.querybuilder.expression;

public class OrderExpression implements Expression{

	public String parse(QueryObject queryObject) {
		String orderBy = queryObject.getOrderBy();
		if (orderBy != null) {
			return " order by " + orderBy + " ";
		} else {
			return "";
		}
	}

}

package com.querybuilder.expression.clausules;

import com.querybuilder.expression.Expression;
import com.querybuilder.expression.QueryObject;

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

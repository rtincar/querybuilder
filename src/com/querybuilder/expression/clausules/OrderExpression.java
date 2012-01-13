package com.querybuilder.expression.clausules;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.Expression;

public class OrderExpression implements Expression{

	public String parse(QueryObject queryObject) {
		String orderBy = queryObject.getOrderBy();
		if (orderBy != null) {
			return " order by " + orderBy + " ";
		} else {
			return "";
		}
	}

	public String getExpression() {
		return " order by ? ";
	}
	
	

}

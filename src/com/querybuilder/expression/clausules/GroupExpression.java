package com.querybuilder.expression.clausules;

import com.querybuilder.expression.Expression;
import com.querybuilder.query.QueryObject;

public class GroupExpression implements Expression {

	public String parse(QueryObject queryObject) {
		String groupBy = queryObject.getGroupBy();
		if (groupBy != null) {
			return " group by " + groupBy + " ";
		} else {
			return "";
		}
	}

	public String getExpression() {
		return "group by ? ";
	}
	
	

}

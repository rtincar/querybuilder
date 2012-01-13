package com.querybuilder.expression.clausules;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.Expression;

public class GroupExpression implements Expression {

	public String parse(QueryObject queryObject) {
		String groupBy = queryObject.getGroupBy();
		if (groupBy != null) {
			return " group by " + groupBy + " ";
		} else {
			return "";
		}
	}

}

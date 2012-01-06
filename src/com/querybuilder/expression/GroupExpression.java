package com.querybuilder.expression;

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

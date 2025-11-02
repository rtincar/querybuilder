package com.querybuilder.expression.clausules;

import java.util.Iterator;

import com.querybuilder.expression.ConditionExpression;
import com.querybuilder.expression.Expression;
import com.querybuilder.query.QueryObject;

public class WhereExpression implements Expression {

	public String parse(QueryObject queryObject) {
		StringBuilder sb = new StringBuilder();
		if (!queryObject.getConditions().isEmpty()) {
			sb.append(" where ( ");
			String op = queryObject.getConditionEvaluationMode().equals(
					QueryObject.ConditionEvaluationMode.ALL) ? "and" : "or";
			Iterator<ConditionExpression> iterator = queryObject.getConditions().iterator();
			while (iterator.hasNext()) {
				ConditionExpression next = iterator.next();
				sb.append(next.parse(queryObject));
				if (iterator.hasNext()) {
					sb.append(" ").append(op).append(" ");
				}
				queryObject.addAllParameters(next.getParameters());
			}
			sb.append(" ) ");
		}
		return sb.toString();
	}

	public String getExpression() {
		return " where ( ? ) ";
	}
	
	

}

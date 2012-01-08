package com.querybuilder.expression.clausules;

import java.util.Iterator;
import java.util.Map;

import com.querybuilder.expression.Expression;
import com.querybuilder.expression.QueryObject;
import com.querybuilder.expression.conditions.ConditionExpression;

public class WhereExpression implements Expression {

	public String parse(QueryObject queryObject) {
		Map<String, Object> parameterMap = queryObject.getParameterMap();
		StringBuilder sb = new StringBuilder();
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
			parameterMap.putAll(next.getParameters());
		}
		sb.append(" ) ");
		return sb.toString();
	}

}

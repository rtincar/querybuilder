package com.querybuilder.expression;

import java.util.Iterator;
import java.util.Map;

public class WhereExpression implements Expression {

	public String parse(QueryObject queryObject) {
		Map<String, Object> parameterMap = queryObject.getParamterMap();
		StringBuilder sb = new StringBuilder();
		sb.append(" where ");
		String op = queryObject.getWhereNode().getType().equals(
				WhereNode.Type.ALL) ? "and" : "or";
		Iterator<ConditionExpression> iterator = queryObject.getWhereNode()
				.getConditions().iterator();
		while (iterator.hasNext()) {
			ConditionExpression next = iterator.next();
			sb.append(next.parse(queryObject)).append(" ").append(op).append(
					" ");
			parameterMap.putAll(next.getParameters());
		}
		sb.append(" ");
		return sb.toString();
	}

}

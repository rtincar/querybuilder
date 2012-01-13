package com.querybuilder.expression.clausules;

import java.util.Iterator;
import java.util.Map;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.ConditionExpression;
import com.querybuilder.expression.Expression;

public class HavingExpression implements Expression {

	public String parse(QueryObject queryObject) {
		Map<String, Object> parameterMap = queryObject.getParameterMap();
		StringBuilder sb = new StringBuilder();
		if (!queryObject.getHavings().isEmpty()) {
			sb.append(" having ( ");
			String op = queryObject.getConditionEvaluationMode().equals(
					QueryObject.ConditionEvaluationMode.ALL) ? "and" : "or";
			Iterator<ConditionExpression> iterator = queryObject.getHavings().iterator();
			while (iterator.hasNext()) {
				ConditionExpression next = iterator.next();
				sb.append(next.parse(queryObject)).append(" ").append(op).append(
				" ");
				parameterMap.putAll(next.getParameters());
			}
			sb.append(" ) ");
		}
		return sb.toString();
	}

	public String getExpression() {
		// TODO Auto-generated method stub
		return " having ( ? ) ";
	}
	
	

}

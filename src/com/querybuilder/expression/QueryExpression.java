package com.querybuilder.expression;

import java.util.Map;

public class QueryExpression implements Expression {
	
	private Map<String, Object> parameterMap;
	private Integer firsResult;
	private Integer maxResults;

	public String parse(QueryObject queryObject) {
		if (queryObject.getLimitNode() != null) {
			firsResult = queryObject.getLimitNode().getFirst();
			maxResults = queryObject.getLimitNode().getMax();
		}
		StringBuilder sb = new StringBuilder();
		SelectExpression select = new SelectExpression();
		WhereExpression where = new WhereExpression();
		sb.append(select.parse(queryObject));
		sb.append(where.parse(queryObject));
		
		
		parameterMap.putAll(queryObject.getParamterMap());
		return sanitizeEmptySpaces(sb.toString());
	}
	
	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}
	
	public Integer getFirstResult() {
		return firsResult;
	}
	
	public Integer getMaxResults() {
		return maxResults;
	}
	
	protected String sanitizeEmptySpaces(String s) {
		return s.replaceAll("\\s+", " ");
	}

}

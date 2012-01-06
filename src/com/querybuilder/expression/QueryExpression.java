package com.querybuilder.expression;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryExpression implements Expression {
	
	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
	private Integer firsResult;
	private Integer maxResults;

	public String parse(QueryObject queryObject) {
		firsResult = queryObject.getFirst();
		maxResults = queryObject.getMax();
		StringBuilder sb = new StringBuilder();
		sb.append(new SelectExpression().parse(queryObject));
		sb.append(new FromExpression().parse(queryObject));
		sb.append(new JoinExpression().parse(queryObject));
		sb.append(new WhereExpression().parse(queryObject));
		sb.append(new GroupExpression().parse(queryObject));
		sb.append(new HavingExpression().parse(queryObject));
		parameterMap.putAll(queryObject.getParameterMap());
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

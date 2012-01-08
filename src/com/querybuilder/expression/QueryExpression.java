package com.querybuilder.expression;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryExpression extends ParametrizedExpression {
	
	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	private Integer firsResult;
	private Integer maxResults;
	private QueryObject qo;

	public QueryExpression() {
		super();
	}
	
	public QueryExpression(QueryObject qo) {
		this.qo = qo;
	}

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
		sb.append(new OrderExpression().parse(queryObject));
		parameters.putAll(queryObject.getParameterMap());
		return sanitizeEmptySpaces(sb.toString());
	}
	
	public String parse() {
		return parse(qo);
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

	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}

}

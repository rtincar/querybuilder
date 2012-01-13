package com.querybuilder.expression;

import java.util.LinkedHashMap;
import java.util.Map;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.clausules.FromExpression;
import com.querybuilder.expression.clausules.GroupExpression;
import com.querybuilder.expression.clausules.HavingExpression;
import com.querybuilder.expression.clausules.JoinExpression;
import com.querybuilder.expression.clausules.OrderExpression;
import com.querybuilder.expression.clausules.SelectExpression;
import com.querybuilder.expression.clausules.WhereExpression;

public class QueryExpression extends ParametrizedExpression {
	
	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	private String parsedExpression;
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
		parsedExpression = sanitizeEmptySpaces(sb.toString());
		return parsedExpression;
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

	public String getExpression() {
		if (parsedExpression == null)
			parse();
		return parsedExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qo == null) ? 0 : qo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryExpression other = (QueryExpression) obj;
		if (qo == null) {
			if (other.qo != null)
				return false;
		} else if (!qo.equals(other.qo))
			return false;
		return true;
	}
	
	
	

}

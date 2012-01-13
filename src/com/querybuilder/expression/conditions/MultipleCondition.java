package com.querybuilder.expression.conditions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.ConditionExpression;

public abstract class MultipleCondition extends ConditionExpression {
	
	private List<ConditionExpression> conditions = new ArrayList<ConditionExpression>();
	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();

	public List<ConditionExpression> getConditions() {
		return conditions;
	}
	
	public void add(ConditionExpression condition) {
		conditions.add(condition);
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameterMap;
	}

	public String parse(QueryObject queryObject) {
		StringBuilder sb = new StringBuilder();
		sb.append(" ( ");
		Iterator<ConditionExpression> iterator = conditions.iterator();
		while(iterator.hasNext()) {
			ConditionExpression next = iterator.next();
			sb.append(next.parse(queryObject));
			if (iterator.hasNext()) {
				sb.append(" ").append(getOp()).append(" ");
			}
			parameterMap.putAll(next.getParameters());
		}
		
		sb.append(" ) "); 
		return sb.toString();
	}
	
	protected abstract String getOp();

	public String getExpression() {
		// TODO Auto-generated method stub
		return " ( ? ) ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result
				+ ((parameterMap == null) ? 0 : parameterMap.hashCode());
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
		MultipleCondition other = (MultipleCondition) obj;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (parameterMap == null) {
			if (other.parameterMap != null)
				return false;
		} else if (!parameterMap.equals(other.parameterMap))
			return false;
		return true;
	}
	
	
	
	

}

package com.querybuilder.expression.conditions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.ConditionExpression;

public class NotCondition extends ConditionExpression {
	
	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>(0);
	
	private ConditionExpression condition;
	
	public NotCondition(ConditionExpression condition) {
		this.condition = condition;
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameterMap;
	}

	public String parse(QueryObject queryObject) {
		String parse = condition.parse(queryObject);
		parameterMap.putAll(condition.getParameters());
		return " !( " + parse + " ) ";
	}

	public String getExpression() {
		return " !( ? ) ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
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
		NotCondition other = (NotCondition) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (parameterMap == null) {
			if (other.parameterMap != null)
				return false;
		} else if (!parameterMap.equals(other.parameterMap))
			return false;
		return true;
	}
	
	
	
	

}

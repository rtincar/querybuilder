package com.querybuilder.expression.conditions;

import java.util.HashMap;
import java.util.Map;

import com.querybuilder.QueryObject;

public class NotCondition extends ConditionExpression {
	
	private Map<String, Object> parameterMap = new HashMap<String, Object>(0);
	
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

}

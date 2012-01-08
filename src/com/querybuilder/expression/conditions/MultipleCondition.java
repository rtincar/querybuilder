package com.querybuilder.expression.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.querybuilder.expression.QueryObject;

public abstract class MultipleCondition extends ConditionExpression {
	
	private List<ConditionExpression> conditions = new ArrayList<ConditionExpression>();
	private Map<String, Object> parameterMap = new HashMap<String, Object>();

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

}

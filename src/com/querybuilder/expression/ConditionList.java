package com.querybuilder.expression;

import java.util.ArrayList;
import java.util.List;

import com.querybuilder.expression.conditions.ConditionExpression;

public class ConditionList {
	
	private List<ConditionExpression> list = new ArrayList<ConditionExpression>();
	
	private ConditionList() {
		
	}
	
	public static ConditionList init() {
		return new ConditionList();
	}
	
	public ConditionList add(ConditionExpression conditionExpression) {
		list.add(conditionExpression);
		return this;
	}
	
	public List<ConditionExpression> getConditions() {
		return list;
	}

}

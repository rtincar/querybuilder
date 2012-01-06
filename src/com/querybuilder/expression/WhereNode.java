package com.querybuilder.expression;

import java.util.ArrayList;
import java.util.List;

public class WhereNode extends Node {
	
	List<ConditionExpression> conditions = new ArrayList<ConditionExpression>();

	
	public void add(ConditionExpression condition) {
		conditions.add(condition);
	}
	
	public List<ConditionExpression> getConditions() {
		return conditions;
	}
	


}

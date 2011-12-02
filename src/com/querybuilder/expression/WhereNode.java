package com.querybuilder.expression;

import java.util.ArrayList;
import java.util.List;

public class WhereNode extends Node {
	
	List<ConditionExpression> conditions = new ArrayList<ConditionExpression>();

	private WhereNode.Type type = WhereNode.Type.ALL;
	
	public List<ConditionExpression> getConditions() {
		QueryCreator.init(null).addCondition(null).addCondition(null).addFrom(getClass(), "a").addSelection("a");
		return conditions;
	}
	
	public WhereNode.Type getType() {
		return type;
	}
	
	public void setType(WhereNode.Type type) {
		this.type = type;
	}
	
	public enum Type {
		ALL, ANY
	}

}

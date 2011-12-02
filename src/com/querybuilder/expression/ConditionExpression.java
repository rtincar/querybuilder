package com.querybuilder.expression;

import java.util.Map;

public abstract class ConditionExpression implements Expression {
	public abstract Map<String, Object> getParameters();
}

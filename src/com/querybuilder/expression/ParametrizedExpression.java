package com.querybuilder.expression;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class ParametrizedExpression implements Expression {
	final String PARAM_NAME = "e";
	final String PARAM_PLACEHOLDER = "?";
	final Pattern PARAM_PATTERN = Pattern.compile("\\?");
	public abstract Map<String, Object> getParameters();

}

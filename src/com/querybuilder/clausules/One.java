package com.querybuilder.clausules;

public class One implements Condition {
	
	private String expression;
	private Object[] arguments;
	
	private One(String expression, Object[] arguments){
		this.expression = expression;
		this.arguments = arguments;
	}

	public String getExpression() {
		return expression;
	}
	
	public Object[] getArguments() {
		return this.arguments;
	}
	
	public static One that(String expression, Object...arguments) {
		return new One(expression, arguments);
	}
	


}

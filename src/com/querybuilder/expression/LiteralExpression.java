package com.querybuilder.expression;

import com.querybuilder.QueryObject;

public class LiteralExpression implements Expression {
	
	private String literal;
	
	public LiteralExpression(String literal) {
		this.literal = literal;
	}

	public String parse(QueryObject queryObject) {
		return literal;
	}

}

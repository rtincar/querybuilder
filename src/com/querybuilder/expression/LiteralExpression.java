package com.querybuilder.expression;

import com.querybuilder.query.QueryObject;

public class LiteralExpression implements Expression {
	
	private String literal;
	
	public LiteralExpression(String literal) {
		this.literal = literal;
	}

	public String parse(QueryObject queryObject) {
		return literal;
	}

	public String getExpression() {
		return literal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((literal == null) ? 0 : literal.hashCode());
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
		LiteralExpression other = (LiteralExpression) obj;
		if (literal == null) {
			if (other.literal != null)
				return false;
		} else if (!literal.equals(other.literal))
			return false;
		return true;
	}
	
	
	
	

}

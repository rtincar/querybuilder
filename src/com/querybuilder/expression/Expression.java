package com.querybuilder.expression;

import com.querybuilder.QueryObject;

public interface Expression {
	
	public String parse(QueryObject queryObject);
	public String getExpression();

}

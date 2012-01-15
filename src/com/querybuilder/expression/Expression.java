package com.querybuilder.expression;

import com.querybuilder.query.QueryObject;

public interface Expression {
	
	public String parse(QueryObject queryObject);
	public String getExpression();

}

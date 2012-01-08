package com.querybuilder.expression;

import com.querybuilder.expression.clausules.SelectExpression;

public interface ExpresionProcessor {
	public void parseSelect(SelectExpression selectExpression);
	

}

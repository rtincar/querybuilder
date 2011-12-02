package com.querybuilder.expression;


public class AnyCondition extends MultipleCondition {

	@Override
	protected String getOp() {
		return "or";
	}
	


}

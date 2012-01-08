package com.querybuilder.expression.conditions;


public class AnyCondition extends MultipleCondition {

	@Override
	protected String getOp() {
		return "or";
	}
	


}

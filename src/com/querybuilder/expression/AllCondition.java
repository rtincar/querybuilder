package com.querybuilder.expression;

public class AllCondition extends MultipleCondition {

	@Override
	protected String getOp() {
		return "and";
	}

}

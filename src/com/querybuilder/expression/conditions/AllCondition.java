package com.querybuilder.expression.conditions;

public class AllCondition extends MultipleCondition {

	@Override
	protected String getOp() {
		return "and";
	}

}

package com.querybuilder.expression;

import com.querybuilder.expression.conditions.AllCondition;
import com.querybuilder.expression.conditions.AnyCondition;
import com.querybuilder.expression.conditions.ConditionExpression;
import com.querybuilder.expression.conditions.SimpleCondition;

/**
 * Simple builder para crear condiciones
 * 
 * @author rtincar
 *
 */
public class Conditions {
	
	public static SimpleCondition one(String condition, Expression...args) {
		return new SimpleCondition(condition, args);
	}
	
	public static AnyCondition any(ConditionExpression...conditionExpressions) {
		AnyCondition any = new AnyCondition();
		for (ConditionExpression ce : conditionExpressions) {
			any.add(ce);
		}
		return any;
	}
	
	public static AllCondition all(ConditionExpression...conditionExpressions) {
		AllCondition all = new AllCondition();
		for (ConditionExpression ce : conditionExpressions) {
			all.add(ce);
		}
		return all;
	}
	
	public static AnyCondition any(ConditionList conditionList) {
		AnyCondition any = new AnyCondition();
		for (ConditionExpression ce : conditionList.getConditions()) {
			any.add(ce);
		}
		return any;
	}
	
	public static AllCondition all(ConditionList conditionList) {
		AllCondition all = new AllCondition();
		for (ConditionExpression ce : conditionList.getConditions()) {
			all.add(ce);
		}
		return all;
	}
	
	

}

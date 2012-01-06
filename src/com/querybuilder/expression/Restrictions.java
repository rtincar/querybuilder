package com.querybuilder.expression;

public class Restrictions {

	
	public static final ConditionExpression eq(String prop, Object val) {
		return new SimpleCondition(prop + " = ?", val);
	}
	
	public static final ConditionExpression neq(String prop, Object val) {
		return new SimpleCondition(prop + " != ?", val);
	}
	
	public static final ConditionExpression gt(String prop, Object val) {
		return new SimpleCondition(prop + " > ?", val);
	}
	
	public static final ConditionExpression ge(String prop, Object val) {
		return new SimpleCondition(prop + " >= ?", val);
	}
	
	public static final ConditionExpression lt(String prop, Object val) {
		return new SimpleCondition(prop + " < ?", val);
	}
	
	public static final ConditionExpression le(String prop, Object val) {
		return new SimpleCondition(prop + " <= ?", val);
	}
	
	public static final ConditionExpression between(String prop, Object val1, Object val2) {
		return new SimpleCondition(prop + " between ? and ?", new Object[]{val1, val2});
	}
	
	public static final ConditionExpression notbetween(String prop, Object val1, Object val2) {
		return new SimpleCondition(prop + " not between ? and ?", new Object[]{val1, val2});
	}
	
	public static final ConditionExpression in(String prop, Object...val) {
		return new SimpleCondition(prop + " in ( ? )", val);
	}
	
	public static final ConditionExpression nin(String prop, Object...val) {
		return new SimpleCondition(prop + " not in ( ? )", val);
	}
	
	public static final ConditionExpression isNull(String prop) {
		return new SimpleCondition(prop + " is null");
	}
	
	public static final ConditionExpression isNoNull(String prop) {
		return new SimpleCondition(prop + " is not null");
	}
	
	public static final ConditionExpression like(String prop, String value) {
		return new SimpleCondition(prop + " like '%'||?||'%'", value);
	}
	
	public static final ConditionExpression ilike(String prop, String value) {
		return new SimpleCondition("lower(" + prop + ") like lower('%'||?||'%')", value);
	}
	
	public static final ConditionExpression notlike(String prop, String value) {
		return new SimpleCondition(prop + " not like '%'||?||'%'", value);
	}
	
	public static final ConditionExpression notilike(String prop, String value) {
		return new SimpleCondition("lower(" + prop + ") not like lower('%'||?||'%')", value);
	}
	
	public static final ConditionExpression not(ConditionExpression condition) {
		return new NotCondition(condition);
	}
	
	public static final ConditionExpression all(ConditionExpression...arg) {
		AllCondition a = new AllCondition();
		for (ConditionExpression c : arg) {
			a.add(c);
		}
		return a;
	}
	
	public static final ConditionExpression any(ConditionExpression...arg) {
		AnyCondition a = new AnyCondition();
		for (ConditionExpression c : arg) {
			a.add(c);
		}
		return a;
	}
}

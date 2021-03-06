package com.querybuilder.expression;

import com.querybuilder.expression.conditions.AllCondition;
import com.querybuilder.expression.conditions.AnyCondition;
import com.querybuilder.expression.conditions.NotCondition;
import com.querybuilder.expression.conditions.SimpleCondition;
import com.querybuilder.query.From;
import com.querybuilder.query.Join;
import com.querybuilder.query.QueryObject;
import com.querybuilder.query.Select;


/**
 * Clase que sirve como factoria de todas las expresiones
 * 
 * @author rtincar
 *
 */
public class ExpressionFactory {
	
	/************ EXPRESIONES DE CONDICIONES *************************/
	
	public static final SimpleCondition eq(String prop, Expression val) {
		return new SimpleCondition(prop + " = ?", val);
	}
	
	public static final SimpleCondition neq(String prop, Expression val) {
		return new SimpleCondition(prop + " != ?", val);
	}
	
	public static final SimpleCondition gt(String prop, Expression val) {
		return new SimpleCondition(prop + " > ?", val);
	}
	
	public static final SimpleCondition ge(String prop, Expression val) {
		return new SimpleCondition(prop + " >= ?", val);
	}
	
	public static final SimpleCondition lt(String prop, Expression val) {
		return new SimpleCondition(prop + " < ?", val);
	}
	
	public static final SimpleCondition le(String prop, Expression val) {
		return new SimpleCondition(prop + " <= ?", val);
	}
	
	public static final SimpleCondition between(String prop, Expression val1, Expression val2) {
		return new SimpleCondition(prop + " between ? and ?", new Expression[]{val1, val2});
	}
	
	public static final SimpleCondition in(String prop, Expression...val) {
		return new SimpleCondition(prop + " in ( ? )", val);
	}
	
	public static final SimpleCondition notin(String prop, Expression...val) {
		return new SimpleCondition(prop + " not in ( ? )", val);
	}
	
	public static final SimpleCondition isNull(String prop) {
		return new SimpleCondition(prop + " is null");
	}
	
	public static final SimpleCondition isNoNull(String prop) {
		return new SimpleCondition(prop + " is not null");
	}
	
	public static final SimpleCondition like(String prop, Expression value) {
		return new SimpleCondition(prop + " like ?", value);
	}
	
	public static final SimpleCondition ilike(String prop, Expression value) {
		return new SimpleCondition("lower(" + prop + ") like lower(?)", value);
	}
	/*  */
	
	public static final SimpleCondition eq(String prop, Object val) {
		return new SimpleCondition(prop + " = ?", value(val));
	}
	
	public static final SimpleCondition neq(String prop, Object val) {
		return new SimpleCondition(prop + " != ?", value(val));
	}
	
	public static final SimpleCondition gt(String prop, Object val) {
		return new SimpleCondition(prop + " > ?", value(val));
	}
	
	public static final SimpleCondition ge(String prop, Object val) {
		return new SimpleCondition(prop + " >= ?", value(val));
	}
	
	public static final SimpleCondition lt(String prop, Object val) {
		return new SimpleCondition(prop + " < ?", value(val));
	}
	
	public static final SimpleCondition le(String prop, Object val) {
		return new SimpleCondition(prop + " <= ?", value(val));
	}
	
	public static final SimpleCondition between(String prop, Object val1, Object val2) {
		return new SimpleCondition(prop + " between ? and ?", new Expression[]{value(val1), value(val2)});
	}
	
	public static final SimpleCondition in(String prop, Object...val) {
		return new SimpleCondition(prop + " in ( ? )", value(val));
	}
	
	public static final SimpleCondition notin(String prop, Object...val) {
		return new SimpleCondition(prop + " not in ( ? )", value(val));
	}
	
	public static final SimpleCondition like(String prop, Object value) {
		return new SimpleCondition(prop + " like ?", value(value));
	}
	
	public static final SimpleCondition ilike(String prop, Object value) {
		return new SimpleCondition("lower(" + prop + ") like lower(?)", value(value));
	}
	
	public static final NotCondition not(ConditionExpression condition) {
		return new NotCondition(condition);
	}
	
	public static final AllCondition all(ConditionExpression...arg) {
		AllCondition a = new AllCondition();
		for (ConditionExpression c : arg) {
			a.add(c);
		}
		return a;
	}
	
	public static final AnyCondition any(ConditionExpression...arg) {
		AnyCondition a = new AnyCondition();
		for (ConditionExpression c : arg) {
			a.add(c);
		}
		return a;
	}
	
	/****************** EXPRESIONES DE FUNCIONES ************************/
	
	public static final FunctionExpression avg(String path) {
		return new FunctionExpression("avg(" + path +")");
	}

	public static final FunctionExpression sum(String path) {
		return new FunctionExpression("sum(" + path +")");
	}

	public static final FunctionExpression min(String path) {
		return new FunctionExpression("min(" + path +")");
	}

	public static final FunctionExpression max(String path) {
		return new FunctionExpression("max(" + path +")");
	}

	public static final FunctionExpression currentDate() {
		return new FunctionExpression("current_date()");
	}

	public static final FunctionExpression currentTime() {
		return new FunctionExpression("current_time()");
	}

	public static final FunctionExpression currentTimestamp() {
		return new FunctionExpression("current_timestamp()");
	}

	public static final FunctionExpression count(String path) {
		return new FunctionExpression("count(" + path + ")");
	}

	public static final FunctionExpression countAll() {
		return new FunctionExpression("count(*)");
	}

	public static final FunctionExpression countDistinct(String...paths) {
		StringBuilder sb = new StringBuilder();
		sb.append("count(distinct ");
		for (int i = 0; i < paths.length; i++) {
			sb.append(paths[i]);
			if (i < (paths.length - 1)) {
				sb.append(",");
			}
		}
		sb.append(")");
		return new FunctionExpression(sb.toString());
		
	}

	public static final FunctionExpression upper(Expression exp) {
		return new FunctionExpression("upper(?)", exp);
	}

	public static final FunctionExpression lower(Expression exp) {
		return new FunctionExpression("lower(?)", exp);
	}

	public static final FunctionExpression concat(Expression...elements) {
		if (elements.length < 2)
			throw new IllegalArgumentException("Se deben indicar al menos dos expresiones que concatenar");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			sb.append("?");
			if (i < (elements.length - 1)) {
				sb.append("||");
			}
		}
		return new FunctionExpression(sb.toString(), elements);
		
	}
	
	public static final PathExpression path(String path) {
		return new PathExpression(path);
	}
	
	public static final LiteralExpression literal(String path) {
		return new LiteralExpression(path);
	}
	
	public static final ValueExpression value(Object value) {
		return new ValueExpression(value);
	}
	
	public static final Select get(Expression exp, String alias) {
		return new Select(exp.parse(new QueryObject()), alias);
	}
	
	public static final Select get(String exp, String alias) {
		return get(new PathExpression(exp), alias);
	}
	
	public static final From entity(Class<?> clazz, String alias) {
		return new From(clazz, alias);
	}
	
	public static final Join joinTo(String path, String alias, QueryObject.JoinType type) {
		return new Join(path, alias, type);
	}

}

package com.querybuilder.expression;

import static com.querybuilder.expression.ExpressionFactory.all;
import static com.querybuilder.expression.ExpressionFactory.any;
import static com.querybuilder.expression.ExpressionFactory.entity;
import static com.querybuilder.expression.ExpressionFactory.eq;
import static com.querybuilder.expression.ExpressionFactory.get;
import static com.querybuilder.expression.ExpressionFactory.gt;
import static com.querybuilder.expression.ExpressionFactory.in;
import static com.querybuilder.expression.ExpressionFactory.isNoNull;
import static com.querybuilder.expression.ExpressionFactory.isNull;
import static com.querybuilder.expression.ExpressionFactory.joinTo;
import static com.querybuilder.expression.ExpressionFactory.like;
import static com.querybuilder.expression.ExpressionFactory.path;
import static com.querybuilder.expression.ExpressionFactory.value;
import static com.querybuilder.expression.ExpressionFactory.count;
import static com.querybuilder.expression.ExpressionFactory.sum;

import java.util.Arrays;
import java.util.Date;

import com.querybuilder.Join;
import com.querybuilder.QueryCreator;
import com.querybuilder.QueryObject;
import com.querybuilder.QueryObject.JoinType;
import com.querybuilder.expression.conditions.SimpleCondition;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		ValueExpression v1 = new ValueExpression(new Object[]{"34", "45"});
		ValueExpression v2 = new ValueExpression(new Object[]{"34", "45"});
		System.out.println(v1.equals(v2));
		
		SimpleCondition in1 = in("prop", value(new Object[]{23L,34L}));
		SimpleCondition in2 = in("prop", value(new Object[]{23L,34L}));
		
		Date d = new Date();
		
		ConditionExpression any = any(
				isNull("psadasd"),
				isNoNull("adasd"),
				gt("asda", d)
		);
		
		ConditionExpression any2 = any(
				isNull("psadasd"),
				isNoNull("adasd"),
				gt("asda", d)
		);

		System.out.println(in1.equals(in2));
		ConditionExpression all = all(eq("prop2", 45), in("a.prop3", Arrays
				.asList(23, 24, 25)), isNull("prop5"), like("prop6", "jkjks"), any);

		ConditionExpression all2 = all(eq("prop2", 45), in("a.prop3", Arrays
				.asList(23, 24, 25)), isNull("prop5"), like("prop6", "jkjks"), any2);
		
		System.out.println(all.equals(all2));
		
		QueryCreator q1 = QueryCreator.init(null);
		q1.select(
			get(path("a.sadds"), "asd"),
			get(path("a.dads"), "dasd"),
			get(count("s.asda"), "cuenta"),
			get(sum("s.asda"), "suma")
		).from(
			entity(Join.class, "j")
		).join(
			joinTo("a.per", "as", JoinType.LEFT),
			joinTo("sd.sda", "asd2", JoinType.LEFT)
		).whereAll(
			eq("sad", "asda"),
			eq("asda", 45),
			eq("adsa", 3),
			eq("asda.asd", 67L),
			in("hg", Arrays.asList(23,34,56))
		).orderBy(
			"sad.sda desc", 
			"sd.as asc"
		).groupBy(
			"sad.dasd", 
			"das.asd"
		);
		QueryObject qo1 = q1.getQueryObject();
		
		
		QueryCreator q2 = QueryCreator.init(null);
		q2.select(
			get(path("a.sadds"), "asd"),
			get(path("a.dads"), "dasd"),
			get(count("s.asda"), "cuenta")
		).from(
			entity(Join.class, "j")
		).join(
			joinTo("a.per", "as", JoinType.LEFT),
			joinTo("sd.sda", "asd2", JoinType.LEFT)
		).whereAll(
			eq("sad", "asda"),
			eq("asda", 45),
			eq("adsa", 3),
			eq("asda.asd", 67L),
			in("hg", Arrays.asList(23,34,56))
		).orderBy(
			"sad.sda desc", 
			"sd.as asc"
		).groupBy(
			"sad.dasd", 
			"das.asd"
		);
		QueryObject qo2 = q2.getQueryObject();
		
		System.out.println(qo1.equals(qo2));
		QueryExpression qe1 = new QueryExpression(qo1);
		QueryExpression qe2 = new QueryExpression(qo2);
		String parsed1 = qe1.parse();
		String parsed2 = qe2.parse();
		System.out.println(parsed1);
		System.out.println(qo1.getParameterMap());
		System.out.println(parsed2);
		System.out.println(qo2.getParameterMap());
		System.out.println(parsed1.equals(parsed2));
	}

}

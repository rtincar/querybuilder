package com.querybuilder.expression;

import static com.querybuilder.expression.ExpressionFactory.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.querybuilder.QueryCreator;
import com.querybuilder.expression.conditions.SimpleCondition;
import com.querybuilder.query.Join;
import com.querybuilder.query.QueryObject;
import com.querybuilder.query.QueryObject.JoinType;

/**
 * Tests para funcionalidad general de QueryBuilder.
 * Reemplaza el antiguo Test.java que usaba System.out.println.
 */
public class QueryBuilderTest {

	@Test
	public void testValueExpressionEquality() {
		ValueExpression v1 = new ValueExpression(new Object[]{"34", "45"});
		ValueExpression v2 = new ValueExpression(new Object[]{"34", "45"});

		assertEquals("ValueExpression con arrays iguales debe ser equals", v1, v2);
	}

	@Test
	public void testSimpleConditionEquality() {
		SimpleCondition in1 = in("prop", value(new Object[]{23L, 34L}));
		SimpleCondition in2 = in("prop", value(new Object[]{23L, 34L}));

		assertEquals("SimpleCondition con mismos parámetros debe ser equals", in1, in2);
	}

	@Test
	public void testAnyConditionEquality() {
		Date d = new Date();

		ConditionExpression any1 = any(
			isNull("psadasd"),
			isNoNull("adasd"),
			gt("asda", d)
		);

		ConditionExpression any2 = any(
			isNull("psadasd"),
			isNoNull("adasd"),
			gt("asda", d)
		);

		assertEquals("AnyCondition con mismas condiciones debe ser equals", any1, any2);
	}

	@Test
	public void testAllConditionEquality() {
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

		ConditionExpression all1 = all(
			eq("prop2", 45),
			in("a.prop3", Arrays.asList(23, 24, 25)),
			isNull("prop5"),
			like("prop6", "jkjks"),
			any
		);

		ConditionExpression all2 = all(
			eq("prop2", 45),
			in("a.prop3", Arrays.asList(23, 24, 25)),
			isNull("prop5"),
			like("prop6", "jkjks"),
			any2
		);

		assertEquals("AllCondition con mismas condiciones debe ser equals", all1, all2);
	}

	@Test
	public void testQueryObjectEquality() {
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
			in("hg", Arrays.asList(23, 34, 56))
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
			in("hg", Arrays.asList(23, 34, 56))
		).orderBy(
			"sad.sda desc",
			"sd.as asc"
		).groupBy(
			"sad.dasd",
			"das.asd"
		);
		QueryObject qo2 = q2.getQueryObject();

		assertEquals("QueryObjects iguales deben ser equals", qo1, qo2);
	}

	@Test
	public void testQueryExpressionEquality() {
		QueryCreator q1 = QueryCreator.init(null);
		q1.select(
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
			in("hg", Arrays.asList(23, 34, 56))
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
			in("hg", Arrays.asList(23, 34, 56))
		).orderBy(
			"sad.sda desc",
			"sd.as asc"
		).groupBy(
			"sad.dasd",
			"das.asd"
		);
		QueryObject qo2 = q2.getQueryObject();

		QueryExpression qe1 = new QueryExpression(qo1);
		QueryExpression qe2 = new QueryExpression(qo2);

		String parsed1 = qe1.parse();
		String parsed2 = qe2.parse();

		assertEquals("Queries iguales deben generar mismo SQL", parsed1, parsed2);
		assertEquals("QueryExpression iguales deben ser equals", qe1, qe2);
	}

	@Test
	public void testQueryExpressionParametersMatch() {
		QueryCreator q1 = QueryCreator.init(null);
		q1.select(
			get(path("a.name"), "name")
		).from(
			entity(Join.class, "j")
		).whereAll(
			eq("j.id", 123),
			in("j.status", Arrays.asList("active", "pending"))
		);

		QueryObject qo1 = q1.getQueryObject();
		QueryExpression qe1 = new QueryExpression(qo1);
		qe1.parse();

		assertNotNull("Debe tener parámetros", qe1.getParameters());
		assertFalse("Debe tener al menos un parámetro", qe1.getParameters().isEmpty());
		assertTrue("Debe contener parámetro e0", qe1.getParameters().containsKey("e0"));
	}
}

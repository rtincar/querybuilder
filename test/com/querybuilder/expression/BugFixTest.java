package com.querybuilder.expression;

import static com.querybuilder.expression.ExpressionFactory.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.querybuilder.expression.clausules.HavingExpression;
import com.querybuilder.expression.clausules.JoinExpression;
import com.querybuilder.expression.clausules.WhereExpression;
import com.querybuilder.query.Join;
import com.querybuilder.query.QueryObject;
import com.querybuilder.query.QueryObject.JoinType;

/**
 * Tests para verificar que los bugs críticos fueron corregidos.
 */
public class BugFixTest {

	/**
	 * Bug corregido: HavingExpression agregaba operador al final.
	 * Antes generaba: "HAVING ( condition1 AND condition2 AND )"
	 * Ahora genera: "HAVING ( condition1 AND condition2 )"
	 */
	@Test
	public void testHavingExpressionNoTrailingOperator() {
		QueryObject qo = new QueryObject();
		qo.setConditionEvaluationMode(QueryObject.ConditionEvaluationMode.ALL);
		qo.getHavingsInternal().add(gt("count(id)", 5));
		qo.getHavingsInternal().add(lt("sum(amount)", 1000));

		HavingExpression havingExpr = new HavingExpression();
		String result = havingExpr.parse(qo);

		assertNotNull("HAVING expression no debe ser nulo", result);
		assertFalse("No debe terminar con 'AND '", result.trim().endsWith("AND"));
		assertFalse("No debe terminar con 'OR '", result.trim().endsWith("OR"));
		assertTrue("Debe contener 'having ('", result.contains("having ("));
		assertTrue("Debe contener ' ) '", result.contains(" ) "));
		assertTrue("Debe contener 'and' entre condiciones", result.contains(" and "));
	}

	/**
	 * Bug corregido: HavingExpression con una sola condición.
	 */
	@Test
	public void testHavingExpressionSingleCondition() {
		QueryObject qo = new QueryObject();
		qo.getHavingsInternal().add(gt("count(id)", 5));

		HavingExpression havingExpr = new HavingExpression();
		String result = havingExpr.parse(qo);

		assertNotNull("HAVING expression no debe ser nulo", result);
		assertFalse("No debe terminar con 'AND '", result.trim().endsWith("AND"));
		assertFalse("No debe terminar con 'OR '", result.trim().endsWith("OR"));
	}

	/**
	 * Bug corregido: WhereExpression debe manejar condiciones vacías.
	 * Antes generaba: "WHERE ( )"
	 * Ahora genera: "" (string vacío)
	 */
	@Test
	public void testWhereExpressionEmptyConditions() {
		QueryObject qo = new QueryObject();
		// No agregamos condiciones

		WhereExpression whereExpr = new WhereExpression();
		String result = whereExpr.parse(qo);

		assertNotNull("WHERE expression no debe ser nulo", result);
		assertEquals("Debe retornar string vacío cuando no hay condiciones", "", result);
		assertFalse("No debe contener 'where'", result.contains("where"));
	}

	/**
	 * WhereExpression con condiciones debe funcionar correctamente.
	 */
	@Test
	public void testWhereExpressionWithConditions() {
		QueryObject qo = new QueryObject();
		qo.setConditionEvaluationMode(QueryObject.ConditionEvaluationMode.ALL);
		qo.getConditionsInternal().add(eq("name", "test"));
		qo.getConditionsInternal().add(gt("age", 18));

		WhereExpression whereExpr = new WhereExpression();
		String result = whereExpr.parse(qo);

		assertNotNull("WHERE expression no debe ser nulo", result);
		assertTrue("Debe contener 'where ('", result.contains("where ("));
		assertTrue("Debe contener 'and' entre condiciones", result.contains(" and "));
		assertFalse("No debe terminar con 'AND '", result.trim().endsWith("AND"));
	}

	/**
	 * Bug corregido: JoinExpression usaba múltiples if en lugar de if-else-if.
	 * Esto es más una mejora de eficiencia que un bug funcional.
	 */
	@Test
	public void testJoinExpressionTypes() {
		// Test INNER JOIN
		QueryObject qo1 = new QueryObject();
		Join innerJoin = new Join();
		innerJoin.setPath("a.users");
		innerJoin.setAlias("u");
		innerJoin.setJoinType(JoinType.INNER);
		qo1.getJoinsInternal().add(innerJoin);

		JoinExpression joinExpr1 = new JoinExpression();
		String result1 = joinExpr1.parse(qo1);

		assertTrue("Debe contener 'inner join'", result1.contains("inner join"));
		assertFalse("No debe contener 'left join'", result1.contains("left join"));
		assertFalse("No debe contener 'full join'", result1.contains("full join"));

		// Test LEFT JOIN
		QueryObject qo2 = new QueryObject();
		Join leftJoin = new Join();
		leftJoin.setPath("b.orders");
		leftJoin.setAlias("o");
		leftJoin.setJoinType(JoinType.LEFT);
		qo2.getJoinsInternal().add(leftJoin);

		JoinExpression joinExpr2 = new JoinExpression();
		String result2 = joinExpr2.parse(qo2);

		assertTrue("Debe contener 'left join'", result2.contains("left join"));
		assertFalse("No debe contener 'inner join'", result2.contains("inner join"));
		assertFalse("No debe contener 'full join'", result2.contains("full join"));

		// Test FULL JOIN
		QueryObject qo3 = new QueryObject();
		Join fullJoin = new Join();
		fullJoin.setPath("c.products");
		fullJoin.setAlias("p");
		fullJoin.setJoinType(JoinType.FULL);
		qo3.getJoinsInternal().add(fullJoin);

		JoinExpression joinExpr3 = new JoinExpression();
		String result3 = joinExpr3.parse(qo3);

		assertTrue("Debe contener 'full join'", result3.contains("full join"));
		assertFalse("No debe contener 'inner join'", result3.contains("inner join"));
		assertFalse("No debe contener 'left join'", result3.contains("left join"));
	}

	/**
	 * Test para verificar que HAVING funciona con modo ANY (OR).
	 */
	@Test
	public void testHavingExpressionWithAnyMode() {
		QueryObject qo = new QueryObject();
		qo.setConditionEvaluationMode(QueryObject.ConditionEvaluationMode.ANY);
		qo.getHavingsInternal().add(gt("count(id)", 5));
		qo.getHavingsInternal().add(lt("sum(amount)", 1000));

		HavingExpression havingExpr = new HavingExpression();
		String result = havingExpr.parse(qo);

		assertNotNull("HAVING expression no debe ser nulo", result);
		assertTrue("Debe contener 'or' entre condiciones", result.contains(" or "));
		assertFalse("No debe terminar con 'OR '", result.trim().endsWith("OR"));
	}

	/**
	 * Test para verificar que WHERE funciona con modo ANY (OR).
	 */
	@Test
	public void testWhereExpressionWithAnyMode() {
		QueryObject qo = new QueryObject();
		qo.setConditionEvaluationMode(QueryObject.ConditionEvaluationMode.ANY);
		qo.getConditionsInternal().add(eq("name", "test"));
		qo.getConditionsInternal().add(eq("name", "demo"));

		WhereExpression whereExpr = new WhereExpression();
		String result = whereExpr.parse(qo);

		assertNotNull("WHERE expression no debe ser nulo", result);
		assertTrue("Debe contener 'or' entre condiciones", result.contains(" or "));
		assertFalse("No debe terminar con 'OR '", result.trim().endsWith("OR"));
	}
}

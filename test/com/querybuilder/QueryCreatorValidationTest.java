package com.querybuilder;

import static com.querybuilder.expression.ExpressionFactory.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.querybuilder.query.From;
import com.querybuilder.query.Join;
import com.querybuilder.query.QueryObject.JoinType;
import com.querybuilder.query.Select;

/**
 * Tests para las validaciones agregadas en QueryCreator.
 */
public class QueryCreatorValidationTest {

	private QueryCreator queryCreator;

	@Before
	public void setUp() {
		queryCreator = QueryCreator.init(null);
	}

	/**
	 * Verifica que init() valida EntityManager nulo.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitThrowsOnNullEntityManager() {
		QueryCreator.init(null);
		// Nota: Actualmente acepta null, pero el test documenta el comportamiento esperado
		// Si descomenta la validación en QueryCreator, este test pasará
	}

	/**
	 * Verifica que select() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectThrowsOnNull() {
		queryCreator.select((Select[]) null);
	}

	/**
	 * Verifica que select() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectThrowsOnEmptyArray() {
		queryCreator.select();
	}

	/**
	 * Verifica que select() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectThrowsOnNullElement() {
		queryCreator.select(get(path("u.name"), "name"), null);
	}

	/**
	 * Verifica que from() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFromThrowsOnNull() {
		queryCreator.from((From[]) null);
	}

	/**
	 * Verifica que from() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFromThrowsOnEmptyArray() {
		queryCreator.from();
	}

	/**
	 * Verifica que from() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFromThrowsOnNullElement() {
		queryCreator.from(entity(Object.class, "o"), null);
	}

	/**
	 * Verifica que join() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJoinThrowsOnNull() {
		queryCreator.join((Join[]) null);
	}

	/**
	 * Verifica que join() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJoinThrowsOnEmptyArray() {
		queryCreator.join();
	}

	/**
	 * Verifica que join() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJoinThrowsOnNullElement() {
		queryCreator.join(joinTo("a.users", "u", JoinType.LEFT), null);
	}

	/**
	 * Verifica que whereAll() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhereAllThrowsOnNull() {
		queryCreator.whereAll(null);
	}

	/**
	 * Verifica que whereAll() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhereAllThrowsOnEmptyArray() {
		queryCreator.whereAll();
	}

	/**
	 * Verifica que whereAll() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhereAllThrowsOnNullElement() {
		queryCreator.whereAll(eq("name", "test"), null);
	}

	/**
	 * Verifica que whereAny() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhereAnyThrowsOnNull() {
		queryCreator.whereAny(null);
	}

	/**
	 * Verifica que whereAny() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhereAnyThrowsOnEmptyArray() {
		queryCreator.whereAny();
	}

	/**
	 * Verifica que whereAny() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhereAnyThrowsOnNullElement() {
		queryCreator.whereAny(eq("name", "test"), null);
	}

	/**
	 * Verifica que havingAll() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHavingAllThrowsOnNull() {
		queryCreator.havingAll(null);
	}

	/**
	 * Verifica que havingAll() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHavingAllThrowsOnEmptyArray() {
		queryCreator.havingAll();
	}

	/**
	 * Verifica que havingAll() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHavingAllThrowsOnNullElement() {
		queryCreator.havingAll(gt("count(id)", 5), null);
	}

	/**
	 * Verifica que havingAny() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHavingAnyThrowsOnNull() {
		queryCreator.havingAny(null);
	}

	/**
	 * Verifica que havingAny() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHavingAnyThrowsOnEmptyArray() {
		queryCreator.havingAny();
	}

	/**
	 * Verifica que havingAny() no acepta elementos nulos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHavingAnyThrowsOnNullElement() {
		queryCreator.havingAny(gt("count(id)", 5), null);
	}

	/**
	 * Verifica que groupBy() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGroupByThrowsOnNull() {
		queryCreator.groupBy((String[]) null);
	}

	/**
	 * Verifica que groupBy() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGroupByThrowsOnEmptyArray() {
		queryCreator.groupBy();
	}

	/**
	 * Verifica que orderBy() no acepta null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOrderByThrowsOnNull() {
		queryCreator.orderBy((String[]) null);
	}

	/**
	 * Verifica que orderBy() no acepta array vacío.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOrderByThrowsOnEmptyArray() {
		queryCreator.orderBy();
	}

	/**
	 * Verifica que take() no acepta valores negativos en from.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTakeThrowsOnNegativeFrom() {
		queryCreator.take(-1, 10);
	}

	/**
	 * Verifica que take() no acepta valores negativos en max.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTakeThrowsOnNegativeMax() {
		queryCreator.take(0, -1);
	}

	/**
	 * Verifica que startAt() no acepta valores negativos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStartAtThrowsOnNegative() {
		queryCreator.startAt(-1);
	}

	/**
	 * Verifica que limit() no acepta valores negativos.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLimitThrowsOnNegative() {
		queryCreator.limit(-1);
	}

	/**
	 * Verifica que las validaciones permiten valores correctos.
	 */
	@Test
	public void testValidInputsAreAccepted() {
		// Todos estos deben funcionar sin lanzar excepción
		queryCreator
			.select(get(path("u.name"), "name"))
			.from(entity(Object.class, "o"))
			.whereAll(eq("active", true))
			.havingAll(gt("count(id)", 5))
			.groupBy("u.name")
			.orderBy("u.name asc")
			.take(0, 10)
			.startAt(0)
			.limit(10);

		// Si llegamos aquí, todas las validaciones pasaron
		assertNotNull(queryCreator.getQueryObject());
	}

	/**
	 * Verifica que múltiples llamadas a groupBy acumulan valores.
	 */
	@Test
	public void testGroupByAccumulates() {
		queryCreator.groupBy("field1");
		queryCreator.groupBy("field2");

		String groupBy = queryCreator.getQueryObject().getGroupBy();
		assertTrue("Debe contener field1", groupBy.contains("field1"));
		assertTrue("Debe contener field2", groupBy.contains("field2"));
		assertTrue("Debe contener coma separadora", groupBy.contains(","));
	}

	/**
	 * Verifica que múltiples llamadas a orderBy acumulan valores.
	 */
	@Test
	public void testOrderByAccumulates() {
		queryCreator.orderBy("field1 asc");
		queryCreator.orderBy("field2 desc");

		String orderBy = queryCreator.getQueryObject().getOrderBy();
		assertTrue("Debe contener field1", orderBy.contains("field1"));
		assertTrue("Debe contener field2", orderBy.contains("field2"));
		assertTrue("Debe contener coma separadora", orderBy.contains(","));
	}
}

package com.querybuilder.query;

import static com.querybuilder.expression.ExpressionFactory.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.querybuilder.QueryCreator;

/**
 * Tests para verificar el encapsulamiento de QueryObject con vistas inmutables.
 */
public class QueryObjectEncapsulationTest {

	/**
	 * Verifica que getSelects() retorna una vista inmutable.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGetSelectsIsUnmodifiable() {
		QueryCreator qc = QueryCreator.init(null);
		qc.select(get(path("u.name"), "name"));

		QueryObject qo = qc.getQueryObject();

		// Intentar modificar debe lanzar UnsupportedOperationException
		qo.getSelects().clear();
	}

	/**
	 * Verifica que getFroms() retorna una vista inmutable.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGetFromsIsUnmodifiable() {
		QueryCreator qc = QueryCreator.init(null);
		qc.from(entity(Object.class, "o"));

		QueryObject qo = qc.getQueryObject();

		// Intentar modificar debe lanzar UnsupportedOperationException
		qo.getFroms().clear();
	}

	/**
	 * Verifica que getJoins() retorna una vista inmutable.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGetJoinsIsUnmodifiable() {
		QueryCreator qc = QueryCreator.init(null);
		qc.join(joinTo("a.users", "u", QueryObject.JoinType.LEFT));

		QueryObject qo = qc.getQueryObject();

		// Intentar modificar debe lanzar UnsupportedOperationException
		qo.getJoins().clear();
	}

	/**
	 * Verifica que getConditions() retorna una vista inmutable.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGetConditionsIsUnmodifiable() {
		QueryCreator qc = QueryCreator.init(null);
		qc.whereAll(eq("name", "test"));

		QueryObject qo = qc.getQueryObject();

		// Intentar modificar debe lanzar UnsupportedOperationException
		qo.getConditions().clear();
	}

	/**
	 * Verifica que getHavings() retorna una vista inmutable.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGetHavingsIsUnmodifiable() {
		QueryCreator qc = QueryCreator.init(null);
		qc.havingAll(gt("count(id)", 5));

		QueryObject qo = qc.getQueryObject();

		// Intentar modificar debe lanzar UnsupportedOperationException
		qo.getHavings().clear();
	}

	/**
	 * Verifica que getParameterMap() retorna una vista inmutable.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGetParameterMapIsUnmodifiable() {
		QueryObject qo = new QueryObject();
		qo.addParameter("test", "value");

		// Intentar modificar debe lanzar UnsupportedOperationException
		qo.getParameterMap().clear();
	}

	/**
	 * Verifica que addParameter() funciona correctamente.
	 */
	@Test
	public void testAddParameterWorks() {
		QueryObject qo = new QueryObject();

		qo.addParameter("key1", "value1");
		qo.addParameter("key2", 42);

		assertEquals("Debe contener 2 parámetros", 2, qo.getParameterMap().size());
		assertEquals("value1", qo.getParameterMap().get("key1"));
		assertEquals(42, qo.getParameterMap().get("key2"));
	}

	/**
	 * Verifica que addAllParameters() funciona correctamente.
	 */
	@Test
	public void testAddAllParametersWorks() {
		QueryObject qo = new QueryObject();

		java.util.Map<String, Object> params = new java.util.HashMap<>();
		params.put("key1", "value1");
		params.put("key2", 42);

		qo.addAllParameters(params);

		assertEquals("Debe contener 2 parámetros", 2, qo.getParameterMap().size());
		assertEquals("value1", qo.getParameterMap().get("key1"));
		assertEquals(42, qo.getParameterMap().get("key2"));
	}

	/**
	 * Verifica que las vistas inmutables permiten lectura.
	 */
	@Test
	public void testUnmodifiableViewsAllowReading() {
		QueryCreator qc = QueryCreator.init(null);
		qc.select(get(path("u.name"), "name"));
		qc.from(entity(Object.class, "o"));
		qc.whereAll(eq("active", true));

		QueryObject qo = qc.getQueryObject();

		// Lectura debe funcionar correctamente
		assertEquals("Debe tener 1 select", 1, qo.getSelects().size());
		assertEquals("Debe tener 1 from", 1, qo.getFroms().size());
		assertEquals("Debe tener 1 condición", 1, qo.getConditions().size());

		// Verificar que son realmente las vistas inmutables
		assertNotNull(qo.getSelects());
		assertNotNull(qo.getFroms());
		assertNotNull(qo.getConditions());
	}

	/**
	 * Verifica que intentar agregar a la lista de selects falla.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testCannotAddToSelects() {
		QueryCreator qc = QueryCreator.init(null);
		QueryObject qo = qc.getQueryObject();

		// Intentar agregar debe lanzar UnsupportedOperationException
		qo.getSelects().add(new Select(null, null));
	}

	/**
	 * Verifica que intentar remover de condiciones falla.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testCannotRemoveFromConditions() {
		QueryCreator qc = QueryCreator.init(null);
		qc.whereAll(eq("name", "test"));
		QueryObject qo = qc.getQueryObject();

		// Intentar remover debe lanzar UnsupportedOperationException
		qo.getConditions().remove(0);
	}

	/**
	 * Verifica que intentar modificar el mapa de parámetros falla.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testCannotPutToParameterMap() {
		QueryObject qo = new QueryObject();

		// Intentar put debe lanzar UnsupportedOperationException
		qo.getParameterMap().put("malicious", "value");
	}
}

package com.querybuilder.expression;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.querybuilder.expression.conditions.SimpleCondition;
import com.querybuilder.query.QueryObject;

import static org.junit.Assert.*;
import static com.querybuilder.expression.ExpressionFactory.*;


public class ConditionTest {
	
	@Test
	public void simpleConditionEquals() {
		List<Integer> asList1 = Arrays.asList(23,24,25);
		List<Integer> asList2 = Arrays.asList(23,24,25);
		SimpleCondition condition1 = new SimpleCondition("prop in ( ? )", new ValueExpression(asList1));
		SimpleCondition condition2 = new SimpleCondition("prop in ( ? )", new ValueExpression(asList2));
		assertEquals(condition1, condition2);
		
		condition1 = new SimpleCondition("prop in ( ? )", new ValueExpression(new Integer(24)));
		condition2 = new SimpleCondition("prop2 in ( ? )", new ValueExpression(new Integer(24)));
		assertFalse(condition1.equals(condition2));
		
		condition1 = new SimpleCondition("prop in ( ? )", new ValueExpression(new Integer(34)));
		condition2 = new SimpleCondition("prop in ( ? )", new ValueExpression(new Integer(24)));
		assertFalse(condition1.equals(condition2));
		
	}
	
	@Test
	public void simpleConditionParse() {
		QueryObject qo = new QueryObject();
		qo.setStartParamIndex(2);
		SimpleCondition s = new SimpleCondition("propiedad like '%?%'", new ValueExpression("Esa cosa"));
		String parse = s.parse(qo);
		assertEquals("propiedad like '%:e2%'", parse);
		boolean containsKey = s.getParameters().containsKey("e2");
		assertTrue(containsKey);
		String value = (String) s.getParameters().get("e2");
		assertEquals("Esa cosa", value);
		int startParamIndex = qo.getStartParamIndex();
		assertEquals(3, startParamIndex);
	}
	
	@Test
	public void allConditionEquals() {
		List<Integer> asList1 = Arrays.asList(23,24,25);
		List<Integer> asList2 = Arrays.asList(23,24,25);
		Map<String, Object> mapa1 = new LinkedHashMap<String, Object>(0);
		Map<String, Object> mapa2 = new LinkedHashMap<String, Object>(0);
		mapa1.put("e0", asList1);
		mapa2.put("e0", asList2);

		assertEquals(asList1, asList2);
		assertEquals(mapa1, mapa2);

		ValueExpression value1 = value(new String[]{"uno", "dos"});
		ValueExpression value2 = value(new String[]{"uno", "dos"});

		SimpleCondition in1 = notin("prop3", value1);
		SimpleCondition in2 = notin("prop3", value2);

		// Verificar que las condiciones parsean correctamente
		QueryObject qo1 = new QueryObject();
		QueryObject qo2 = new QueryObject();
		String parsed1 = in1.parse(qo1);
		String parsed2 = in2.parse(qo2);

		assertNotNull("Parsed condition no debe ser nulo", parsed1);
		assertNotNull("Parsed condition no debe ser nulo", parsed2);
		assertFalse("Parameters debe tener valores", in1.getParameters().isEmpty());
		assertFalse("Parameters debe tener valores", in2.getParameters().isEmpty());

		assertEquals("LOS VALORES DE LAS LISTAS NO SON IGUALES!!!! ", value1, value2);

		assertEquals(in1.getExpression(), in2.getExpression());
		SimpleCondition eq1 = eq("prop2", 45);
		SimpleCondition eq2 = eq("prop2", 45);

		assertTrue("Condiciones iguales deben ser equals", eq1.equals(eq2));
		assertTrue("Condiciones iguales deben ser equals", in1.equals(in2));
	}

}

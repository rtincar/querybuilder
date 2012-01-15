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
		/*ConditionExpression all = all(
			eq("prop2", 45),
			in("a.prop3", Arrays.asList(23,24,25)),
			isNull("prop5"),
			like("prop6", "jkjks")
		);
		
		ConditionExpression all2 = all(
			eq("prop2", 45),
			in("a.prop3", Arrays.asList(23,24,25)),
			isNull("prop5"),
			like("prop6", "jkjks")
		);*/
		//assertEquals("Las listas de condiciones no son iguales!!!", all, all2);
		
		
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
		
		System.out.println(in1.parse(new QueryObject()));
		System.out.println(in1.getParameters());
		System.out.println(in2.parse(new QueryObject()));
		System.out.println(in2.getParameters());
		
		
		assertEquals("LOS VALORES DE LAS LISTAS NO SON IGUALES!!!! ",value1, value2);
		
		assertEquals(in1.getExpression(), in2.getExpression());
		SimpleCondition eq1 = eq("prop2", 45);
		SimpleCondition eq2 = eq("prop2", 45);
		
		System.out.println(eq1.equals(eq2));
		
		System.out.println(in1.equals(in2));
		
		
		
	}
	
	
	
	
	
	

}

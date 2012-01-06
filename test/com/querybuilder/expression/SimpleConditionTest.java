package com.querybuilder.expression;

import junit.framework.TestCase;

public class SimpleConditionTest extends TestCase {
	
	protected int il;

	@Override
	protected void setUp() throws Exception {
		il = 23;
	}
	
	public void addFiveTest() {
		int r = il + 5;
		assertEquals(28, r);
	}

	@Override
	protected void runTest() throws Throwable {
		addFiveTest();
	}
	
	
	
	

}

package com.querybuilder.clausules;

import java.util.ArrayList;
import java.util.List;

public class Any extends Many {
	
	private List<Condition> conditions = new ArrayList<Condition>();

	private Any(Condition condition) {
		conditions.add(condition);
	}

	@Override
	public List<Condition> getConditions() {
		return conditions;
	}

	public Any or(Condition condition) {
		conditions.add(condition);
		return this;
	}

	public static Any that(Condition condition) {
		return new Any(condition);
	}

}

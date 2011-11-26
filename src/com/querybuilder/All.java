package com.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class All extends Many {

	private List<Condition> conditions = new ArrayList<Condition>();

	private All(Condition condition) {
		conditions.add(condition);
	}

	@Override
	public List<Condition> getConditions() {
		return conditions;
	}

	public All and(Condition condition) {
		conditions.add(condition);
		return this;
	}

	public static All with(Condition condition) {
		return new All(condition);
	}

}

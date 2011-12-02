package com.querybuilder.clausules;

import java.util.Arrays;
import java.util.List;


public class Having extends Predicator {
	
	private Having(Predicator.Type type) {
		this.type = type;
	}

	private Having(Condition condition) {
		conditions.add(condition);
	}

	public static Having that(Condition condition) {
		return new Having(condition);
	}

	public static Having that(Predicator.Type type, Condition... conditions) {
		Having instance = new Having(type);
		instance.getConditions().addAll(
				(List<Condition>) Arrays.asList(conditions));
		return instance;
	}

}

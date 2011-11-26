package com.querybuilder;

import java.util.Arrays;
import java.util.List;

public class Where extends Predicator {

	private Where(Type type) {
		this.type = type;
	}

	private Where(Condition condition) {
		conditions.add(condition);
	}

	public static Where given(Condition condition) {
		return new Where(condition);
	}

	public static Where given(Where.Type type, Condition... conditions) {
		Where instance = new Where(type);
		instance.getConditions().addAll(
				(List<Condition>) Arrays.asList(conditions));
		return instance;
	}

}

package com.querybuilder.clausules;

import java.util.ArrayList;
import java.util.List;


public abstract class Predicator implements Clausule {

	protected List<Condition> conditions = new ArrayList<Condition>();

	protected Type type = Type.ALL;

	public enum Type {
		ANY, ALL
	}

	public Type getType() {
		return type;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

}

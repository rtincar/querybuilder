package com.querybuilder;

import java.util.List;


public abstract class Many implements Condition {

	public abstract List<Condition> getConditions();

}

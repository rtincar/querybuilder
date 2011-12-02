package com.querybuilder;


public class SubqueryBuilder extends AbstractQueryBuilder<SubqueryBuilder> {
	
	private SubqueryBuilder() {}
	
	public static SubqueryBuilder create() {
		return new SubqueryBuilder();
	}
	
	@Override
	protected SubqueryBuilder self() {
		return this;
	}

}

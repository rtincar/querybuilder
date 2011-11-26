package com.querybuilder;


public class Subquery extends AbstractQuery<Subquery> {
	
	private Subquery() {}
	
	public static Subquery create() {
		return new Subquery();
	}
	
	@Override
	protected Subquery self() {
		return this;
	}

}

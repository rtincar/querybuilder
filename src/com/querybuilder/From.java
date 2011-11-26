package com.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class From {
	
	private List<From.FromExpression> froms = new ArrayList<From.FromExpression>();

	private From(Class<?> clazz, String alias) {
		froms.add(new FromExpression(clazz, alias));
	}

	public From and(Class<?> clazz, String alias) {
		froms.add(new FromExpression(clazz, alias));
		return this;
	}

	public static From entity(Class<?> clazz, String alias) {
		return new From(clazz, alias);
	}
	
	public List<From.FromExpression> getFroms() {
		return froms;
	}
	
	public class FromExpression {
		private Class<?> clazz;
		private String alias;

		public FromExpression(Class<?> clazz, String alias) {
			super();
			this.clazz = clazz;
			this.alias = alias;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public String getAlias() {
			return alias;
		}

	}
	
	

}

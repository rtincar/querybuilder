package com.querybuilder.clausules;

import java.util.ArrayList;
import java.util.List;

public class Join implements Clausule {
	
	private List<JoinExpression> joins = new ArrayList<Join.JoinExpression>();
	
	public enum Type {
		INNER, LEFT, FETCH
	}
	
	private Join(String join, String alias, Join.Type type) {
		joins.add(new JoinExpression(join, alias, type));
	}
	
	public static Join with(String join, String alias, Join.Type type) {
		return new Join(join, alias, type);
	}
	
	public Join and(String join, String alias, Join.Type type) {
		joins.add(new JoinExpression(join, alias, type));
		return this;
	}
	
	public class JoinExpression {
		private String join;
		private String alias;
		private Join.Type type;

		public JoinExpression(String join, String alias, Type type) {
			super();
			this.join = join;
			this.alias = alias;
			this.type = type;
		}

		public String getJoin() {
			return join;
		}

		public String getAlias() {
			return alias;
		}

		public Join.Type getType() {
			return type;
		}

	}
	
	public List<Join.JoinExpression> getJoins() {
		return joins;
	}

}

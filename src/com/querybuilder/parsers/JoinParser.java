package com.querybuilder.parsers;

import java.util.List;

import com.querybuilder.clausules.Join;

public class JoinParser extends ClausuleParser<Join> {

	private JoinParser(Join join) {
		super(join);
	}

	public static JoinParser get(Join join) {
		return new JoinParser(join);
	}

	public String getParsedString() {
		return sb.toString();
	}

	public void parse() {
		sb = new StringBuilder();
		List<Join.JoinExpression> joins = getClausule().getJoins();
		for (Join.JoinExpression je : joins) {
			if (je.getType().equals(Join.Type.INNER)) {
				sb.append(" inner join " + je.getJoin() + " as "
						+ je.getAlias() + " ");
			} else if (je.getType().equals(Join.Type.LEFT)) {
				sb.append(" left join " + je.getJoin() + " as " + je.getAlias()
						+ " ");
			} else {
				sb.append(" join fetch " + je.getJoin() + " as "
						+ je.getAlias() + " ");
			}
		}
	}

}

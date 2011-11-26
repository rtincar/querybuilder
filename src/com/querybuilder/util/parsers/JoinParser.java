package com.querybuilder.util.parsers;

import java.util.List;

import com.querybuilder.Join;
import com.querybuilder.Join.JoinExpression;

public class JoinParser extends AbstractParser {
	
	private Join join;
	private StringBuilder sb;
	
	private JoinParser(Join join) {
		this.join = join;
	}
	
	public static JoinParser get(Join join) {
		return new JoinParser(join);
	}

	@Override
	public String getParsedString() {
		return sb.toString();
	}

	@Override
	public void parse() {
		sb = new StringBuilder();
		List<Join.JoinExpression> joins = join.getJoins();
		for(Join.JoinExpression je : joins) {
			if (je.getType().equals(Join.Type.INNER)) {
				sb.append(" inner join " + je.getJoin() + " as " + je.getAlias() + " ");
			} else if (je.getType().equals(Join.Type.LEFT)) {
				sb.append(" left join " + je.getJoin() + " as " + je.getAlias() + " ");
			} else {
				sb.append(" join fetch " + je.getJoin() + " as " + je.getAlias() + " ");
			}
		}
	}

}

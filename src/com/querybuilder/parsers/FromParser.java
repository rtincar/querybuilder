package com.querybuilder.parsers;

import java.util.List;

import com.querybuilder.clausules.From;
import com.querybuilder.clausules.From.FromExpression;

public class FromParser extends ClausuleParser<From> {
	
	private FromParser(From from) {
		super(from);
	}
	
	public static FromParser get(From from) {
		return new FromParser(from);
	}

	public void parse() {
		sb = new StringBuilder();
		List<FromExpression> froms = getClausule().getFroms();
		int i = 0;
		sb.append(" from ");
		for (FromExpression f : froms) {
			sb.append(f.getClazz().getSimpleName() + " as " + f.getAlias());
			if (i < froms.size() - 1) {
				sb.append(", ");
			}
			i++;
		}
		sb.append(" ");

	}

}

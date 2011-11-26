package com.querybuilder.util.parsers;

import java.util.List;

import com.querybuilder.From;
import com.querybuilder.From.FromExpression;

public class FromParser extends AbstractParser {
	
	private From from;
	private StringBuilder sb;
	
	private FromParser(From from) {
		this.from = from;
	}
	
	public static FromParser get(From from) {
		return new FromParser(from);
	}

	@Override
	public String getParsedString() {
		return sb.toString();
	}

	@Override
	public void parse() {
		sb = new StringBuilder();
		List<FromExpression> froms = from.getFroms();
		int i = 0;
		sb.append(" from ");
		for (FromExpression f : froms) {
			sb.append(f.getClazz().getSimpleName() + " as " + f.getAlias());
			if (i < froms.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(" ");

	}

}

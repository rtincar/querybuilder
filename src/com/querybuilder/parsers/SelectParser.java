package com.querybuilder.parsers;

import java.util.Map;

import com.querybuilder.clausules.Select;

public class SelectParser extends ClausuleParser<Select> {
	

	
	private SelectParser(Select select) {
		super(select);
	}
	
	public static SelectParser get(Select select) {
		return new SelectParser(select);
	}

	public void parse() {
		sb = new StringBuilder();
		Map<String, String> selection = getClausule().getSelection();
		int cont = 0;
		sb.append("select ");
		for (String key : selection.keySet()) {
			sb.append(key + " as " + selection.get(key));
			if (cont < selection.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(" ");
	}

	public String getParsedString() {
		return sb.toString();
	}

}

package com.querybuilder.util.parsers;

import java.util.Map;

import com.querybuilder.Select;

public class SelectParser extends AbstractParser {
	
	private Select select;
	private StringBuilder sb;
	
	private SelectParser(Select select) {
		this.select = select;
	}
	
	public static SelectParser get(Select select) {
		return new SelectParser(select);
	}

	@Override
	public void parse() {
		sb = new StringBuilder();
		Map<String, String> selection = select.getSelection();
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

	@Override
	public String getParsedString() {
		return sb.toString();
	}

}

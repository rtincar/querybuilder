package com.querybuilder.expression;

import java.util.Iterator;
import java.util.Map;

public class SelectExpression implements Expression {
	
	public String parse(QueryObject queryObject) {
		Map<String, String> selectionMap = queryObject.getSelectNode().getSelectionMap();
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		Iterator<String> iterator = selectionMap.keySet().iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			sb.append(next).append(" as ").append(selectionMap.get(next))
					.append(", ");
		}
		sb.append(" ");
		return sb.toString();
	}


}

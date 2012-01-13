package com.querybuilder.expression.clausules;

import java.util.Iterator;
import java.util.List;

import com.querybuilder.QueryObject;
import com.querybuilder.QueryObject.Select;
import com.querybuilder.expression.Expression;

public class SelectExpression implements Expression {
	
	public String parse(QueryObject queryObject) {
		List<Select> selects = queryObject.getSelects();
		StringBuilder sb = new StringBuilder();
		if (!selects.isEmpty()) {
			sb.append("select ");
			for (Iterator<Select> iterator = selects.iterator(); iterator
					.hasNext();) {
				Select next = iterator.next();
				sb.append(next.getPath()).append(" as ")
						.append(next.getAlias());
				if (iterator.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append(" ");
		}
		return sb.toString();
	}


}

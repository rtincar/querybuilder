package com.querybuilder.expression;

import java.util.Iterator;
import java.util.List;

import com.querybuilder.expression.QueryObject.From;

public class FromExpression implements Expression {

	public String parse(QueryObject queryObject) {
		List<From> froms = queryObject.getFroms();
		StringBuilder sb = new StringBuilder();
		if (!froms.isEmpty()) {
			sb.append("from ");
			for (Iterator<From> iterator = froms.iterator(); iterator
					.hasNext();) {
				From next = iterator.next();
				sb.append(next.getEntity().getSimpleName()).append(" as ")
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

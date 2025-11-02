package com.querybuilder.expression.clausules;

import java.util.Iterator;
import java.util.List;

import com.querybuilder.expression.Expression;
import com.querybuilder.query.From;
import com.querybuilder.query.QueryObject;

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

	public String getExpression() {
		return "from ? ";
	}
	
	

}

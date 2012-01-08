package com.querybuilder.expression.clausules;

import java.util.Iterator;
import java.util.List;

import com.querybuilder.expression.Expression;
import com.querybuilder.expression.QueryObject;
import com.querybuilder.expression.QueryObject.Join;

public class JoinExpression implements Expression {

	public String parse(QueryObject queryObject) {
		List<Join> joins = queryObject.getJoins();
		StringBuilder sb = new StringBuilder();
		if (!joins.isEmpty()) {
			sb.append(" ");
			Iterator<Join> iterator = joins.iterator();
			while (iterator.hasNext()) {
				Join join = iterator.next();
				if (join.getJoinType().equals(QueryObject.JoinType.INNER)) {
					sb.append(" inner join ");
				}
				if (join.getJoinType().equals(QueryObject.JoinType.LEFT)) {
					sb.append(" left join ");
				}
				if (join.getJoinType().equals(QueryObject.JoinType.FULL)) {
					sb.append(" full join ");
				}
				sb.append(join.getPath()).append(" as ").append(join.getAlias());
				if (iterator.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append(" ");
		}
		return sb.toString();
	}

}

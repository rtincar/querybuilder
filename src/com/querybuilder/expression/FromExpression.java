package com.querybuilder.expression;

import java.util.Iterator;
import java.util.Map;

public class FromExpression implements Expression {

	public String parse(QueryObject queryObject) {
		Map<Class<?>, String> froms = queryObject.getFroms();
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		Iterator<Class<?>> iterator = froms.keySet().iterator();
		while (iterator.hasNext()) {
			Class<?> clazz = iterator.next();
			String alias = froms.get(clazz);
			sb.append(clazz.getSimpleName()).append(" as ").append(alias);
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

}

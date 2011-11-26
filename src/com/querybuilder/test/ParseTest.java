package com.querybuilder.test;

import java.util.Date;

import com.querybuilder.QueryBuilder;
import com.querybuilder.Subquery;
import com.querybuilder.clausules.All;
import com.querybuilder.clausules.Any;
import com.querybuilder.clausules.From;
import com.querybuilder.clausules.Group;
import com.querybuilder.clausules.Having;
import com.querybuilder.clausules.One;
import com.querybuilder.clausules.Order;
import com.querybuilder.clausules.Select;
import com.querybuilder.clausules.Where;
import com.querybuilder.parsers.QueryParser;


public class ParseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Subquery sub = Subquery.create();
		sub.select(Select.get("a.id", "idA")).from(
				From.entity(Having.class, "a")).where(
				Where.given(One.that("a.estado > ?", 23)));
		
		Where where = Where.given(
				All.with(One.that("c.idea is null")).
					and(One.that("c.fecha > ?", new Date())).
					and(Any.with(One.that("c.value = ?", 
							sub)).
						or(One.that("c.idea < ?", 890))
						)
					);
		Select select = Select.get("c", "c");
		From from = From.entity(Group.class, "c");
		Order order = Order.by("c.id", Order.Direction.DESC).and("c.fecha", Order.Direction.ASC);
		Group group = Group.by("c.idea").and("c.fecha");
		
		QueryBuilder create = QueryBuilder.create(null);
		create.select(select).from(from).where(where).group(group).order(order);
		QueryParser queryParser = QueryParser.get(create);
		queryParser.parse();
		System.out.println(queryParser.getParsedString());
		System.out.println(queryParser.getParameterMap());
		

	}

}

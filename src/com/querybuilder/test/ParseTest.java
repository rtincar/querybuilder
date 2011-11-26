package com.querybuilder.test;

import java.util.Date;

import com.querybuilder.All;
import com.querybuilder.Any;
import com.querybuilder.One;
import com.querybuilder.Subquery;
import com.querybuilder.clausules.Where;
import com.querybuilder.parsers.WhereParser;


public class ParseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Where where = Where.given(
				All.with(One.that("c.idea is null")).
					and(One.that("c.fecha > ?", new Date())).
					and(Any.with(One.that("c.value = ?", 
							Subquery.create().where(
									One.that("c.numero between ? and ?", 23, 26)))).
						or(One.that("c.idea < ?", 890))
						)
					);
		WhereParser whereParser = WhereParser.get(where);
		whereParser.parse();
		System.out.println(whereParser.getParsedString());
		System.out.println(whereParser.getParameterMap());
		

	}

}

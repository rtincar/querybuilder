package com.querybuilder.test;

import static com.querybuilder.expression.ExpressionFactory.*;

import java.util.Map;

import com.querybuilder.clausules.Any;
import com.querybuilder.expression.AllCondition;
import com.querybuilder.expression.ConditionExpression;
import com.querybuilder.expression.QueryCreator;
import com.querybuilder.expression.QueryExpression;
import com.querybuilder.expression.QueryObject.JoinType;

public class ConditionsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ConditionExpression any = any(
			eq("adsad", value("sdasad")),
			eq("sadsa", value(23L)),
			all(
				eq("sda", 34L),
				isNull("sada"),
				ge("asdas", 23),
				le("asd", 56),
				between("asdas", 23, 56),
				like("dasd", concat( literal("'%'"), value("asdsd"), literal("'%'") )),
				all(
					eq("asd", 67),
					eq("as","ads")
				)
			)
		);
		
		QueryCreator creator = QueryCreator.init(null);
		
		creator.select(
			get("asdads", "a"), 
			get("asdasd", "sda"))
		.from(
			entity(Any.class, "a"), 
			entity(AllCondition.class, "all"))
		.join(
			joinTo("a.hijos", "hijos", JoinType.LEFT))
		.whereAll(any)
		.orderBy("a.p3 desc")
		.groupBy("a.p4", "a.p5");
		

		QueryExpression qe = new QueryExpression();
		String parse = qe.parse(creator.getQueryObject());
		Map<String, Object> parameterMap = qe.getParameters();
		System.out.println(parse);
		System.out.println(parameterMap);
		
		

	}

}

package com.querybuilder.test;

import java.util.Map;

import com.querybuilder.clausules.Any;
import com.querybuilder.expression.ConditionExpression;
import com.querybuilder.expression.ConditionList;
import com.querybuilder.expression.Conditions;
import com.querybuilder.expression.QueryCreator;
import com.querybuilder.expression.QueryExpression;
import com.querybuilder.expression.Restrictions;
import com.querybuilder.expression.SimpleCondition;
import com.querybuilder.expression.QueryObject.JoinType;

import static com.querybuilder.expression.Restrictions.*;

public class ConditionsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ConditionExpression any = any(
			eq("adsad", "sdad"),
			eq("sadsa", 23),
			all(
				eq("sda", 34L),
				isNull("sada"),
				ge("asdas", 23),
				le("asd", 56),
				between("asdas", 23, 56),
				like("dasd", "asdsd"),
				all(
					eq("asd", 67),
					eq("as","ads")
				)
			)
		);
		
		QueryCreator creator = QueryCreator.init(null);
		creator.addSelect("a.propiadad", "p");
		creator.addSelect("a.prop2", "p2");
		creator.joinTo("a.cosas", "c", JoinType.LEFT);
		creator.from(Any.class, "a");
		creator.addCondition(any);
		creator.orderBy("a.p3 desc");
		creator.groupBy("a.p4,a.p5");
		QueryExpression qe = new QueryExpression();
		String parse = qe.parse(creator.getQueryObject());
		Map<String, Object> parameterMap = qe.getParameterMap();
		System.out.println(parse);
		System.out.println(parameterMap);
		
		

	}

}

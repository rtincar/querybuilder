package com.querybuilder.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.querybuilder.QueryBuilder;
import com.querybuilder.SubqueryBuilder;
import com.querybuilder.clausules.All;
import com.querybuilder.clausules.Any;
import com.querybuilder.clausules.From;
import com.querybuilder.clausules.Group;
import com.querybuilder.clausules.Having;
import com.querybuilder.clausules.Join;
import com.querybuilder.clausules.One;
import com.querybuilder.clausules.Order;
import com.querybuilder.clausules.Select;
import com.querybuilder.clausules.Where;
import com.querybuilder.parsers.QueryParser;
import com.querybuilder.util.transformer.AbstractTransformer;

public class ParseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SubqueryBuilder sub = SubqueryBuilder.create();
		sub.select(Select.get("a.id").and("c.p2").and("c.p3")).from(
				From.entity(Having.class, "a")).where(
				Where.given(One.that("a.estado > ?", 23)));
		Where where = Where.given(All.that(One.that("c.idea is null")).and(
				One.that("c.fecha > ?", new Date())).and(
				Any.that(One.that("c.value = ?", sub)).or(
						One.that("c.idea < ?", 890))));
		Select select = Select.get("c");
		From from = From.entity(Group.class, "c");
		Order order = Order.by("c.id", Order.Direction.DESC).and("c.fecha",
				Order.Direction.ASC);
		Group group = Group.by("c.idea").and("c.fecha");
		Join join = Join.with("c.grupos", "gs", Join.Type.INNER);
		Having having = Having.that(One.that("c.empa < ?", 47));

		QueryBuilder create = QueryBuilder.create(null);
		create.select(select).from(from).join(join).where(where).group(group)
				.having(having).order(order);

		QueryParser queryParser = QueryParser.get(create);
		queryParser.parse();

		System.out.println(queryParser.getParsedString());
		System.out.println(queryParser.getParameterMap());

		QueryBuilder qb = QueryBuilder.create(null);
		qb.from(Having.class, "h").take(2, 30);

		QueryParser p = QueryParser.get(qb);
		p.parse();

		System.out.println(p.getParsedString());

		QueryBuilder qb2 = QueryBuilder.create(null);

		List<Having> all = qb2.select("a").from(Having.class, "a").all(
				Having.class);

		List<Map<String, Object>> g = qb2.select("a").from(Having.class, "a")
				.all(new MapTransformer(qb.getSelect()));

	}

	static class MapTransformer extends AbstractTransformer<Map<String, Object>> {

		private Select select;
		public MapTransformer(Select select) {
			this.select = select;
		}

		public Map<String, Object> transform(Object object) {

			List<String> keys = new ArrayList<String>(select.getSelection().values());
			Object[] values = (Object[]) object;

			if (keys.size() != values.length) {
				throw new IllegalArgumentException(
						"El numero de propiedades de la" +
						"seleccion difiere con el numero de propiedades obtenidas");
			}
			Map<String, Object> result = new HashMap<String, Object>();
			for (int i = 0; i < keys.size(); i++) {
				result.put(keys.get(i), values[i]);
			}
			return result;
		}
	}
}

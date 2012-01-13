package com.querybuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.querybuilder.expression.ConditionExpression;
import com.querybuilder.expression.ExpressionFactory;
import com.querybuilder.expression.QueryExpression;
import com.querybuilder.util.transformer.Transformer;

/**
 * Clase con metodos convenientes para generar la consulta y obtner los resultados
 * 
 * 
 * @author rtincar
 *
 */
public class QueryCreator {

	/**
	 * Almacena toda la informacion de la consulta
	 */
	private QueryObject queryObject;
	/**
	 * Instancia de EntityManager
	 */
	private EntityManager entityManager;

	public QueryObject getQueryObject() {
		return queryObject;
	}

	private QueryCreator(EntityManager entityManager) {
		this.entityManager = entityManager;
		queryObject = new QueryObject();
	}

	/**
	 * Constructor estatico
	 * 
	 * @param entityManager
	 * @return
	 */
	public static QueryCreator init(EntityManager entityManager) {
		return new QueryCreator(entityManager);
	}

	/**
	 * Retorna el resultado de la consulta
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> all() {
		Query q = createQuery();
		return q.getResultList();
	}

	/**
	 * Retorna el objeto de la consulta si solo se espera un unico resultado
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T one() {
		Query q = createQuery();
		return (T) q.getSingleResult();
	}

	/**
	 * Retorna el primer resutlado de la consulta
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T first() {
		queryObject.setMax(1);
		Query q = createQuery();
		return (T) q.getSingleResult();
	}

	/**
	 * Metodo que recibe como argumento una implentacion de Transformer y retorna
	 * el resultado transformado
	 * 
	 * @param <T>
	 * @param transformer
	 * @return
	 */
	public <T> List<T> all(Transformer<T> transformer) {
		List<?> result = all();
		List<T> transformedResult = new ArrayList<T>(result.size());
		for (Object o : result) {
			transformedResult.add(transformer.transform(o));
		}
		return transformedResult;
	}

	/**
	 * Idem que el metodo one() pero tranformando el resultado
	 * 
	 * @param <T>
	 * @param transformer
	 * @return
	 */
	public <T> T one(Transformer<T> transformer) {
		Object one = one();
		return (one != null) ? transformer.transform(one) : null;
	}

	/**
	 * Idem que first() pero transformando el resultado
	 * 
	 * @param <T>
	 * @param transformer
	 * @return
	 */
	public <T> T first(Transformer<T> transformer) {
		Object first = first();
		return (first != null) ? transformer.transform(first) : null;
	}

	/**
	 * Metodo que genera el objeto Query a partir de QueryObject
	 * 
	 * @return
	 */
	private Query createQuery() {
		QueryExpression qe = new QueryExpression(queryObject);
		String parsedQuery = qe.parse();
		Query q = entityManager.createQuery(parsedQuery);
		for (String k : qe.getParameters().keySet()) {
			q.setParameter(k, qe.getParameters().get(k));
		}
		if (qe.getFirstResult() != null) {
			q.setFirstResult(qe.getFirstResult());
		}
		if (qe.getMaxResults() != null) {
			q.setMaxResults(qe.getMaxResults());
		}
		return q;
	}

	/**
	 * Setter del modo de evaluacion de condiciones. Hay dos modos de evaluacion
	 * de las condiciones de la consulta: 
	 * ALL: se deben cumplir todas las condiciones
	 * ANY: se debe cumplir cualquiera de ellas
	 * 
	 * @param mode
	 * @return
	 */
	public QueryCreator setConditionEvaluationMode(
			QueryObject.ConditionEvaluationMode mode) {
		queryObject.setConditionEvaluationMode(mode);
		return this;
	}

	/**
	 * Con este metodo se indica la proyeccion (seleccion) de la consulta
	 * 
	 * @param selects
	 * @return
	 */
	public QueryCreator select(Select... selects) {
		queryObject.getSelects().addAll(Arrays.asList(selects));
		return this;
	}

	/**
	 * Indicamos las entidades raices de la consulta
	 * 
	 * @param froms
	 * @return
	 */
	public QueryCreator from(From... froms) {
		queryObject.getFroms().addAll(Arrays.asList(froms));
		return this;
	}

	/**
	 * Indicamos las distintas entidades con las que se realiza join
	 * 
	 * @param joins
	 * @return
	 */
	public QueryCreator join(Join... joins) {
		queryObject.getJoins().addAll(Arrays.asList(joins));
		return this;
	}

	/**
	 * Indicamos las condiciones de la consulta. Con este metodo se indica que
	 * deben cumplirse todas las condiciones
	 * 
	 * @param conditions
	 * @return
	 */
	public QueryCreator whereAll(ConditionExpression... conditions) {
		queryObject.getConditions().add(ExpressionFactory.all(conditions));
		return this;
	}

	/**
	 * 
	 * Indicamos las condiciones de la consulta. Con este metodo se indica que
	 * debe cumplirse cualquiera de las condiciones
	 * 
	 * @param conditions
	 * @return
	 */
	public QueryCreator whereAny(ConditionExpression... conditions) {
		queryObject.getConditions().add(ExpressionFactory.any(conditions));
		return this;
	}

	/**
	 * Indicamos el agrupamiento
	 * 
	 * @param group
	 * @return
	 */
	public QueryCreator groupBy(String... group) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = Arrays.asList(group).iterator(); iterator
				.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		if (queryObject.getGroupBy() != null
				&& queryObject.getGroupBy().length() > 0) {
			String current = queryObject.getGroupBy();
			queryObject.setGroupBy(current + ", " + sb.toString());
		} else {
			queryObject.setGroupBy(sb.toString());
		}
		return this;
	}

	/**
	 * Construimos la clausula having indicando que se deben cumplir todas las 
	 * condiciones
	 * 
	 * @param conditionExpression
	 * @return
	 */
	public QueryCreator havingAll(ConditionExpression... conditionExpression) {
		queryObject.getHavings()
				.add(ExpressionFactory.all(conditionExpression));
		return this;
	}

	/**
	 * Construimos la clausula having indicando que se debe cumplir cualquiera
	 * de las condiciones
	 * 
	 * @param conditionExpression
	 * @return
	 */
	public QueryCreator havingAny(ConditionExpression... conditionExpression) {
		queryObject.getHavings()
				.add(ExpressionFactory.any(conditionExpression));
		return this;
	}

	/**
	 * Indicamos la ordenacion
	 * 
	 * @param order
	 * @return
	 */
	public QueryCreator orderBy(String... order) {

		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = Arrays.asList(order).iterator(); iterator
				.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		if (queryObject.getOrderBy() != null
				&& queryObject.getOrderBy().length() > 0) {
			String current = queryObject.getOrderBy();
			queryObject.setOrderBy(current + ", " + sb.toString());
		} else {
			queryObject.setOrderBy(sb.toString());
		}
		return this;
	}

	/**
	 * Toma el numero de registro indicados por max a partir del registro 
	 * indicado con from
	 * 
	 * @param from
	 * @param max
	 * @return
	 */
	public QueryCreator take(int from, int max) {
		queryObject.setFirst(from);
		queryObject.setMax(max);
		return this;
	}

	/**
	 * Retorna los registros a partir de start
	 * 
	 * @param start
	 * @return
	 */
	public QueryCreator startAt(int start) {
		queryObject.setFirst(start);
		return this;
	}

	/**
	 * Retorna como maximo el numero de registros indicados por limit
	 * 
	 * @param limit
	 * @return
	 */
	public QueryCreator limit(int limit) {
		queryObject.setMax(limit);
		return this;
	}

}

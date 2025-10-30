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
import com.querybuilder.query.From;
import com.querybuilder.query.Join;
import com.querybuilder.query.QueryObject;
import com.querybuilder.query.Select;
import com.querybuilder.transformer.Transformer;

/**
 * API fluida para construir consultas JPA-QL de forma programática.
 *
 * <h3>Patrón de Uso</h3>
 * QueryCreator proporciona una interfaz fluida (método chaining) para construir
 * consultas dinámicas de forma type-safe. El QueryObject subyacente está protegido
 * contra modificaciones externas mediante vistas inmutables.
 *
 * <h3>Ejemplo de Uso</h3>
 * <pre>{@code
 * QueryCreator qc = QueryCreator.init(entityManager);
 * List<Usuario> usuarios = qc
 *     .select(get(path("u.nombre"), "nombre"), get(path("u.email"), "email"))
 *     .from(entity(Usuario.class, "u"))
 *     .whereAll(
 *         eq("u.activo", true),
 *         gt("u.edad", 18)
 *     )
 *     .orderBy("u.nombre asc")
 *     .all();
 * }</pre>
 *
 * <h3>Thread-Safety</h3>
 * QueryCreator NO es thread-safe. Cree una nueva instancia para cada consulta.
 *
 * @author rtincar
 * @see QueryObject
 * @see ExpressionFactory
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
		if (entityManager == null) {
			throw new IllegalArgumentException("EntityManager no puede ser nulo");
		}
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
		if (selects == null || selects.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos un select");
		}
		for (Select select : selects) {
			if (select == null) {
				throw new IllegalArgumentException("Los elementos select no pueden ser nulos");
			}
		}
		queryObject.getSelectsInternal().addAll(Arrays.asList(selects));
		return this;
	}

	/**
	 * Indicamos las entidades raices de la consulta
	 * 
	 * @param froms
	 * @return
	 */
	public QueryCreator from(From... froms) {
		if (froms == null || froms.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos un from");
		}
		for (From from : froms) {
			if (from == null) {
				throw new IllegalArgumentException("Los elementos from no pueden ser nulos");
			}
		}
		queryObject.getFromsInternal().addAll(Arrays.asList(froms));
		return this;
	}

	/**
	 * Indicamos las distintas entidades con las que se realiza join
	 * 
	 * @param joins
	 * @return
	 */
	public QueryCreator join(Join... joins) {
		if (joins == null || joins.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos un join");
		}
		for (Join join : joins) {
			if (join == null) {
				throw new IllegalArgumentException("Los elementos join no pueden ser nulos");
			}
		}
		queryObject.getJoinsInternal().addAll(Arrays.asList(joins));
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
		if (conditions == null || conditions.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos una condición");
		}
		for (ConditionExpression condition : conditions) {
			if (condition == null) {
				throw new IllegalArgumentException("Las condiciones no pueden ser nulas");
			}
		}
		queryObject.getConditionsInternal().add(ExpressionFactory.all(conditions));
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
		if (conditions == null || conditions.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos una condición");
		}
		for (ConditionExpression condition : conditions) {
			if (condition == null) {
				throw new IllegalArgumentException("Las condiciones no pueden ser nulas");
			}
		}
		queryObject.getConditionsInternal().add(ExpressionFactory.any(conditions));
		return this;
	}

	/**
	 * Indicamos el agrupamiento
	 * 
	 * @param group
	 * @return
	 */
	public QueryCreator groupBy(String... group) {
		if (group == null || group.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos un campo para agrupar");
		}
		String newGroupBy = joinWithCommas(group);
		if (queryObject.getGroupBy() != null
				&& queryObject.getGroupBy().length() > 0) {
			String current = queryObject.getGroupBy();
			queryObject.setGroupBy(current + ", " + newGroupBy);
		} else {
			queryObject.setGroupBy(newGroupBy);
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
		if (conditionExpression == null || conditionExpression.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos una condición para having");
		}
		for (ConditionExpression condition : conditionExpression) {
			if (condition == null) {
				throw new IllegalArgumentException("Las condiciones having no pueden ser nulas");
			}
		}
		queryObject.getHavingsInternal()
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
		if (conditionExpression == null || conditionExpression.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos una condición para having");
		}
		for (ConditionExpression condition : conditionExpression) {
			if (condition == null) {
				throw new IllegalArgumentException("Las condiciones having no pueden ser nulas");
			}
		}
		queryObject.getHavingsInternal()
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
		if (order == null || order.length == 0) {
			throw new IllegalArgumentException("Debe proporcionar al menos un campo para ordenar");
		}
		String newOrderBy = joinWithCommas(order);
		if (queryObject.getOrderBy() != null
				&& queryObject.getOrderBy().length() > 0) {
			String current = queryObject.getOrderBy();
			queryObject.setOrderBy(current + ", " + newOrderBy);
		} else {
			queryObject.setOrderBy(newOrderBy);
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
		if (from < 0) {
			throw new IllegalArgumentException("El valor 'from' no puede ser negativo");
		}
		if (max < 0) {
			throw new IllegalArgumentException("El valor 'max' no puede ser negativo");
		}
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
		if (start < 0) {
			throw new IllegalArgumentException("El valor 'start' no puede ser negativo");
		}
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
		if (limit < 0) {
			throw new IllegalArgumentException("El valor 'limit' no puede ser negativo");
		}
		queryObject.setMax(limit);
		return this;
	}

	/**
	 * Método helper que concatena un array de strings con separador de coma
	 *
	 * @param values Array de strings a concatenar
	 * @return String con valores separados por ", "
	 */
	private String joinWithCommas(String... values) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = Arrays.asList(values).iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

}

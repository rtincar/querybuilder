package com.querybuilder;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.querybuilder.QueryObject.From;
import com.querybuilder.QueryObject.Join;
import com.querybuilder.QueryObject.Select;
import com.querybuilder.expression.ExpressionFactory;
import com.querybuilder.expression.QueryExpression;
import com.querybuilder.expression.conditions.ConditionExpression;

public class QueryCreator {
	
	private QueryObject queryObject;
	private EntityManager entityManager;
	
	public QueryObject getQueryObject() {
		return queryObject;
	}
	
	private QueryCreator(EntityManager entityManager) {
		this.entityManager  = entityManager;
		queryObject = new QueryObject();
	}
	
	public static QueryCreator init(EntityManager entityManager) {
		return new QueryCreator(entityManager);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> all() {
		Query q = createQuery();
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T one() {
		Query q = createQuery();
		return (T) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T first() {
		queryObject.setMax(1);
		Query q = createQuery();
		return (T) q.getSingleResult();
	}

	private Query createQuery() {
		QueryExpression qe = new QueryExpression();
		String parsedQuery = qe.parse(queryObject);
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
	
	public QueryCreator setConditionEvaluationMode(QueryObject.ConditionEvaluationMode mode) {
		queryObject.setConditionEvaluationMode(mode);
		return this;
	}

	public QueryCreator select(Select...selects) {
		queryObject.getSelects().addAll(Arrays.asList(selects));
		return this;
	}
	
	public QueryCreator from(From...froms) {
		queryObject.getFroms().addAll(Arrays.asList(froms));
		return this;
	}

	public QueryCreator join(Join...joins) {
		queryObject.getJoins().addAll(Arrays.asList(joins));
		return this;
	}

	public QueryCreator whereAll(ConditionExpression...conditions) {
		queryObject.getConditions().add(ExpressionFactory.all(conditions));
		return this;
	}
	
	public QueryCreator whereAny(ConditionExpression...conditions) {
		queryObject.getConditions().add(ExpressionFactory.any(conditions));
		return this;
	}
	
	public QueryCreator groupBy(String...group) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = Arrays.asList(group).iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		if (queryObject.getGroupBy() != null && queryObject.getGroupBy().length() > 0) {
			String current = queryObject.getGroupBy();
			queryObject.setGroupBy(current + ", " + sb.toString());
		} else {
			queryObject.setGroupBy(sb.toString());
		}
		return this;
	}
	
	public QueryCreator havingAll(ConditionExpression...conditionExpression) {
		queryObject.getHavings().add(ExpressionFactory.all(conditionExpression));
		return this;
	}
	
	public QueryCreator havingAny(ConditionExpression...conditionExpression) {
		queryObject.getHavings().add(ExpressionFactory.any(conditionExpression));
		return this;
	}
	
	public QueryCreator orderBy(String...order) {
		
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = Arrays.asList(order).iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		if (queryObject.getOrderBy() != null && queryObject.getOrderBy().length() > 0) {
			String current = queryObject.getOrderBy();
			queryObject.setOrderBy(current + ", " + sb.toString());
		} else {
			queryObject.setOrderBy(sb.toString());
		}
		return this;
	}
	
	public QueryCreator take(int from, int max) {
		queryObject.setFirst(from);
		queryObject.setMax(max);
		return this;
	}
	
	public QueryCreator startAt(int start) {
		queryObject.setFirst(start);
		return this;
	}
	
	public QueryCreator limit(int limit) {
		queryObject.setMax(limit);
		return this;
	}
	

}

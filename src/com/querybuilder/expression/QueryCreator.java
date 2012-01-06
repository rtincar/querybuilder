package com.querybuilder.expression;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryCreator {
	
	private QueryObject queryObject;
	private EntityManager entityManager;
	
	private int selectAliasIndex = 0;
	private static final String SELECT_ALIAS = "_a";
	
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
		for (String k : qe.getParameterMap().keySet()) {
			q.setParameter(k, qe.getParameterMap().get(k));
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
	
	public QueryCreator addCondition(ConditionExpression conditionExpression) {
		queryObject.getConditions().add(conditionExpression);
		return this;
	}
	
	public QueryCreator addSelect(String select) {
		String alias = SELECT_ALIAS + selectAliasIndex;
		selectAliasIndex++;
		queryObject.getSelects().put(select, alias);
		return this;
	}
	
	public QueryCreator addSelect(String propery, String alias) {
		queryObject.getSelects().put(propery, alias);
		return this;
	}
	
	public QueryCreator from(Class<?> clazz, String alias) {
		queryObject.getFroms().put(clazz, alias);
		return this;
	}
	
	public QueryCreator joinTo(String path, String alias, QueryObject.JoinType type) {
		queryObject.getJoins().add(queryObject.new Join(path, alias, type));
		return this;
	}
	
	public QueryCreator groupBy(String group) {
		if (queryObject.getGroupBy() != null && queryObject.getGroupBy().length() > 0) {
			String current = queryObject.getGroupBy();
			queryObject.setGroupBy(current + ", " + group);
		} else {
			queryObject.setGroupBy(group);
		}
		return this;
	}
	
	public QueryCreator having(ConditionExpression conditionExpression) {
		queryObject.getHavings().add(conditionExpression);
		return this;
	}
	
	public QueryCreator orderBy(String order) {
		if (queryObject.getOrderBy() != null && queryObject.getOrderBy().length() > 0) {
			String current = queryObject.getOrderBy();
			queryObject.setOrderBy(current + ", " + order);
		} else {
			queryObject.setOrderBy(order);
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

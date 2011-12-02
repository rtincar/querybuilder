package com.querybuilder.expression;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryCreator {
	
	private QueryObject queryObject;
	private EntityManager entityManager;
	
	protected QueryObject getQueryObject() {
		return queryObject;
	}
	
	private QueryCreator(EntityManager entityManager) {
		this.entityManager  = entityManager;
		queryObject = new QueryObject();
	
	}
	
	public static QueryCreator init(EntityManager entityManager) {
		return new QueryCreator(entityManager);
	}
	
	public <T> List<T> all() {
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
		return q.getResultList();
	}
	
	public QueryCreator addCondition(ConditionExpression conditionExpression) {
		queryObject.getWhereNode().getConditions().add(conditionExpression);
		return this;
	}
	
	public QueryCreator addSelection(String...selection) {
		return this;
	}
	
	public QueryCreator addFrom(Class<?> clazz, String alias) {
		return this;
	}
	
	public QueryCreator addGroup(String...group) {
		return this;
	}
	
	public QueryCreator addOrder(String...order) {
		return this;
	}

}

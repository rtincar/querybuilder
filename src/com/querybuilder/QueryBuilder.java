package com.querybuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.querybuilder.parsers.QueryParser;
import com.querybuilder.util.processor.Processor;
import com.querybuilder.util.transformer.Transformer;

@SuppressWarnings("unchecked")
public class QueryBuilder extends AbstractQuery<QueryBuilder>{
	
	private QueryParser queryParser = QueryParser.get(this);
	private Integer first;
	private Integer max;
	private EntityManager entityManager;
	
	private QueryBuilder(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public static QueryBuilder create(EntityManager entityManager) {
		return new QueryBuilder(entityManager);
	}
	
	@Override
	protected QueryBuilder self() {
		return this;
	}

	public QueryBuilder take(Integer from) {
		this.first = from;
		return self();
	}
	
	public QueryBuilder take(Integer from, Integer elements) {
		this.first = from;
		this.max = elements;
		return self();
	}
	
	public List<?> all() {
		queryParser.parse();
		String parsedQuery = queryParser.getParsedString();
		Map<String, Object> parameterMap = queryParser.getParameterMap();
		Query query = entityManager.createQuery(parsedQuery);
		for (String k : parameterMap.keySet()) {
			query.setParameter(k, parameterMap.get(k));
		}
		if (first != null) {
			query.setFirstResult(first);
		}
		if (max != null) {
			query.setMaxResults(max);
		} 
		return query.getResultList();
	}
	
	
	public Object one() {
		queryParser.parse();
		String parsedQuery = queryParser.getParsedString();
		Map<String, Object> parameterMap = queryParser.getParameterMap();
		Query query = entityManager.createQuery(parsedQuery);
		for (String k : parameterMap.keySet()) {
			query.setParameter(k, parameterMap.get(k));
		}
		return query.getSingleResult();
	}
	
	public Object first() {
		queryParser.parse();
		String parsedQuery = queryParser.getParsedString();
		Map<String, Object> parameterMap = queryParser.getParameterMap();
		Query query = entityManager.createQuery(parsedQuery);
		for (String k : parameterMap.keySet()) {
			query.setParameter(k, parameterMap.get(k));
		}
		if (first != null) {
			query.setFirstResult(first);
		}
		if (max != null) {
			query.setMaxResults(1);
		} 
		List resultList = query.getResultList();
		return (resultList.size() > 0) ? resultList.get(0) : null;
	}
	
	public <T> List<T> all(Class<T> clazz) {
		return (List<T>) all();
	}
	
	public <T> T one(Class<T> clazz) {
		return (T) one(); 
	} 
	
	public <T> T first(Class<T> clazz) {
		return (T) first();
	}
	
	public <T> List<T> all(Transformer<T> transformer) {
		List<?> result = all();
		List<T> resultTransformed = new ArrayList<T>(result.size());
		for (Object o : result) {
			resultTransformed.add(transformer.transform(o));
		}
		return resultTransformed;
	}
	
	public <T> T one(Transformer<T> transformer) {
		T result = null;
		Object o = one();
		if (o != null) {
			result = transformer.transform(o);
		}
		return result; 
	} 
	
	public <T> T first(Transformer<T> transformer) {
		T result = null;
		Object o = first();
		if (o != null) {
			result = transformer.transform(o);
		}
		return result; 
	}
	
	public <T> List<T> all(Processor<T> processor) {
		List<T> result = (List<T>) all();
		for (T o : result) {
			processor.process(o);
		}
		return  result;
	}
	
	public <T> T one(Processor<T> processor) {
		T result = (T) one();
		if (result != null) {
			processor.process(result);
		}
		return result; 
	} 
	
	public <T> T first(Processor<T> processor) {
		T result = (T) first();
		if (result != null) {
			processor.process(result);
		}
		return result;  
	}
	
	public Long count() {
		return null;
	}

	public Integer getFirst() {
		return first;
	}

	public Integer getMax() {
		return max;
	}

}

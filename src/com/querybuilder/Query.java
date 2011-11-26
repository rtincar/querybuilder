package com.querybuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.querybuilder.util.parsers.QueryParser;
import com.querybuilder.util.processor.Processor;
import com.querybuilder.util.transformer.Transformer;

@SuppressWarnings("unchecked")
public class Query extends AbstractQuery<Query>{
	
	private QueryParser queryParser = QueryParser.get(this);
	private Integer first;
	private Integer max;
	
	private Query(){}
	
	public static Query create(EntityManager proxy) {
		return new Query();
	}
	
	@Override
	protected Query self() {
		return this;
	}

	public Query take(Integer from) {
		this.first = from;
		return self();
	}
	
	public Query take(Integer from, Integer elements) {
		this.first = from;
		this.max = elements;
		return self();
	}
	
	public List<?> all() {
		queryParser.parse();
		String parsedQuery = queryParser.getParsedString();
		Map<String, Object> parameterMap = queryParser.getParameterMap();
		return null;
	}
	
	
	public Object one() {
		return null;
	}
	
	public Object first() {
		return null;
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

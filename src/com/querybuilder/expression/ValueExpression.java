package com.querybuilder.expression;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Esta expresion representa un parametro de la consulta
 * 
 * @author rtincar
 *
 */
public class ValueExpression extends ParametrizedExpression {
	
	private Object value;
	
	private Map<String, Object> parameters = new LinkedHashMap<String, Object>(0);
	
	
	public ValueExpression(Object value) {
		if (value == null)
			throw new IllegalArgumentException("El valor no puede ser nulo");
		this.value = value;
	}

	public String parse(QueryObject queryObject) {
		int startParamIndex = queryObject.getStartParamIndex();
		String paramName = PARAM_NAME + startParamIndex;
		parameters.put(paramName, value);
		startParamIndex++;
		queryObject.setStartParamIndex(startParamIndex);
		return ":" + paramName;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}

}

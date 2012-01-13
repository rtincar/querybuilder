package com.querybuilder.expression;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.querybuilder.QueryObject;

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

	public String getExpression() {
		return " ? ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((value == null) ? 0 : ((value instanceof Object[]) ? Arrays.hashCode( (Object[]) value ) : value.hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueExpression other = (ValueExpression) obj;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (value instanceof Object[] && other.value instanceof Object[]) {
			if (!Arrays.equals((Object[]) value, (Object[]) other.value))
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


	
	
	
	

}

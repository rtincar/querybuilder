package com.querybuilder.expression;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import com.querybuilder.query.QueryObject;

/**
 * Esta expresion representa una funcion
 * 
 * @author rtincar
 *
 */
public class FunctionExpression extends ParametrizedExpression {
	
	private String function;
	private Expression[] expressions;
	private Map<String, Object> parameters = new LinkedHashMap<String, Object>(0);

	public FunctionExpression(String function, Expression...expressions) {
		this.function = function;
		this.expressions = expressions;
	}

	public String parse(QueryObject queryObject) {
		StringBuilder sb = new StringBuilder();
		if (expressions != null) {
			checkNumberOfArguments(function, expressions.length);
			StringTokenizer tokens = new StringTokenizer(function, PARAM_PLACEHOLDER, true);
			int cont = 0;
			while (tokens.hasMoreTokens()) {
				String s = tokens.nextToken();
				if (s.equals(PARAM_PLACEHOLDER)) {
					Expression exp = expressions[cont];
					if (exp instanceof ParametrizedExpression) {
						ParametrizedExpression pexp = (ParametrizedExpression) exp;
						sb.append(pexp.parse(queryObject));
						parameters.putAll(pexp.getParameters());
					} else {
						sb.append(exp.parse(queryObject));
					}
					cont++;
				} else {
					sb.append(s);
				}
			}
		} else {
			sb.append(function);
		}
		return sb.toString();
	}
	
	/**
	 * Comprueba que el numero de argumentos coincide con el numero de
	 * parametros esperados por la expresion. Si no es asi lanza una excepcion.
	 * 
	 * @param expression
	 * @param number
	 */
	private void checkNumberOfArguments(String expression, int number) {
		Matcher matcher = PARAM_PATTERN.matcher(expression);
		int cont = 0;
		while (matcher.find()) {
			cont++;
		}
		if (cont != number)
			throw new IllegalArgumentException(
					"El numero de parametros no coincide "
							+ "con el numero de argumentos");
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public String getExpression() {
		return function;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(expressions);
		result = prime * result
				+ ((function == null) ? 0 : function.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
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
		FunctionExpression other = (FunctionExpression) obj;
		if (!Arrays.equals(expressions, other.expressions))
			return false;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}
	
	
	
	
}

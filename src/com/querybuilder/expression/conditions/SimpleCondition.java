package com.querybuilder.expression.conditions;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import com.querybuilder.expression.ConditionExpression;
import com.querybuilder.expression.Expression;
import com.querybuilder.expression.ParametrizedExpression;
import com.querybuilder.expression.QueryExpression;
import com.querybuilder.query.QueryObject;

public class SimpleCondition extends ConditionExpression {
	
	private String condition;
	private Expression[] expressions;
	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>(0);

	public SimpleCondition(String condition, Expression...expressions) {
		this.condition = condition;
		this.expressions = expressions;
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameterMap;
	}

	public String parse(QueryObject queryObject) {
		StringBuilder sb = new StringBuilder();
		if (expressions != null) {
			checkNumberOfArguments(condition, expressions.length);
			StringTokenizer tokens = new StringTokenizer(condition, PARAM_PLACEHOLDER, true);
			int cont = 0;
			while (tokens.hasMoreTokens()) {
				String s = tokens.nextToken();
				if (s.equals(PARAM_PLACEHOLDER)) {
					Expression arg = expressions[cont];
					if (arg instanceof QueryExpression) {
						QueryExpression qe = (QueryExpression) arg;
						sb.append(" ( ").append(qe.parse()).append(" ) ");
						parameterMap.putAll(qe.getParameters());
					} else if (arg instanceof ParametrizedExpression) {
						ParametrizedExpression pexp = (ParametrizedExpression) arg;
						sb.append(pexp.parse(queryObject));
						parameterMap.putAll(pexp.getParameters());
					} else {
						sb.append(arg.parse(queryObject));
					}
					cont++;
				} else {
					sb.append(s);
				}
			}
		} else {
			sb.append(condition);
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

	public String getExpression() {
		return condition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + Arrays.hashCode(expressions);
		result = prime * result
				+ ((parameterMap == null) ? 0 : parameterMap.hashCode());
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
		SimpleCondition other = (SimpleCondition) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (!Arrays.equals(expressions, other.expressions))
			return false;
		if (parameterMap == null) {
			if (other.parameterMap != null)
				return false;
		} else if (!parameterMap.equals(other.parameterMap))
			return false;
		return true;
	}
	
	
	

}

package com.querybuilder.expression.conditions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import com.querybuilder.QueryObject;
import com.querybuilder.expression.Expression;
import com.querybuilder.expression.ParametrizedExpression;
import com.querybuilder.expression.QueryExpression;

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

}

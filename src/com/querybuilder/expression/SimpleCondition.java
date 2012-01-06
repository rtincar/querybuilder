package com.querybuilder.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleCondition extends ConditionExpression {
	
	private static final Pattern PARAM_PATTERN = Pattern.compile("\\?");

	private String condition;
	private Object[] arguments;
	private Map<String, Object> parameterMap = new HashMap<String, Object>(0);

	public SimpleCondition(String condition, Object...arguments) {
		this.condition = condition;
		this.arguments = arguments;
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameterMap;
	}

	public String parse(QueryObject queryObject) {
		StringBuilder sb = new StringBuilder();
		if (arguments != null) {
			checkNumberOfArguments(condition, arguments.length);
			StringTokenizer tokens = new StringTokenizer(condition, "?", true);
			int cont = 0;
			int paramIndex = queryObject.getStartParamIndex();
			while (tokens.hasMoreTokens()) {
				String s = tokens.nextToken();
				if (s.equals("?")) {
					Object arg = arguments[cont];
					if (arg instanceof QueryObject) {
						paramIndex = processQueryObjectArgument(sb, paramIndex,
								arg);
					} else {
						paramIndex = processObjectArgument(sb, paramIndex, arg);
						cont++;
					}
				} else {
					sb.append(s);
				}
			}
			queryObject.setStartParamIndex(paramIndex);
		} else {
			sb.append(condition);
		}
		return sb.toString();
	}

	private int processObjectArgument(StringBuilder sb, int paramIndex,
			Object arg) {
		String paramName = "e" + paramIndex;
		sb.append(":" + paramName);
		parameterMap.put(paramName, arg);
		paramIndex++;
		return paramIndex;
	}

	private int processQueryObjectArgument(StringBuilder sb, int paramIndex,
			Object arg) {
		QueryExpression qe = new QueryExpression();
		QueryObject qo = (QueryObject) arg;
		qo.setStartParamIndex(paramIndex);
		sb.append(" ( ").append(qe.parse(qo)).append(" ) ");
		paramIndex = qo.getStartParamIndex();
		parameterMap.putAll(qo.getParameterMap());
		return paramIndex;
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

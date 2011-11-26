package com.querybuilder.parsers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.querybuilder.Subquery;
import com.querybuilder.clausules.All;
import com.querybuilder.clausules.Any;
import com.querybuilder.clausules.Condition;
import com.querybuilder.clausules.Having;
import com.querybuilder.clausules.One;
import com.querybuilder.clausules.Where;

public class HavingParser extends ClausuleParser<Having> {

	private static final String PARAM_PREFIX = "e";
	private static final Pattern PARAM_PATTERN = Pattern.compile("\\?");

	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
	private int paramIndex = 0;

	private HavingParser(Having having, int start) {
		super(having);
		this.paramIndex = start;
	}

	public static HavingParser get(Having having, int start) {
		return new HavingParser(having, start);
	}

	public static HavingParser get(Having having) {
		return new HavingParser(having, 0);
	}

	public void parse() {
		sb = new StringBuilder();
		sb.append(" where ");
		List<Condition> conditions = getClausule().getConditions();
		parseConditions(conditions, sb, getClausule().getType().equals(Where.Type.ANY));
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	private void parseAny(StringBuilder sb, Any any) {
		List<Condition> conditions = any.getConditions();
		parseConditions(conditions, sb, true);
	}

	private void parseAll(StringBuilder sb, All all) {
		List<Condition> conditions = all.getConditions();
		parseConditions(conditions, sb, false);
	}

	private void parseOne(StringBuilder sb, One one) {
		String expression = one.getExpression();
		Object[] arguments = one.getArguments();
		if (arguments != null) {
			checkNumberOfArguments(expression, arguments.length);
			StringTokenizer tokens = new StringTokenizer(expression, "?", true);
			int cont = 0;
			while (tokens.hasMoreTokens()) {
				String s = tokens.nextToken();
				if (s.equals("?")) {
					Object arg = arguments[cont];
					if (arg instanceof Subquery) {
						Subquery sub = (Subquery) arg;
						SubqueryParser build = SubqueryParser.get(sub, paramIndex);
						build.parse();
						paramIndex += build.getParameterMap().size();
						parameterMap.putAll(build.getParameterMap());
						sb.append(" ( " + build.getParsedString() + " ) ");
					} else {
						String paramName = PARAM_PREFIX + paramIndex;
						sb.append(":" + paramName);
						parameterMap.put(paramName, arg);
						paramIndex++;
					}
					cont++;
				} else {
					sb.append(s);
				}
			}
		} else {
			sb.append(expression);
		}

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

	private void parseConditions(List<Condition> conditions, StringBuilder sb,
			boolean any) {
		sb.append(" ( ");
		for (int i = 0; i < conditions.size(); i++) {
			Condition c = conditions.get(i);
			if (c instanceof One) {
				parseOne(sb, (One) c);
			}
			if (c instanceof Any) {
				parseAny(sb, (Any) c);
			}
			if (c instanceof All) {
				parseAll(sb, (All) c);
			}
			if (i < conditions.size() - 1) {
				sb.append(any ? " or " : " and ");
			}
		}
		sb.append(" ) ");
	}
	
	

}

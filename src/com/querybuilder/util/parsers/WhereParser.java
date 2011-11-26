package com.querybuilder.util.parsers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.querybuilder.All;
import com.querybuilder.Any;
import com.querybuilder.Condition;
import com.querybuilder.One;
import com.querybuilder.Subquery;
import com.querybuilder.Where;

public class WhereParser extends AbstractParser {

	private static final String PARAM_PREFIX = "e";
	private static final Pattern PARAM_PATTERN = Pattern.compile("\\?");

	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
	private Where where;
	private int paramIndex = 0;
	private StringBuilder sb;

	private WhereParser(Where where, int start) {
		this.where = where;
		this.paramIndex = start;
	}

	public static WhereParser get(Where where, int start) {
		return new WhereParser(where, start);
	}

	public static WhereParser get(Where where) {
		return new WhereParser(where, 0);
	}

	@Override
	public void parse() {
		sb = new StringBuilder();
		parseWhere(sb);
	}

	@Override
	public String getParsedString() {
		return sanitizeEmptySpaces(sb.toString());
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	private void parseWhere(StringBuilder sb) {
		sb.append(" where ");
		List<Condition> conditions = where.getConditions();
		parseConditions(conditions, sb, where.getType().equals(Where.Type.ANY));
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

	private String sanitizeEmptySpaces(String s) {
		return s.replaceAll("\\s+", " ");
	}

}

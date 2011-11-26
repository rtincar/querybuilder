package com.querybuilder.util.parsers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.querybuilder.Subquery;
import com.querybuilder.Where;

public class SubqueryParser extends AbstractParser {

	private Subquery subQuery;
	private String parsedSubquery;
	private Map<String, Object> parameterMap = new HashMap<String, Object>();
	private int paramIndex = 0;
	private StringBuilder sb = new StringBuilder();

	private SubqueryParser(Subquery query, int start) {
		this.subQuery = query;
		this.paramIndex = start;
	}

	public static SubqueryParser get(Subquery query, int start) {
		return new SubqueryParser(query, start);
	}

	@Override
	public void parse() {
		parameterMap = new LinkedHashMap<String, Object>();
		parseWhere(sb);
		parsedSubquery = sb.toString();
	}

	@Override
	public String getParsedString() {
		return parsedSubquery;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	private void parseWhere(StringBuilder sb) {
		Where where = subQuery.getWhere();
		if (where != null) {
			WhereParser whereParser = WhereParser.get(where, paramIndex);
			whereParser.parse();
			sb.append(whereParser.getParsedString());
			parameterMap.putAll(whereParser.getParameterMap());
			paramIndex += parameterMap.size();
		}
	}

}

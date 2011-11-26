package com.querybuilder.parsers;

import java.util.LinkedHashMap;
import java.util.Map;

import com.querybuilder.Query;
import com.querybuilder.clausules.Where;

public class QueryParser implements Parser {

	private Query query;
	private String parsedQuery;
	private Map<String, Object> parameterMap;
	private int paramIndex = 0;
	private StringBuilder sb = new StringBuilder();
	

	private QueryParser(Query query) {
		this.query = query;
	}
	
	public static QueryParser get(Query query) {
		return new QueryParser(query);
	}

	public void parse() {
		parameterMap = new LinkedHashMap<String, Object>();
		parseWhere(sb);
	}

	public String getParsedString() {
		return parsedQuery;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}
	
	private void parseWhere(StringBuilder sb) {
		Where where = query.getWhere();
		if (where != null) {
			WhereParser whereParser = WhereParser.get(where, paramIndex);
			whereParser.parse();
			sb.append(whereParser.getParsedString());
			parameterMap.putAll(whereParser.getParameterMap());
			paramIndex += parameterMap.size() + 1;//Esto hay que pensarlo
		}
	}


}

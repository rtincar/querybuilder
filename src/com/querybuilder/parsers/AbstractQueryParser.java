package com.querybuilder.parsers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.querybuilder.AbstractQuery;
import com.querybuilder.clausules.Group;
import com.querybuilder.clausules.Having;
import com.querybuilder.clausules.Join;
import com.querybuilder.clausules.Select;
import com.querybuilder.clausules.Where;
import com.querybuilder.clausules.From.FromExpression;

public abstract class AbstractQueryParser<T extends AbstractQuery<T>> implements Parser {
	
	protected Map<String, Object> parameterMap;
	protected int paramIndex = 0;
	protected StringBuilder sb;
	protected T query;
	
	protected AbstractQueryParser(T q) {
		query = q;
	}
	
	protected String sanitizeEmptySpaces(String s) {
		return s.replaceAll("\\s+", " ");
	}

	public String getParsedString() {
		return sanitizeEmptySpaces(sb.toString());
	}
	
	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}
	
	public void parse() {
		parameterMap = new LinkedHashMap<String, Object>();
		sb = new StringBuilder();
		parseSelect();
		parseFrom();
		parseJoin();
		parseWhere();
		parseGroup();
		parseHaving();
	}

	protected void parseHaving() {
		Having having = query.getHaving();
		if (having != null) {
			HavingParser havingParser = HavingParser.get(having, paramIndex);
			havingParser.parse();
			sb.append(havingParser.getParsedString());
			parameterMap.putAll(havingParser.getParameterMap());
			paramIndex += parameterMap.size();
		}
	}

	protected void parseGroup() {
		Group group = query.getGroup();
		if (group != null) {
			GroupParser groupParser = GroupParser.get(group);
			groupParser.parse();
			sb.append(groupParser.getParsedString());
		} 
		
	}

	protected void parseJoin() {
		Join join = query.getJoin();
		if (join != null) {
			JoinParser joinParser = JoinParser.get(join);
			joinParser.parse();
			sb.append(joinParser.getParsedString());
		}
	}

	protected void parseFrom() {
		FromParser fromParser = FromParser.get(query.getFrom());
		fromParser.parse();
		sb.append(fromParser.getParsedString());
	}

	protected void parseSelect() {
		Select select = query.getSelect();
		if (select != null) {
			SelectParser selectParser = SelectParser.get(select);
			selectParser.parse();
			sb.append(selectParser.getParsedString());
		} else {
			sb.append("select ");
			List<FromExpression> froms = query.getFrom().getFroms();
			int i = 0;
			for (FromExpression fe : froms) {
				sb.append(fe.getAlias());
				if (i < froms.size() - 1) {
					sb.append(",");
				}
				i++;
			}
			sb.append(" ");
		}
	}

	protected void parseWhere() {
		Where where = query.getWhere();
		if (where != null) {
			WhereParser whereParser = WhereParser.get(where, paramIndex);
			whereParser.parse();
			sb.append(whereParser.getParsedString());
			parameterMap.putAll(whereParser.getParameterMap());
			paramIndex += parameterMap.size();
		}
	}

}

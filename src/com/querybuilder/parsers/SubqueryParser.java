package com.querybuilder.parsers;

import com.querybuilder.Subquery;

public class SubqueryParser extends AbstractQueryParser<Subquery> {

	private SubqueryParser(Subquery query, int start) {
		super(query);
		this.paramIndex = start;
	}

	public static SubqueryParser get(Subquery query, int start) {
		return new SubqueryParser(query, start);
	}

}

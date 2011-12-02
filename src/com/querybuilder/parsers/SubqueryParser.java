package com.querybuilder.parsers;

import com.querybuilder.SubqueryBuilder;

public class SubqueryParser extends AbstractQueryParser<SubqueryBuilder> {

	private SubqueryParser(SubqueryBuilder query, int start) {
		super(query);
		this.paramIndex = start;
	}

	public static SubqueryParser get(SubqueryBuilder query, int start) {
		return new SubqueryParser(query, start);
	}

}

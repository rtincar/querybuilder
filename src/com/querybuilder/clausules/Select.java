package com.querybuilder.clausules;

import java.util.LinkedHashMap;
import java.util.Map;

public class Select implements Clausule {
	
	private Map<String, String> selection = new LinkedHashMap<String, String>();
	private int aliasCount = 1;
	private static final String ALIAS_PREFIX = "_a";

	private Select(String column, String alias) {
		selection.put(column, alias);
	}

	public Select and(String column, String alias) {
		selection.put(column, alias);
		return this;
	}
	
	public Select and(String column) {
		selection.put(column, createAlias());
		return this;
	}

	public static Select get(String column, String alias) {
		return new Select(column, alias);
	}
	
	public static Select get(String selection) {
		return new Select(selection, ALIAS_PREFIX + 0);
	}

	public Map<String, String> getSelection() {
		return selection;
	}
	
	private String createAlias() {
		String alias = ALIAS_PREFIX + aliasCount;
		aliasCount++;
		return alias;
	}

}

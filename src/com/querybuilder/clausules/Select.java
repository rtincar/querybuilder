package com.querybuilder.clausules;

import java.util.LinkedHashMap;
import java.util.Map;

public class Select implements Clausule {
	private Map<String, String> selection = new LinkedHashMap<String, String>();

	private Select(String column, String alias) {
		selection.put(column, alias);
	}

	public Select and(String column, String alias) {
		selection.put(column, alias);
		return this;
	}
	
	public Select and(String column) {
		selection.put(column, column);
		return this;
	}

	public static Select get(String column, String alias) {
		return new Select(column, alias);
	}
	
	public static Select get(String selection) {
		return new Select(selection, selection);
	}

	public Map<String, String> getSelection() {
		return selection;
	}

}

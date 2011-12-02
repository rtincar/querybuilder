package com.querybuilder.expression;

import java.util.Map;


public class SelectNode extends Node {
	
	public Map<String, String> selectionMap = new java.util.LinkedHashMap<String, String>();

	public Map<String, String> getSelectionMap() {
		return selectionMap;
	}
	

}

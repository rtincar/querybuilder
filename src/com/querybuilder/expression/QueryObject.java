package com.querybuilder.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa el objeto Context en el patron Interpreter
 * 
 * @author rtincar
 * 
 */
public class QueryObject {
	
	private int startParamIndex = 0;
	
	private SelectNode selectNode;
	private Map<String, Object> paramterMap = new HashMap<String, Object>();
	private LimitNode limitNode;
	private WhereNode whereNode;

	public SelectNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(SelectNode selectNode) {
		this.selectNode = selectNode;
	}

	public Map<String, Object> getParamterMap() {
		return paramterMap;
	}

	public void setParamterMap(Map<String, Object> paramterMap) {
		this.paramterMap = paramterMap;
	}

	public LimitNode getLimitNode() {
		return limitNode;
	}

	public void setLimitNode(LimitNode limitNode) {
		this.limitNode = limitNode;
	}

	public WhereNode getWhereNode() {
		return whereNode;
	}

	public void setWhereNode(WhereNode whereNode) {
		this.whereNode = whereNode;
	}

	public int getStartParamIndex() {
		return startParamIndex;
	}

	public void setStartParamIndex(int startParamIndex) {
		this.startParamIndex = startParamIndex;
	}
	
	

}

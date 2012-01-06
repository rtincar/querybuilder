package com.querybuilder.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa el objeto Context en el patron Interpreter
 * 
 * @author rtincar
 * 
 */
public class QueryObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160919509754143622L;

	public enum ConditionEvaluationMode {
		ALL, ANY
	}

	public enum JoinType {
		FULL, LEFT, INNER
	}

	private int startParamIndex = 0;

	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
	private Map<String, String> selects = new LinkedHashMap<String, String>();
	private Map<Class<?>, String> froms = new LinkedHashMap<Class<?>, String>();
	private List<Join> joins = new ArrayList<Join>();
	private List<ConditionExpression> conditions = new ArrayList<ConditionExpression>();
	private List<ConditionExpression> havings = new ArrayList<ConditionExpression>();
	private String orderBy;
	private String groupBy;
	private Integer first;
	private Integer max;
	private QueryObject.ConditionEvaluationMode conditionEvaluationMode = QueryObject.ConditionEvaluationMode.ALL;

	public Map<String, String> getSelects() {
		return selects;
	}

	public void setSelects(Map<String, String> selects) {
		this.selects = selects;
	}

	public Map<Class<?>, String> getFroms() {
		return froms;
	}

	public void setFroms(Map<Class<?>, String> froms) {
		this.froms = froms;
	}

	public List<Join> getJoins() {
		return joins;
	}

	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}

	public List<ConditionExpression> getHavings() {
		return havings;
	}

	public void setHavings(List<ConditionExpression> havings) {
		this.havings = havings;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public int getStartParamIndex() {
		return startParamIndex;
	}

	public void setStartParamIndex(int startParamIndex) {
		this.startParamIndex = startParamIndex;
	}

	public QueryObject.ConditionEvaluationMode getConditionEvaluationMode() {
		return conditionEvaluationMode;
	}

	public void setConditionEvaluationMode(
			QueryObject.ConditionEvaluationMode conditionEvaluationMode) {
		this.conditionEvaluationMode = conditionEvaluationMode;
	}

	public List<ConditionExpression> getConditions() {
		return conditions;
	}

	public void setConditions(List<ConditionExpression> conditions) {
		this.conditions = conditions;
	}

	public class Join {

		private String path;
		private String alias;
		private JoinType joinType;

		public Join(String path, String alias, JoinType joinType) {
			this.path = path;
			this.alias = alias;
			this.joinType = joinType;
		}

		public String getPath() {
			return path;
		}

		public String getAlias() {
			return alias;
		}

		public JoinType getJoinType() {
			return joinType;
		}

	}

}

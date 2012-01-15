package com.querybuilder.query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.querybuilder.expression.ConditionExpression;

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

	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	private List<Select> selects = new ArrayList<Select>(0);
	private List<From> froms = new ArrayList<From>(0);
	private List<Join> joins = new ArrayList<Join>(0);
	private List<ConditionExpression> conditions = new ArrayList<ConditionExpression>(0);
	private List<ConditionExpression> havings = new ArrayList<ConditionExpression>(0);
	private String orderBy;
	private String groupBy;
	private Integer first;
	private Integer max;
	private QueryObject.ConditionEvaluationMode conditionEvaluationMode = QueryObject.ConditionEvaluationMode.ALL;

	public List<Select> getSelects() {
		return selects;
	}

	public void setSelects(List<Select> selects) {
		this.selects = selects;
	}

	public List<From> getFroms() {
		return froms;
	}

	public void setFroms(List<From> froms) {
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
		return parameters;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameters = parameterMap;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((conditionEvaluationMode == null) ? 0
						: conditionEvaluationMode.hashCode());
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((froms == null) ? 0 : froms.hashCode());
		result = prime * result + ((groupBy == null) ? 0 : groupBy.hashCode());
		result = prime * result + ((havings == null) ? 0 : havings.hashCode());
		result = prime * result + ((joins == null) ? 0 : joins.hashCode());
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((selects == null) ? 0 : selects.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryObject other = (QueryObject) obj;
		if (conditionEvaluationMode == null) {
			if (other.conditionEvaluationMode != null)
				return false;
		} else if (!conditionEvaluationMode
				.equals(other.conditionEvaluationMode))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (froms == null) {
			if (other.froms != null)
				return false;
		} else if (!froms.equals(other.froms))
			return false;
		if (groupBy == null) {
			if (other.groupBy != null)
				return false;
		} else if (!groupBy.equals(other.groupBy))
			return false;
		if (havings == null) {
			if (other.havings != null)
				return false;
		} else if (!havings.equals(other.havings))
			return false;
		if (joins == null) {
			if (other.joins != null)
				return false;
		} else if (!joins.equals(other.joins))
			return false;
		if (max == null) {
			if (other.max != null)
				return false;
		} else if (!max.equals(other.max))
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (selects == null) {
			if (other.selects != null)
				return false;
		} else if (!selects.equals(other.selects))
			return false;
		return true;
	}
	
	

}

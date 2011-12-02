package com.querybuilder.expression;

public class LimitNode extends Node {

	private Integer first;
	private Integer max;

	public LimitNode() {
		super();
	}

	public LimitNode(Integer first, Integer max) {
		this.first = first;
		this.max = max;
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

}

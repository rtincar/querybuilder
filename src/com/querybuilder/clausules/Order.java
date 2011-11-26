package com.querybuilder.clausules;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order implements Clausule {
	
	public enum Direction {
		ASC, DESC
	}

	private Map<String, Direction> orders = new LinkedHashMap<String, Order.Direction>();
	
	private Order(String column, Order.Direction direction){
		orders.put(column, direction);
	}
	
	public static Order by(String column, Order.Direction direction) {
		return new Order(column, direction);
	}
	
	public Order and(String column, Direction direction) {
		orders.put(column, direction);
		return this;
	}
	
	public Map<String, Direction> getOrders() {
		return orders;
	}

}

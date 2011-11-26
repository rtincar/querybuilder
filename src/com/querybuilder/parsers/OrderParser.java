package com.querybuilder.parsers;

import java.util.Map;

import com.querybuilder.clausules.Order;
import com.querybuilder.clausules.Order.Direction;

public class OrderParser extends ClausuleParser<Order> {

	private OrderParser(Order concret) {
		super(concret);
	}
	
	public static OrderParser get(Order order) {
		return new OrderParser(order);
	}

	public void parse() {
		sb.append(" order by ");
		int i = 0;
		Map<String, Direction> orders = getClausule().getOrders();
		for (String key : orders.keySet()) {
			sb.append(key);
			sb.append(" ");
			sb.append((orders.get(key).equals(Order.Direction.ASC) ? "asc"
					: "desc"));
			if (i < orders.size() - 1) {
				sb.append(",");
			}
			i++;
		}
	}
}

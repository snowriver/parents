package com.tenzhao.common.criterion;

import java.io.Serializable;

public class OrderEntry  implements Serializable {
	private final OrderByUtils order;
	public OrderEntry(OrderByUtils order) {
		this.order = order;
	}
	public OrderByUtils getOrder() {
		return order;
	}
	@Override
	public String toString() {
		return order.toString();
	}
}

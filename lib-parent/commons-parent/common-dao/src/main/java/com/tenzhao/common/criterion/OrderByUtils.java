package com.tenzhao.common.criterion;

import java.io.Serializable;

import com.tenzhao.common.dao.OrderBy;

public class OrderByUtils {
	
	public static OrderBy asc(String propertyName) {
		return new OrderBy().asc( propertyName);
	}

	public static OrderBy desc(String propertyName) {
		return new OrderBy().desc( propertyName);
	}

}

package com.tenzhao.common.criterion;

import java.util.Objects;

public class LikeExpression implements Criterion {
	private final String propertyName;
	private final Object value;
	private final String likeMode;
	protected LikeExpression(
			String propertyName,
			Object value,
			String op,
			LikeMatchMode likeMode
			) {
		if(Objects.isNull(likeMode)) {
			throw new RuntimeException(propertyName+" cannot be null");
		}
		this.propertyName = propertyName+op;
		this.value = value;
		this.likeMode = likeMode.name() ;
	}

	protected LikeExpression(String propertyName, String value) {
		this( propertyName, value, null,null);
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}
	
	public boolean getLikeValue() {
		return Boolean.TRUE ;
	}
	
	@Override
	public String getKeyword() {
		return "like";
	}

	@Override
	public Object getValue() {
		return value;
	}

	public String getLikeMode() {
		return likeMode;
	}

}

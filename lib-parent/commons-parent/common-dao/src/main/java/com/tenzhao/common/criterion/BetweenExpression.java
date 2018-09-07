package com.tenzhao.common.criterion;

import java.util.Objects;

public class BetweenExpression implements Criterion {
	private final String propertyName;
	private final Object value;
	private final Object secondValue;

	protected BetweenExpression(String propertyName, Object value, Object secondValue,String op) {
		if(Objects.isNull(value) || Objects.isNull(secondValue)) {
			throw new RuntimeException( propertyName + " cannot be null");
		}
		this.propertyName = propertyName+op;
		this.value = value;
		this.secondValue = secondValue;
	}

	@Override
	public String toString() {
		return propertyName  + value + " and " + secondValue;
	}

	@Override
	public String getKeyword() {
		return "betweenValue";
	}
	
	public String getOp() {
		return "betweenValue";
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public Object getValue() {
		return  value;
	}

	public Object getSecondValue() {
		return secondValue;
	}

}

package com.tenzhao.common.criterion;

public class PropertyExpression implements Criterion {

	private final String propertyName;
	private final String otherPropertyName;
	private final String op;

	protected PropertyExpression(String propertyName, String otherPropertyName, String op) {
		this.propertyName = propertyName + op + otherPropertyName;
		this.otherPropertyName = otherPropertyName;
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	@Override
	public String toString() {
		return propertyName + getOp() + otherPropertyName;
	}

	public boolean getNoValue() {
		return Boolean.TRUE ;
	}
	
	@Override
	public String getKeyword() {
		return "noValue";
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public Object getValue() {
		return otherPropertyName;
	}
}

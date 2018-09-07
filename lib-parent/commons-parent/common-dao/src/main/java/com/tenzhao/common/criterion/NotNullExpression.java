package com.tenzhao.common.criterion;


public class NotNullExpression implements Criterion {

	private final String propertyName;
	
	protected NotNullExpression(String propertyName) {
		this.propertyName = propertyName+" is not null";
	}

	@Override
	public String toString() {
		return propertyName + " is not null";
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
		return "";
	}
}

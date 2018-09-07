package com.tenzhao.common.criterion;

public class NullExpression implements Criterion {

	private final String propertyName;

	/**
	 * Constructs a NullExpression
	 *
	 * @param propertyName The name of the property to check for null
	 *
	 * @see Restrictions#isNull
	 */
	protected NullExpression(String propertyName) {
		this.propertyName = propertyName+" is null";
	}

	@Override
	public String toString() {
		return propertyName + " is null";
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

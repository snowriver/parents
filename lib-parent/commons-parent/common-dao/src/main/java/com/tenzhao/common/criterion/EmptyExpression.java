package com.tenzhao.common.criterion;

public class EmptyExpression extends AbstractEmptinessExpression implements Criterion {

	protected EmptyExpression(String propertyName) {
		super( propertyName );
	}

	@Override
	protected boolean excludeEmpty() {
		return false;
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
		return propertyName;
	}
}

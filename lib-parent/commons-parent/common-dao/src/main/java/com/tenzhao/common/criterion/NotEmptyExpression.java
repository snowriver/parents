package com.tenzhao.common.criterion;

public class NotEmptyExpression extends AbstractEmptinessExpression implements Criterion {
	/**
	 * Constructs an EmptyExpression
	 *
	 * @param propertyName The collection property name
	 *
	 * @see Restrictions#isNotEmpty
	 */
	protected NotEmptyExpression(String propertyName) {
		super( propertyName );
	}

	@Override
	protected boolean excludeEmpty() {
		return true;
	}

	@Override
	public String getKeyword() {
		return "noValue";
	}

	@Override
	public Object getValue() {
		return "";
	}

}

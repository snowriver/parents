package com.tenzhao.common.criterion;
public abstract class AbstractEmptinessExpression implements Criterion {
	/**
	 */
	private static final long serialVersionUID = 1L;
	protected final String propertyName;

	protected AbstractEmptinessExpression(String propertyName) {
		this.propertyName = propertyName;
	}
	protected abstract boolean excludeEmpty();


	@Override
	public final String toString() {
		return propertyName + ( excludeEmpty() ? " <>''" : " =''" );
	}
	
	@Override
	public String getPropertyName() {
		return propertyName+ ( excludeEmpty() ? " <>''" : " =''" );
	}
}

package com.tenzhao.common.criterion;

public class NotExpression implements Criterion {
	private Criterion criterion;

	/**
	 * Constructs a NotExpression
	 *
	 * @param criterion The expression to wrap and negate
	 *
	 * @see Restrictions#not
	 */
	protected NotExpression(Criterion criterion) {
		this.criterion = criterion;
	}

	@Override
	public String toString() {
		return criterion.getPropertyName()+" not"+criterion.toString().replaceAll("^"+criterion.getPropertyName()+" ","");
	}

	@Override
	public String getKeyword() {
		return "not";
	}
	
	@Override
	public String getPropertyName() {
		return "";
	}

	@Override
	public Object getValue() {
		return null;
	}
}

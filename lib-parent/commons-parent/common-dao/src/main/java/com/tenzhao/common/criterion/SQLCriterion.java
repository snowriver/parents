package com.tenzhao.common.criterion;

public class SQLCriterion implements Criterion {
	private final String sql;

	protected SQLCriterion(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return sql;
	}

	@Override
	public String getKeyword() {
		return "";
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

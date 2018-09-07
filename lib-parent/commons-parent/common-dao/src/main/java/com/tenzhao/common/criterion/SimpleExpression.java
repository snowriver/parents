package com.tenzhao.common.criterion;

import java.util.Objects;

public class SimpleExpression implements Criterion {
	/**
	 */
	private static final long serialVersionUID = -2619195316096027023L;
	private final String propertyName;
	private final Object value;
	private final String op;

	protected SimpleExpression(String propertyName, Object value, String op) {
		if (Objects.isNull(value)) {
            throw new RuntimeException( "SimpleExpression中属性 "+propertyName + " cannot be null");
        }
		this.propertyName = propertyName+op;
		this.value = value;
		this.op = op;
	}

	protected final String getOp() {
		return op;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		String sql = "";
		if(!Objects.isNull(value)) {
			Object _t = value;
			if(value instanceof String || value instanceof Character) {
				_t = ("'"+value+"'") ;
			}
			sql = propertyName + _t;
		}
		return sql ;
	}

	public boolean getSingleValue() {
		return Boolean.TRUE ;
	}
	
	@Override
	public String getKeyword() {
		return "singleValue";
	}

}

package com.tenzhao.common.criterion;

import org.apache.commons.lang.StringUtils;

public class InExpression implements Criterion {

	private final  String propertyName;
	private final Object[] values;
	public String op ;
	public String getOp() {
		return op;
	}
	public boolean getListValue() {
		return Boolean.TRUE ;
	}
	
	public String getKeyword() {
		return "listValue";
	}
	/**
	 * Constructs an InExpression
	 *
	 * @param propertyName The property name to check
	 * @param values The values to check against
	 *
	 * @see Restrictions#in(String, java.util.Collection)
	 * @see Restrictions#in(String, Object...)
	 */
	protected InExpression(String propertyName, Object[] values, String op) {
		this.propertyName = propertyName +op;
		this.values = values;
	}

	@Override
	public String toString() {
		String inStr = "" ;
		if(values instanceof String[] || values instanceof Character[]) {
			StringBuilder strBuilder = new StringBuilder("");
			for (Object value : values) {
				strBuilder.append("'").append(((String)value).replaceAll("'", "\\\\'")).append("'").append(",");
			}
			inStr+=strBuilder.deleteCharAt(strBuilder.length()-1);
		}else {
			inStr+=StringUtils.join(values, ",") ;
		}
		return propertyName + getKeyword()+" (" +inStr+ ')';
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public Object[] getValues() {
		return values;
	}
	@Override
	public Object getValue() {
		return toString();
	}
	
}

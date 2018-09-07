package com.tenzhao.common.criterion;
import java.io.Serializable;

public interface Criterion extends Serializable {
	String getKeyword() ;
	public String getPropertyName();
	public Object getValue();
}

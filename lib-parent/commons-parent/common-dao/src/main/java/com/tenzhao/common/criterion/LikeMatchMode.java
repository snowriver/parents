
package com.tenzhao.common.criterion;

public enum LikeMatchMode {
	/** 全名称匹配或下划线占位符  str或str_*/
	EXACT ,
	/** str% */
	START ,
	/** %str */
	END ,
	/** %str% */
	ANYWHERE;
}

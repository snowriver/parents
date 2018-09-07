package com.tenzhao.common.criterion;

public enum MysqlDateFormat{
	yyyyMMdd("%Y%m%d"),
	yyyyMMddHHmmss("%Y%m%d%H%i%s");
	
	private String pattern ;
	private MysqlDateFormat(String format) {
		this.pattern = format ;
	}
	public String getPattern() {
		return pattern;
	}
	
}

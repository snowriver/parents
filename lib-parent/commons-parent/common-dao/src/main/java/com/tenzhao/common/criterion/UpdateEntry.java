package com.tenzhao.common.criterion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;

public class UpdateEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8355115077955982093L;
	private String jdbcType="" ;
	private Object value ;
	private String propertyName="" ;
	private String keyword="" ;
	private String format = "";
	private UpdateEntry(String propertyName,Object value,JDBCType sqlType,String keyword ) {
		if(Objects.nonNull(sqlType)) {
			jdbcType = " ,jdbcType="+sqlType.name();
		}
		this.propertyName = propertyName;
		this.value = value+jdbcType ;
		this.keyword = keyword;
	}
	
	private UpdateEntry(String propertyName,Object value,JDBCType sqlType,String keyword,String format ) {
		if(StringUtils.isNotBlank(jdbcType)) {
			jdbcType = " jdbcType="+sqlType.name();
		}
		this.value = value+jdbcType  ;
		this.format = format;
	}
	
	public static UpdateEntry isNull(String propertyName,JDBCType jdbcType) {
		return new UpdateEntry(propertyName,null,jdbcType,"isNull");
	}
	public static UpdateEntry eq(String propertyName,Object value) {
		if(Objects.isNull(value)) {
			throw new IllegalArgumentException("方法 eq 参数不能为空");
		}
		return new UpdateEntry(propertyName,value,null,"eq");
	}
	
	public static UpdateEntry eqOrisNull(String propertyName,Object value,JDBCType jdbcType) {
		if(Objects.isNull(value)) {
			return isNull(propertyName,jdbcType);
		}
		return new UpdateEntry(propertyName,value,null,"eq");
	}
	public static UpdateEntry eqProperty(String srcPropertyName,String valPropertyName) {
		if(StringUtils.isBlank(valPropertyName)) {
			throw new IllegalArgumentException("方法 eqProperty参数不能为空");
		}
		return new UpdateEntry(srcPropertyName,valPropertyName,null,"eqProperty");
	}
	public static UpdateEntry addNumber(String propertyName,Number value) {
		JDBCType type = null ;
//		if(value instanceof Integer || value instanceof AtomicInteger ) {
//			type = JDBCType.INTEGER ;
//		}else if(value instanceof Long || value instanceof AtomicLong) {
//			type = JDBCType.BIGINT ;
//		}else if(value instanceof BigDecimal) {
//			type = JDBCType.DECIMAL ;
//		}else if(value instanceof Double) {
//			type = JDBCType.DOUBLE ;
//		}else if(value instanceof Float ) {
//			type = JDBCType.FLOAT ;
//		}else if(value instanceof Short) {
//			type = JDBCType.INTEGER ;
//		}else if(value instanceof Byte) {
//			type = JDBCType.BIT ;
//		}
		if(Objects.isNull(value)) {
			value = 0;
		}
		return new UpdateEntry(propertyName,value,type,"addNumber");
	}
	public static UpdateEntry joinStr(String propertyName,Object value) {
		if(Objects.isNull(value)) {
			throw new IllegalArgumentException("方法 joinStr 参数不能为空或非数字");
		}
		return new UpdateEntry(propertyName,value,null,"joinStr");
	}
	public static UpdateEntry addDay(String propertyName,Integer value,MysqlDateFormat toFormat) {
		if(Objects.isNull(value)) {
			throw new IllegalArgumentException("方法 addDay 参数不能为空或非数字");
		}
		return new UpdateEntry(propertyName,value,null,"addDay",toFormat.getPattern());
	}
	
	public static UpdateEntry addMonth(String propertyName,Integer value,MysqlDateFormat toFormat) {
		if(Objects.isNull(value)) {
			throw new IllegalArgumentException("方法 addMonth 参数不能为空或非数字");
		}
		return new UpdateEntry(propertyName,value,null,"addMonth",toFormat.getPattern());
	}
	
	/**
	 * 可以将年、月、日、小时、分钟、计算成秒添加
	 * @param value
	 * @param toFormat
	 * @return
	 */
	public static UpdateEntry addSecond(String propertyName,Long value,MysqlDateFormat toFormat) {
		if(Objects.isNull(value)) {
			throw new IllegalArgumentException("方法 addSecond 参数不能为空或非数字");
		}
		return new UpdateEntry(propertyName,value,null,"addSecond",toFormat.getPattern());
	}
	
	public Object getJdbcType() {
		return value;
	}
	public Object getValue() {
		return value;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getPropertyName() {
		return propertyName;
	}
	public String getFormat() {
		return format;
	}


}

package com.tenzhao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * <pre>
 * 存储excel导入结束后的结果，含所有错误行信息,及Excel的抬头(不含复杂抬头,暂时只限一行的抬头)，
 * </pre>
 * @author chenxj
 *
 */
public class ExcelImpResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Map<String, Element> getTitleMap() {
		return titleMap;
	}
	public void setTitleMap(Map<String, Element> titleMap) {
		this.titleMap = titleMap;
	}
	/**
	 * 没有通过规则验证的行
	 * */
	public List<ErrRowinfo> getLstErrRowinfo() {
		return lstErrRowinfo;
	}
	public void setLstErrRowinfo(List<ErrRowinfo> lstErrRowinfo) {
		this.lstErrRowinfo = lstErrRowinfo;
	}
	public String getRuntimeException() {
		return runtimeException;
	}
	public void setRuntimeException(String runtimeException) {
		this.runtimeException = runtimeException;
	}
	private List<ErrRowinfo> lstErrRowinfo ;
	private Map<String,Element> titleMap ;
	
	/** 数据处理线程中的异常*/
	private String runtimeException;
	
}

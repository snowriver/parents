package com.tenzhao.common.excel.imp;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;


public interface  DataHanderThread extends Runnable {
	public String excelxml = "excel-bean.xml" ;
	public Map<Object,Row> getDatamap();
	
	/***
	 * 自己映射title
	 * @return
	 */
	// public Map<String,String> getExcelTitle();
}

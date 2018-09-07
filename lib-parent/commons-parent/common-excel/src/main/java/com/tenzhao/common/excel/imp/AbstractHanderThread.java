package com.tenzhao.common.excel.imp;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.tenzhao.common.ErrRowinfo;
import com.tenzhao.common.excel.CellMappingElement;

public abstract class AbstractHanderThread implements DataHanderThread {
	public List<ErrRowinfo> resultRow ;
	public Map<Object,Row> datamap ;
	public Map<String,CellMappingElement> notProCellMap ;
	public Object condition ;
	
	public AbstractHanderThread(Map<Object,Row> datamap, List<ErrRowinfo> resultRow,Map<String,CellMappingElement> notProCellMap,Object condition) {
		this.datamap = datamap ;
		this.resultRow = resultRow ;
		this.notProCellMap = notProCellMap;
		this.condition = condition;
	}
	public AbstractHanderThread(){}
	@Override
	public abstract void run() ;

	@Override
	public  Map<Object, Row> getDatamap(){
		return datamap ;
	}

}

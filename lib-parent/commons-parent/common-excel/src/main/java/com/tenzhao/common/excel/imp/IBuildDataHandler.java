package com.tenzhao.common.excel.imp;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.tenzhao.common.ErrRowinfo;
import com.tenzhao.common.excel.CellMappingElement;

public interface IBuildDataHandler{
	
	/**
	 * <pre>
	 * 返回一个继承DataHanderThread的线程类,类的构造函数接收要处理的数据
	 * @param datamap 需要入库的数据
	 * @param resultRow 处理的结果，一个线程安全的list 。
	 * @param notProCellMap excel中非XML模板中定义的javaBean的属性对应的列。
	 * 构建方式如下：<code>List &lt;Row&gt; resultRow=Collections.synchronizedList(new ArrayList&lt;Row&gt;());</code>
	 * 
	 * </pre>
	 * @param arrSet 
	 * @return
	 * @throws Exception 
	 */
	public DataHanderThread getThread(Map<Object,Row> datamap,List<ErrRowinfo> resultRow,Map<String,CellMappingElement> notProCellMap) ;
}

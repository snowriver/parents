package com.tenzhao.common.excel.util;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.tenzhao.common.excel.imp.Excelimp;

import com.tenzhao.common.excel.CellMappingElement;

/**
 * 
 * @author ruanrj
 *
 */
public class ExcelUtil {
	
	/**
	 * excel取值 优先级
	 *   1.excel数据取值; 2.从excel-bean.xml模版获取默认值 ; 3. null
	 * @param row
	 * @param notProCellMap
	 * @param property notProCellMap#key
	 * @return
	 */
	public static String getValue(Row row, Map<String, CellMappingElement> notProCellMap, String property){
		if(notProCellMap.get(property) == null)
			return null;
		String s = Excelimp.replaceBlank(row.getCell(notProCellMap.get(property).getNum()).getStringCellValue());
		if(isBlank(s)){
			try{
				s = notProCellMap.get(property).getDefaultValue();
			}catch(NullPointerException e){
				return null;
			}
			
			if(isBlank(s)){
				return null;
			}else
				return s;
		}else
			return s;			
	}
	
	/**
	 * @see org.apache.commons.lang.StringUtils
	 * @param str
	 * @return
	 */
	private static boolean isBlank(String str){
		int strlen;
		if(str == null || (strlen = str.length()) == 0){
			return true;
		}		
		for(int i = 0; i < strlen; i++){
			if(!Character.isWhitespace(str.charAt(i)))
				return false;
		}		
		return true;
	}
}

package com.tenzhao.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.tenzhao.common.excel.imp.Excelimp;

/**
 * excel导入时出错的行信息
 * @author chenxj
 *
 */
public class ErrRowinfo  {
	
	private Row row ;
	private String errmsg ;
	private String[] arrResult ;
	
	
		
	public ErrRowinfo(Row r, String resultStr) {
		this.row = r;
		this.errmsg = resultStr ;
	}
	
	public Row getRow() {
		return row;
	}
	public void setRow(Row row) {
		this.row = row;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String[] getArrResult(Integer titleCells) {
		if(arrResult!=null)return arrResult ;
		arrResult  = new String[titleCells];
		for (int cn = 0; cn < titleCells; cn++) {
			Cell cell = row.getCell(cn, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			arrResult[cn] = (cell == null ?"":Excelimp.getCellStr(cell, null));
			
		}
		return arrResult;
	}

	public void setArrResult(String[] arrResult) {
		
		this.arrResult = arrResult;
	}
	
}

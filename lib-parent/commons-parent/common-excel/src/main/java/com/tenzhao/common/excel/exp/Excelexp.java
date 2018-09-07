package com.tenzhao.common.excel.exp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tenzhao.common.ErrRowinfo;

/**
 * 导出Excel
 * 
 * @author chenxj
 * 
 */
public class Excelexp {
	private static final String FILE_SUFFIX = ".xlsx|.xls" ;
	private Excelexp(){}
	private static Excelexp excelexp ;
	public static synchronized Excelexp getInstance() {
		if(excelexp == null){
			excelexp = new Excelexp() ;
		}
		return excelexp;
	}

	/**
	 * <pre>
	 * 	生成只带一行抬头的Excel
	 * </pre>
	 * @param excelName excel的全路径名;路径名中使用的目录分隔符必须是正斜杠("/") 例如：D:/Project/share/1.jpg,默认的sheet名称与文件名相同 
	 * @param titles   指定的抬头
	 * @return
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public File buildExcel(String excelName,String[] titles) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return buildExcel(excelName,titles,null,null);
	}
	
	/**
	 * 
	 * @param excelName excel的全路径名
	 * @param titles  指定的抬头
	 * @param fileds  与抬头对应的对象字段
	 * @param dirPath
	 * @return
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public <T> File buildExcel(String excelName,String[] titles,String[] fileds,List<T> data) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		FileOutputStream out = null ;
		File file = null ;
		// HSSWorkbook 用于构建.xls的Excel(97~2003版),XSSFWorkbook构建大于等于Excel2007的版本
		Workbook wb = new XSSFWorkbook();
		// Workbook[] wbs = new Workbook[] { /* new HSSFWorkbook(), */new XSSFWorkbook() };
		CreationHelper createHelper = wb.getCreationHelper();

		// create a new sheet
		Sheet sheet = wb.createSheet(excelName.substring(excelName.lastIndexOf("/")+1, excelName.length()).replaceAll(FILE_SUFFIX, ""));
		// declare a row object reference
	
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();
		DataFormat df = wb.createDataFormat();

		// create 2 fonts objects
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// Set font 1 to 12 point type, blue and bold
		f.setFontHeightInPoints((short) 12);
		f.setColor(IndexedColors.RED.getIndex());
		f.setBold(true);

		// Set font 2 to 10 point type, red and bold
		f2.setFontHeightInPoints((short) 20);
		f2.setColor(IndexedColors.RED.getIndex());
		f2.setBold(true);

		// Set cell style and formatting
		cs.setFont(f);
		cs.setDataFormat(df.getFormat("#,##0.0"));

		// Set the other cell style and formatting
		cs2.setBorderBottom(BorderStyle.THIN);
		cs2.setDataFormat(df.getFormat("text"));
		cs2.setFont(f2);

		builderHead(sheet,titles, createHelper);
		buildData(createHelper,sheet,fileds,data);

		// Save
		String fileAbspath = excelName ;
		out = new FileOutputStream(fileAbspath);
		wb.write(out);
		out.close();
		file = new File(fileAbspath);
		return file ;
	}

	/**
	 * 构建数据
	 * @param createHelper
	 * @param sheet
	 * @param list
	 * @param arrObj 所有行，每行的对象又是一个列的数组
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private <T>void buildData(CreationHelper createHelper, Sheet sheet,String[] fields,List<T> arrObj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		int headRows = 1;
		Row r = null ;
		
		if(arrObj == null || arrObj.isEmpty())return ;
		for (int i = 0; i < arrObj.size(); i++) {
			T t = arrObj.get(i);
			r = sheet.createRow(i+headRows);
			// Cell c = null ;
			for (int cellnum = 0; cellnum < fields.length; cellnum++) {
				// c = r.createCell(cellnum);
				Method method = t.getClass().getMethod("get"+StringUtils.capitalize(fields[cellnum]), null)  ;
				buildCell(r,method.invoke(t, null),cellnum);
			}
		}
	}
	
	/**
	 * 将导入excel文件产生的出错行ResultRowinfo,构建到excel中
	 * @param sheet
	 * @param r
	 * @param obj
	 */
	private void buildRow(Sheet sheet,Row r,Object obj) {
		ErrRowinfo rowinfo = (ErrRowinfo)obj ;
		Row row = rowinfo.getRow();
		for(int i = 0;i<row.getLastCellNum();i++){
			Cell cell = row.getCell(i) ;
			Cell c = r.createCell(i,cell.getCellType()) ;
			setCellValue(c,cell);
		}
		Cell cell = r.createCell(r.getLastCellNum()+1,Cell.CELL_TYPE_STRING);
		cell.setCellValue(rowinfo.getErrmsg());
	}

	private void setCellValue(Cell c, Cell cell) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					c.setCellValue(cell.getRichStringCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						c.setCellValue(cell.getDateCellValue()) ;
					} else {
						c.setCellValue(cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					c.setCellValue(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					c.setCellValue(cell.getCellFormula());
					break;
				default:
					c.setCellValue(cell.getRichStringCellValue().getString());
				}
	}

	/**
	 * 构建sheet
	 * @param object
	 */
	private void buildCell(Row r,Object value,int cellNum) {
		Cell c = null ;
		if(value.getClass().getName() .equals("java.util.Date")){
			c = r.createCell(cellNum);
			c.setCellValue((Date)value);
		}else if("java.util.Calendar".equals(value.getClass().getName())){
			c = r.createCell(cellNum);
			c.setCellValue((Calendar)value);
		}else if("java.lang.Double".equals(value.getClass().getName())){
			c = r.createCell(cellNum,0);
			c.setCellValue((Double)value);
		}else if("java.lang.Integer".equals(value.getClass().getName())){
			c = r.createCell(cellNum,0);
			c.setCellValue((Integer)value) ;
		}else if("java.lang.Float".equals(value.getClass().getName())){
			c = r.createCell(cellNum,0);
			c.setCellValue((Float)value) ;
		}else if("java.lang.Short".equals(value.getClass().getName())){
			c = r.createCell(cellNum,0);
			c.setCellValue((Short)value) ;
		}else if("java.lang.Long".equals(value.getClass().getName())){
			c = r.createCell(cellNum,0);
			c.setCellValue((Long)value) ;
		}else{
			c = r.createCell(cellNum);
			c.setCellValue(value.toString()) ;
		}
	}

	/**
	 * 构建标题头
	 */
	private static void builderHead(Sheet s,String[] heads, CreationHelper createHelper) {
		Row r = s.createRow(0);
		for (int cellnum = 0; cellnum < heads.length; cellnum++) {
			// 根据字体大小和标题长度设置列宽
			s.setColumnWidth(cellnum, 256 * (heads[cellnum].length()<9?9:heads[cellnum].length()));
			Cell c = r.createCell(cellnum);
			c.setCellValue(heads[cellnum]);
			// c2.setCellValue(
			// createHelper.createRichTextString("Hello! " + cellnum)
			// );
		}
	}

}

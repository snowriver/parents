package com.tenzhao.common.excel.imp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.tenzhao.common.ErrRowinfo;
import com.tenzhao.common.ExcelImpResult;
import com.tenzhao.common.excel.CellMappingElement;
import com.tenzhao.common.xml.XmlOperate;

/**
 * 从Excel读取数据,仅限一个Sheet
 * 
 * @author chenxj
 *
 */
public class Excelimp {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Excelimp.class);
	private final static String EXCEL_FILE = Excelimp.class.getResource("/").getPath().concat("excel-bean.xml");
	private final static String DEFAULT_DATEFORMATER = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 读Excel 1、将excel的抬头
	 * 
	 * @param rowStart
	 *            从第几行开始读
	 * @param rowEnd
	 * @param inputstream
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FactoryConfigurationError
	 */
	@SuppressWarnings("unchecked")
	public static ExcelImpResult readExcel(Integer rowStart, Integer rowEnd, InputStream inputstream, String mapxmlid,
			IBuildDataHandler t) throws IOException, InvalidFormatException, FactoryConfigurationError,
			ParserConfigurationException, SAXException, DocumentException, Exception {
		ExcelImpResult excelImpResult = new ExcelImpResult();
		// 创建线程安全的集合，接收返回的错误行
		List<ErrRowinfo> resultRow = Collections.synchronizedList(new ArrayList<ErrRowinfo>());
		// bean节点
		Element xmlBean = XmlOperate.getElement(mapxmlid, EXCEL_FILE);
		Workbook wb = WorkbookFactory.create(inputstream);
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		Sheet sheet = wb.getSheetAt(0);

		Map<String, Element> map = getExcelTitle(xmlBean, sheet.getRow(sheet.getFirstRowNum()));
		excelImpResult.setTitleMap(map);
		Set<Entry<String, Element>> set = map.entrySet();
		Entry<String, Element>[] arrSet = new Map.Entry[set.size()];
		set.toArray(arrSet);

		if (rowStart == null) {
			// 默认从第二行开始读数据
			rowStart = sheet.getFirstRowNum() + 1;
		}
		if (rowEnd == null) {
			rowEnd = sheet.getLastRowNum();
		}

		Map<Object, Row> dataMap = new LinkedHashMap<Object, Row>();
		Map<String, CellMappingElement> notProCellMap = getNotProCellMap(arrSet, xmlBean);
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row r = sheet.getRow(rowNum);
			if (r == null)
				break; // 读到空行就退出
			if (r.getCell(0) == null || "".equals(r.getCell(0).getStringCellValue().trim()))
				break; // 读到第一列为空跳出
			String resultStr = validateRow(r, arrSet);
			if (resultStr.length() == 0) {
				Object obj = null;
				try {
					// 此处反射之后加入list中 ？对于不能反射的字段怎么处理
					obj = reflectRow(r, arrSet, xmlBean);
					notExcelCol(obj, map, xmlBean);
				} catch (Exception exception) {
					exception.printStackTrace();
					ErrRowinfo definedRow = new ErrRowinfo(r, exception.getMessage());
					resultRow.add(definedRow);
					continue;
				}

				dataMap.put(obj, r);
			} else {
				ErrRowinfo definedRow = new ErrRowinfo(r, resultStr);
				resultRow.add(definedRow);
				continue;
			}
			if (rowNum > 0 && (rowNum % 1000 == 0)) {
				Map<Object, Row> tmpMap = new LinkedHashMap<Object, Row>();
				tmpMap.putAll(dataMap);
				cachedThreadPool.execute(t.getThread(tmpMap, resultRow, notProCellMap));
				dataMap.clear();
			}
			// 数据量大时使用线程操作
		}
		if (dataMap.size() > 0) {
			cachedThreadPool.execute(t.getThread(dataMap, resultRow, notProCellMap));
			dataMap = null;
		}

		cachedThreadPool.shutdown();
		while (true) {
			if (cachedThreadPool.isTerminated()) {
				// System.out.println("结束了！");
				break;
			}
		}
		excelImpResult.setLstErrRowinfo(resultRow);
		return excelImpResult;
	}

	public static ExcelImpResult readExcel(Integer rowStart, Integer rowEnd, InputStream inputstream, String mapxmlid) throws IOException, InvalidFormatException, FactoryConfigurationError,
			ParserConfigurationException, SAXException, DocumentException, Exception {
		ExcelImpResult excelImpResult = new ExcelImpResult();
		// 创建线程安全的集合，接收返回的错误行
		List<ErrRowinfo> resultRow = Collections.synchronizedList(new ArrayList<ErrRowinfo>());
		// bean节点
		Element xmlBean = XmlOperate.getElement(mapxmlid, EXCEL_FILE);
		Workbook wb = WorkbookFactory.create(inputstream);
		Sheet sheet = wb.getSheetAt(0);

		Map<String, Element> map = getExcelTitle(xmlBean, sheet.getRow(sheet.getFirstRowNum()));
		excelImpResult.setTitleMap(map);
		Set<Entry<String, Element>> set = map.entrySet();
		Entry<String, Element>[] arrSet = new Map.Entry[set.size()];
		set.toArray(arrSet);
		rowStart = (rowStart == null)?(sheet.getFirstRowNum() + 1):rowStart ;
		rowEnd = Optional.ofNullable(rowEnd).orElse(sheet.getLastRowNum());
		Map<Object, Row> dataMap = new LinkedHashMap<Object, Row>();
		Map<String, CellMappingElement> notProCellMap = getNotProCellMap(arrSet, xmlBean);
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row r = sheet.getRow(rowNum);
			if (Objects.isNull(r)) {
				break; // 读到空行就退出
			}
			if (r.getCell(0) == null || "".equals(r.getCell(0).getStringCellValue().trim()))
				break; // 读到第一列为空跳出
			String resultStr = validateRow(r, arrSet);
			if (resultStr.length() == 0) {
				Object obj = null;
				try {
					// 此处反射之后加入list中 ？对于不能反射的字段怎么处理
					obj = reflectRow(r, arrSet, xmlBean);
					notExcelCol(obj, map, xmlBean);
				} catch (Exception exception) {
					exception.printStackTrace();
					ErrRowinfo definedRow = new ErrRowinfo(r, exception.getMessage());
					resultRow.add(definedRow);
					continue;
				}
				dataMap.put(obj, r);
			} else {
				ErrRowinfo definedRow = new ErrRowinfo(r, resultStr);
				resultRow.add(definedRow);
				continue;
			}
		}
		excelImpResult.setLstErrRowinfo(resultRow);
		return excelImpResult;
	}
	
	
	/**
	 * <pre>
	 * 找出Excel中不是javaBean 属性的列,如有则返回Map<xmlProperty元素的name属性,对应的excel中的列序号
	 * ,没有则返回null
	 * map的Key为xml模板中定义的name属性,Value对应的excel中的列序号
	 * </pre>
	 * 
	 * @param arrSet
	 * @param beanElement
	 * @return 返回Map 或null
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, CellMappingElement> getNotProCellMap(Entry<String, Element>[] arrSet,
			Element beanElement) {
		Map<String, CellMappingElement> map = new HashMap<String, CellMappingElement>();
		for (int i = 0; i < arrSet.length; i++) {
			Element e = arrSet[i].getValue();
			Boolean isPro = Boolean.parseBoolean(e.attributeValue("isPro"));
			// 如果不是bean 属性则跳过
			if (!isPro) {
				String name = e.selectSingleNode("./@name").getStringValue();
				map.put(name, new CellMappingElement(i, e));
			}
		}

		// modify by ruanrj start 2015-11-26 取非属性默认值
		int len = arrSet.length;
		List<Element> list = beanElement.selectNodes("./property[@isPro='flase']");
		for (Element element : list) {
			String name = element.attributeValue("name");
			if (!map.containsKey(name)) {
				map.put(name, new CellMappingElement(len++, element));
			}
		}
		// modify by ruanrj end
		return map.size() > 0 ? map : null;
	}

	/**
	 * <pre>
	 * 从一行数据中反射生成,xml模板定义的bean，如果对应的列是bean的属性，则赋值给该属性
	 * </pre>
	 * 
	 * @param r
	 * @param e
	 * @param arrSet
	 *            excel抬头对应的xml中的property属性
	 * @return Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Object reflectRow(Row r, Entry<String, Element>[] arrSet, Element root) throws Exception {
		Class clazz = Class.forName(root.attributeValue("type"));
		Object o = clazz.newInstance();
		for (int i = 0; i < arrSet.length; i++) {
			Element e = arrSet[i].getValue();
			String cellVar = getCellStr(r.getCell(i), e.attributeValue("scale"));
			try {
				reflectCell(clazz, o, cellVar, e);
			} catch (Exception exception) {
				StringBuilder errBuilder = new StringBuilder("第");
				errBuilder.append(r.getRowNum() + 1).append("行").append(i + 1).append("列,【").append(arrSet[i].getKey())
						.append("】反向异常").append(exception.getMessage());
				exception.printStackTrace();
				exception.printStackTrace();
				throw new Exception(errBuilder.toString());
			}

		}

		return o;
	}

	/**
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void notExcelCol(Object o, Map map, Element root) throws Exception {
		Class clazz = o.getClass();
		List<Element> lstnotExcelCol = root.selectNodes("./property[@isPro='true']");
		for (Element e : lstnotExcelCol) {
			String excelTitlename = e.attributeValue("excelTitlename");
			if (Objects.isNull(map.get(excelTitlename))) {
				try {
					String defaultV = e.attributeValue("default");
					if (StringUtils.isNotBlank(defaultV)) {
						reflectCell(clazz, o, defaultV, e);
					}
				} catch (Exception exception) {
					exception.printStackTrace();
					LOGGER.warn(excelTitlename + "默认值设置出错:{}",exception);
					continue;
				}
			}
		}
	}

	/**
	 * <pre>
	 * 反射excel单元格的值给指定对象的属性
	 * </pre>
	 * 
	 * @param clazz
	 * @param o
	 *            目标对象
	 * @param cellVar
	 * @param e
	 *            对应bean的某个property元素
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void reflectCell(Class clazz, Object o, String cellVar, Element e) throws Exception {
		String proType = e.selectSingleNode("./@dataType").getStringValue();
		Boolean isPro = Boolean.parseBoolean(e.attributeValue("isPro"));
		// 如果不是bean 属性则跳过
		if (!isPro) {
			return;
		}
		String attname = e.attributeValue("name");
		if (cellVar == null || "".equals(cellVar))
			cellVar = e.attributeValue("default");
		String initialUpper = attname.substring(0, 1).toUpperCase().concat(attname.substring(1));
		Class methodParamType = o.getClass().getDeclaredField(attname).getType();
		Method setMethod = clazz.getMethod("set" + initialUpper, methodParamType);
		// String simpleName = methodParamType.getSimpleName();
		if (cellVar != null && cellVar.length() == 0) {
			return;
		}

		if ("String".equals(proType)) {
			setMethod.invoke(o, getMapentryBeanValue(cellVar, e));
		} else if ("Short".equalsIgnoreCase(proType) || "Integer".equalsIgnoreCase(proType)
				|| "Long".equalsIgnoreCase(proType)) {

			cellVar = getMapentryBeanValue(cellVar, e);

			if (cellVar.indexOf(".") >= 0
					&& (cellVar.substring(cellVar.indexOf("."), cellVar.length()).length() == 2)) {
				cellVar = cellVar.replace(".0", "");
			}
			setMethod.invoke(o, methodParamType.getMethod("valueOf", String.class).invoke(null, cellVar));
		} else if ("Float".equalsIgnoreCase(proType) || "Double".equalsIgnoreCase(proType)) {
			cellVar = getMapentryBeanValue(cellVar, e);
			setMethod.invoke(o, methodParamType.getMethod("valueOf", String.class).invoke(null, cellVar));
		} else if ("BigDecimal".equalsIgnoreCase(proType)) {
			cellVar = getMapentryBeanValue(cellVar, e);
			setMethod.invoke(o,
					methodParamType.getMethod("valueOf", double.class).invoke(null, Double.valueOf(cellVar)));
		} else if ("Boolean".equalsIgnoreCase(proType)) {
			cellVar = getMapentryBeanValue(cellVar, e);
			setMethod.invoke(o, methodParamType.getMethod("valueOf", String.class).invoke(null, cellVar));
		} else if ("Byte".equalsIgnoreCase(proType)) {
			cellVar = getMapentryBeanValue(cellVar, e);
			setMethod.invoke(o, methodParamType.getMethod("valueOf", String.class).invoke(null, cellVar));
		} else if ("Character".equalsIgnoreCase(proType)) {
			cellVar = getMapentryBeanValue(cellVar, e);
			setMethod.invoke(o, methodParamType.getMethod("valueOf", char.class).invoke(null, cellVar.charAt(0)));
		} else if ("Date".equalsIgnoreCase(proType)) {
			cellVar = getMapentryBeanValue(cellVar, e);
			String formater = e.attributeValue("formater");
			SimpleDateFormat dateFormat = new SimpleDateFormat(formater == null ? DEFAULT_DATEFORMATER : formater);
			if ("now".equalsIgnoreCase(cellVar)) {
				cellVar = dateFormat.format(new Date());
			}
			setMethod.invoke(o, dateFormat.parse(cellVar));
		}

	}

	/**
	 * 获取当前元素子元素map下excel_key属性等于指定值的bean_value的属性元素值
	 * 
	 * @param cellVar
	 * @param e
	 * @return
	 */
	public static String getMapentryBeanValue(String cellVar, Element e) {
		Node node = e.selectSingleNode("./map/entry[@excel_key='" + cellVar + "']/@bean_value");
		if (node != null) {
			cellVar = node.getStringValue().trim();
		}
		return cellVar.trim();
	}

	/**
	 * <pre>
	 * 		excel 抬头验证，检查抬头文字是否与xml中定义的一致,以及是否缺少必要的列
	 * </pre>
	 * 
	 * @param e
	 *            xml模板中的某个bean元素
	 * @param title
	 * @return 返回excel抬头对应的XML模板的属性【property】元素的键值K为excel的抬头，V对应xml中的Property元素
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Element> getExcelTitle(Element xmlBean, Row r) throws Exception {
		int lastColumn = r.getLastCellNum();
		Map<String, Element> resultmap = new LinkedHashMap<String, Element>();
		List<Element> requiredField = xmlBean.selectNodes("./property[@required='true']");
		List<String> requiredList = new ArrayList<String>(requiredField.size());
		for (Element element : requiredField) {
			requiredList.add(element.attributeValue("excelTitlename"));
		}
		requiredField = null;
		for (int cn = 0; cn < lastColumn; cn++) {
			Cell cell = r.getCell(cn, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (cell != null) {
				String d = replaceBlank(cell.getStringCellValue());
				Node node = xmlBean.selectSingleNode("./property[@excelTitlename='" + d + "']");
				if (node == null) {
					LOGGER.warn("文件抬头【" + d + "】与模板定义不符,如需读取该列请调整XML模板");
					continue;
					// throw new Exception();
				}
				requiredList.remove(node.selectSingleNode("@excelTitlename").getStringValue());
				Element tmpE = (Element) node;
				resultmap.put(d, tmpE);
			}

		}
		if (requiredList.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for (String str : requiredList) {
				builder.append("、").append(str);
			}
			builder.deleteCharAt(0);
			throw new Exception("导入的文件缺少必须的列【" + builder.toString() + "】");
		}

		return resultmap;
	}

	/**
	 * <pre>
	 * 根据对应xml的property元素，验证一行数据，如果返回空串则该行数据通过验证
	 * </pre>
	 */
	private static String validateRow(Row r, Entry<String, Element>[] arrSet) {
		// int lastColumn = r.getLastCellNum() ;
		StringBuilder b = new StringBuilder("");
		for (int cn = 0; cn < arrSet.length; cn++) {
			Cell cell = r.getCell(cn,  MissingCellPolicy.RETURN_BLANK_AS_NULL);
			Element elementByset = arrSet[cn].getValue();
			String required = elementByset.attributeValue("required");
			int rowNum = (r.getRowNum() + 1);
			if (cell == null && "true".equalsIgnoreCase(required)) {
				String defaultValue = elementByset.attributeValue("default");
				if (defaultValue == null || "".equals(defaultValue)) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("不能为空");
					break;
				}
			} else if (cell == null && "false".equalsIgnoreCase(required)) {
				// 如果不是必须的且为null 则忽略验证
				continue;
			}
			String scale = elementByset.attributeValue("scale");
			String v = getCellStr(cell, scale);
			if (v.length() == 0 && "false".equalsIgnoreCase(required)) {
				// 如果是空串且不是必填项则忽略检查
				continue;
			}
			if (v.length() > Integer.valueOf(elementByset.attributeValue("maxLength"))) {
				b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
						.append("】").append("长度超过数据库设定长度");
				break;
			}
			// ================= 类型判断 ====================
			String dataType = elementByset.attributeValue("dataType");

			// 字符串格式不用验证
			if ("String".equalsIgnoreCase(dataType)) {
				continue;
			}
			if ("Short".equalsIgnoreCase(dataType) || "Integer".equalsIgnoreCase(dataType)
					|| "Long".equalsIgnoreCase(dataType)) {
				try {
					v = getMapentryBeanValue(v, elementByset);
					if (v.indexOf(".") >= 0 && (v.substring(v.indexOf("."), v.length()).length() == 2)) {
						v = v.replace(".0", "");
					}
					Long.parseLong(v);

				} catch (NumberFormatException ne) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("必须是整数");
					break;
				}
				continue;
			}

			if ("Float".equalsIgnoreCase(dataType) || "Double".equalsIgnoreCase(dataType)
					|| "BigDecimal".equalsIgnoreCase(dataType)) {
				try {
					v = getMapentryBeanValue(v, elementByset);
					Double.parseDouble(v);
				} catch (NumberFormatException ne) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("必须是浮点数");
					break;
				}

				continue;
			}

			if ("Boolean".equalsIgnoreCase(dataType)) {
				//
				v = getMapentryBeanValue(v, elementByset);
				if (!"false".equalsIgnoreCase(v) & !"true".equalsIgnoreCase(v)) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("必须是true或false");
					break;
				}
				continue;
			}

			if ("Byte".equalsIgnoreCase(dataType)) {
				try {
					v = getMapentryBeanValue(v, elementByset);
					Byte.parseByte(v);

				} catch (NumberFormatException ns) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("必须是1个字节");
					break;
				}
				continue;
			}
			if ("Character".equalsIgnoreCase(dataType)) {
				v = getMapentryBeanValue(v, elementByset);
				if (v.length() > 1) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("必须是1个字符");
					break;
				}
				continue;
			}
			if ("Date".equalsIgnoreCase(dataType)) {
				String format = elementByset.attributeValue("formater");
				try {
					short num = cell.getCellStyle().getDataFormat();
					if (21 == num && !"HH:mm:ss".equals(format)) {
						throw new ParseException("", 0);
					} else if (20 == num && !"HH:mm".equals(format)) {
						throw new ParseException("", 0);
					}

					SimpleDateFormat dateFormat = new SimpleDateFormat(format);
					dateFormat.parse(v);
				} catch (ParseException ns) {
					b.append("第 ").append(rowNum).append(" 行").append(cn + 1).append("列,【").append(arrSet[cn].getKey())
							.append("】").append("不是指定的日期格式【").append(format).append("】");
					break;
				}
				continue;
			}

		}

		return b.toString();
	}

	/**
	 * <pre>
	 * 将excel类型的单元格数据按字符串返回,excel的 NUMERIC的日期格式按"yyyy/MM/dd HH:mm:ss"格式返回字符串
	 * </pre>
	 * 
	 * @param cell
	 * @param scale
	 *            精度格式 “#.00”小数点后两位，“0”整数
	 * @return
	 */
	public static String getCellStr(Cell cell, String scale) {
		String v = "";
		if (cell == null) {
			return v;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			v = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat dateformat = new SimpleDateFormat(DEFAULT_DATEFORMATER);
				Date date = cell.getDateCellValue();
				v = dateformat.format(date);
			} else {
				scale = (scale == null ? "0" : scale);
				DecimalFormat df = new DecimalFormat(scale);
				v = df.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			v = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			v = cell.getCellFormula();
			break;
		default:
			v = cell.getRichStringCellValue().getString();
		}
		return replaceBlank(v);
	}

	public static String replaceBlank(Cell cell, String scale) {
		String str = getCellStr(cell, scale);
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\t|\r|\n|\\u00A0|( {1,})");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 替换unicode空串
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (str != null && str.length() > 0) {
			Pattern p = Pattern.compile("\\t|\r|\n|\\u00A0|( {1,})");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}

	/**
	 * 将一个事务中的数据写入ResultRowinfo
	 * 
	 * @param set
	 * @param resultRow
	 * @param start
	 * @param rowNum
	 *            与err参数对应，当行数是rowNum时，使用err参数的错误信息,当值为-1时,所有行使用统一err
	 * @param err
	 */
	@SuppressWarnings("unchecked")
	public static void setErrorList(Set<Entry<Object, Row>> set, List<ErrRowinfo> resultRow, int start, int rowNum,
			String err) {
		Entry<Object, Row>[] arrSet = new Map.Entry[set.size()];
		set.toArray(arrSet);
		Boolean flag = false;
		if (rowNum <= arrSet.length && rowNum > 0) {
			flag = true;
		}

		for (int i = start; i < arrSet.length; i++) {
			Row row = arrSet[i].getValue();
			ErrRowinfo resultRowinfo = null;
			if (row.getRowNum() == rowNum) {
				resultRowinfo = new ErrRowinfo(row, err);
				resultRow.add(resultRowinfo);
				continue;
			}
			StringBuilder builder = new StringBuilder("");
			builder.append("第 ").append(row.getRowNum() + 1).append(" 行").append(",")
					.append(flag ? "事务回滚，同批数据中某个出错" : err);
			resultRowinfo = new ErrRowinfo(row, builder.toString());
			resultRow.add(resultRowinfo);
		}

	}

}

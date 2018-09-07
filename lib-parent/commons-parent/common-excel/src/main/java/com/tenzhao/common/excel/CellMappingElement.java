package com.tenzhao.common.excel;

import org.dom4j.Element;

/**
 * Excel中列位置对应的XML中property元素
 * @author chenxj
 *
 */
public class CellMappingElement {
	
	/** excel-bean.xml，attribute[default] **/
	private static final String TEMP_DEFAULT = "default";
	
	private Integer num;
	private Element element ;
	public CellMappingElement(Integer num,Element element){
		this.setNum(num) ;
		this.setElement(element) ;
	}
	public Element getElement() {
		return element;
	}
	
	public void setElement(Element element) {
		this.element = element;
	}
	
	public Integer getNum() {
		return num;
	}
	
	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getDefaultValue() throws NullPointerException{
		return element.attribute(TEMP_DEFAULT).getStringValue();
	}
}

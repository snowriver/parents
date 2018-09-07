package com.tenzhao.commons.wx.pay.utils;

import java.util.Set;
import java.util.SortedMap;

public final class XmlUtils {

	/**
	 * 构建XML参数
	 * @param form
	 * @return
	 */
	public static  String builtXmlArgs(SortedMap<String,String> form){
		Set<String> keys = form.keySet() ;
		StringBuilder xmlBuilder = new StringBuilder("<xml>");
		for(String key : keys){
			 Object v = form.get(key);
	         if(null != v && !"".equals(v)) {
	        	 xmlBuilder.append(String.format("<%s>%s</%s>", key,form.get(key),key));
	         }
		}
		xmlBuilder.append("</xml>");
		return xmlBuilder.toString();
	}
}

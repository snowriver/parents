package com.tenzhao.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 去除字符串中的制表、回车、换行符，并将两个以上空格替换为空 eg:<sdfl name>\r\n </asdf> return:<sdfl
	 * name></asdf>
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\t|\r|\n|( {1,})");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static boolean isEmptyStr(String source) {
		if (source == null || source.trim().length() == 0)
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param source
	 * @return
	 */
	public static boolean isNotEmptyStr(String source) {
		if (source == null || "".equals(source.trim()))
			return false;
		return true;
	}

	/**
	 * 字符串首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirst(String str) {
		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}

	/**
	 * 字符串首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirst(String str) {
		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}

	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}
	
}
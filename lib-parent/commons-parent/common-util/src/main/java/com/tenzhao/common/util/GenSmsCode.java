package com.tenzhao.common.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 短信验证码生成类
 * @author chenxj
 */
public class GenSmsCode {
	/**
	 * 生成指定长度短信验证码
	 * @param len
	 * @return
	 */
	public static String genSmsCode(Integer len){
		String strdouble = String.valueOf(RandomUtils.nextLong()) ;
		while(strdouble.length()<len){
			genSmsCode(len); 
		}
		return strdouble.substring(strdouble.length()-len, strdouble.length());
	}
	/**
	 * 创建小于指定整数的正整数含0
	 * @param num
	 * @return
	 */
	public static Integer genInt(int num){
		return RandomUtils.nextInt(num) ;
	}
}

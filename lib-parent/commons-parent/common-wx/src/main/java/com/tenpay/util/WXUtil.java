package com.tenpay.util;

import java.util.Random;

public class WXUtil {
	
	/**
	 * 回调函数响应给微信的内容
	 */
	public final static String NOTIFY_SUCCESS = "SUCCESS" ;
	public final static String NOTIFY_FAIL = "FAIL" ;
	public final static String NOTIFY_SUCCESS_MSG = "OK" ;
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
}

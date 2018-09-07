/*
 * Copyright 2014-2016 浙江腾朝互联科技有限公司.
 */

package com.tenzhao.zst.paychannel.o2oleader.constants;

/**
 * 移领api常量
 * <a href="http://api.020leader.com/index.php?s=/home/item/show/item_id/1">移领统一支付接口文档</a>
 * @author chenxj
 */
public final class O2OleaderConstants {
	
	public final static String URL = "https://mapi.020leader.com";
	public final static String QR_IMG_SERVICE_URL ="https://alipay.020leader.com";
	
	public enum Param_service{
		REFUND("unipay.acquire.refund"),
		QUERY("unipay.acquire.query"),
		CREATEANDPAY("unipay.acquire.createandpay"),
		SCAN_CODE_PAY("unipay.acquire.precreate");
		private String value ;
		private Param_service(String value){
			this.value = value ;
		}
		public String getValue() {
			return value;
		}
	}
	
	/** 订单类型码 */
	public enum Param_trans_type{
		BARCODE_OF_PAY("S1"),CANCEL_ORDER("S2"),REFUND("S3"),SCAN_OF_PAY("S4");
		private String value ;
		private Param_trans_type(String value){
			this.value = value ;
		}
		public String getValue() {
			return value;
		}
	}
	/** 交易类型码 */
	public enum Param_dynamic_type{
		WXPAY((short)1),ALIPAY((short)2),BAIDU_WALLET((short)3),ALIPAY2((short)4),YZF((short)5),MOBILE_QQ((short)9);
		private short value ;
		private Param_dynamic_type(short value){
			this.value = value ;
		}
		public short getValue() {
			return value;
		}
		public static Param_dynamic_type getInstance(short value){
			for(Param_dynamic_type dynamic_type : Param_dynamic_type.values()){
	    		if(value == dynamic_type.getValue()){
	    			return dynamic_type ;
	    		}
	    	}
			return null ;
		}
	}
	/** 支付类型 */
	public enum Param_pay_type{
		WXPAY((short)1),ALIPAY((short)2),BAIDU_WALLET((short)3),ALIPAY2((short)4),YZF((short)5),MOBILE_QQ((short)9);
		private short value ;
		private Param_pay_type(short value){
			this.value = value ;
		}
		public short getValue() {
			return value;
		}
	}
	
	/**
	 * 条码支付返回的result_code
	 * unknown交易失败，paying等待支付，success交易成功
	 * @author chenxj
	 */
	public enum EnumCreateandpayResultCode{
		unknown,paying,success;
	}
	
}



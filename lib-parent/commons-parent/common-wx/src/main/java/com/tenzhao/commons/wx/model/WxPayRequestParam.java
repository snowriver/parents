package com.tenzhao.commons.wx.model;

import java.io.Serializable;

import me.chanjar.weixin.common.util.RandomUtils;

/**
 * 微信支付获取prepayid的请求参数
 * @author chenxj
 */
public class WxPayRequestParam implements Serializable {
	private static final long serialVersionUID = -2449264458053200019L;
	
	/**
	 * @param out_trade_no 内部订单号
	 * @param body         订单描述
	 * @param total_fee    订单金额 单位：分
	 * @param notify_url   回调地址
	 */
	public WxPayRequestParam(String out_trade_no ,String body,Integer total_fee,String notify_url,String attach){
		this.out_trade_no = out_trade_no ;
		this.body = body ;
		this.total_fee = total_fee ;
		this.notify_url = notify_url ;
		this.nonce_str = RandomUtils.getRandomStr();
		this.attach = attach ;
	}
	/** 设备号 */
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	/** 随机字符串  Required=true */
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	/** 商品描述 Required=true */
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	/** 商品详情 Required=false */
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/** 签名类型  Required=false */
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	/** 附加数据 在查询API和支付通知中原样返回  Required=false */
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	/** 商户订单号  Required=true */
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	/** 货币类型  Required=false*/
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	/** 总金额单位：分  Required=true */
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	/** 终端IP-用户端实际ip Required=true */
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	/** 交易起始时间  Required=false */
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	/** 交易结束时间 Required=false */
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	/** 商品标记 Required=false */
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	/** 微信回调通知地址 Required = true */
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	/** 交易类型 Required=true */
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	/** 指定支付方式 Required=false */
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
	/** 签名 Required=true */
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	/** 应用ID Required=true*/
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/** 商户号  Required=true */
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	
	private String appid ;
	
	private String mch_id;
	
	private String device_info="WEB" ;
	
	private String nonce_str;
	
	private String body;
	
	private String detail;
	
	private String sign_type = "MD5";
	
	/** 自定义数据  */
	private String attach;
	
	private String out_trade_no ;
	
	private String fee_type = "CNY";
	
	private Integer total_fee;
	
	private String spbill_create_ip="127.0.0.1";
	
	private String time_start;
	
	private String time_expire;
	
	private String goods_tag ;
	
	private String notify_url="https://hdtest.tenzhao.com/api/voip/wxpay/notify" ;
	
	private String trade_type="APP" ;
	
	private String limit_pay ="no_credit" ;
	
	private String sign ;
	
}

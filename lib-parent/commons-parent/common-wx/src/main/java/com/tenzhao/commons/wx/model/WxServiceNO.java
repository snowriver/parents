package com.tenzhao.commons.wx.model;

/**
 * 微信服务号信息
 * @author chenxj
 */
public class WxServiceNO {
	/** 服务号下的appId */
	private String appid = "wx677b3f8f2aec738c";
	/** api密钥(商户key) */
	private String apiKey = "3a2503e609939f40e23c162e8fc0681f" ;
	/** 微信分配的商户号 */
	private String mch_id = "1416903602";
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appId) {
		this.appid = appId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
}

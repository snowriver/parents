package com.getui.java;

/**
 * 个推android,列表推送的个人用户信息
 * @author chenxj
 */
public class GeTuiTarget extends com.gexin.rp.sdk.base.impl.Target {
	private String alias ;
	private String appId ;
	private String clientId;
	private Short deviceType ;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Short getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Short deviceType) {
		this.deviceType = deviceType;
	}
}

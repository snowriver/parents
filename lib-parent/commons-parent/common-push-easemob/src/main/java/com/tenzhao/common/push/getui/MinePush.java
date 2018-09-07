package com.tenzhao.common.push.getui;

import com.gexin.rp.sdk.http.IGtPush;
import com.tenzhao.common.push.EnumPushMode;

public class MinePush extends IGtPush {
	
	private String app_id ;
	private String app_key ;
	private String app_secret ; 
	private String master_secret;
	private EnumPushMode pushMode ;
	public MinePush(String appKey, String masterSecret) {
		super(appKey, masterSecret);
	}
	public MinePush(String appKey, String masterSecret,EnumPushMode pushMode) {
		super(appKey, masterSecret);
		this.pushMode = pushMode ;
	}
	public MinePush(String appKey, String masterSecret,boolean useSSL) {
		super(appKey, masterSecret,true);
	}
	public MinePush(String appId, String appKey, String appSecret, String masterSecret,EnumPushMode pushMode) {
		super(appKey, masterSecret,true);
		this.app_id = appId;
		this.app_key = appKey;
		this.app_secret = appSecret ;
		this.master_secret = masterSecret;
		this.pushMode = pushMode;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}
	public String getMaster_secret() {
		return master_secret;
	}
	public void setMaster_secret(String master_secret) {
		this.master_secret = master_secret;
	}
	
	/**
	 * @return the pushMode
	 */
	public EnumPushMode getPushMode() {
		return pushMode;
	}
	
	/**
	 * @param pushMode the pushMode to set
	 */
	public void setPushMode(EnumPushMode pushMode) {
		this.pushMode = pushMode;
	}
}

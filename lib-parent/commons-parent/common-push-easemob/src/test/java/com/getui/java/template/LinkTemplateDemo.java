package com.getui.java.template;

import com.gexin.rp.sdk.template.LinkTemplate;

public class LinkTemplateDemo {
	public static final String appId = "TxzlIyCcfS9KuENjjP4ux1";
	public static final String appKey = "rAnoicfrNX7915IxPocAL2";
	public static final String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";   
	public static final String CID = "e605a0db5ce3cca9b76b012978064940";

	public static LinkTemplate linkTemplateDemo(String appId, String appKey) {
	    LinkTemplate template = new LinkTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // 设置通知栏标题与内容
	    template.setTitle("请输入通知栏标题");
	    template.setText("请输入通知栏内容");
	    // 配置通知栏图标
	    template.setLogo("icon.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 设置打开的网址地址
	    template.setUrl("http://www.getui.com");
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	
	public static void main(String[] args) {
		linkTemplateDemo(appId,appKey);
	}
}

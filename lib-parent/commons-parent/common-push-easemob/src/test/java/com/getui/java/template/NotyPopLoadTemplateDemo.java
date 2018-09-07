package com.getui.java.template;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;

public class NotyPopLoadTemplateDemo implements GetuiInfoTest {
	
	
	public static NotyPopLoadTemplate notyPopLoadTemplateDemo(String appId, String appKey) {
	    NotyPopLoadTemplate template = new NotyPopLoadTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // 设置通知栏标题与内容
	    template.setNotyTitle("请输入通知栏标题");
	    template.setNotyContent("请输入通知栏内容");
	    // 配置通知栏图标
	    template.setNotyIcon("icon.png");
	    template.setLogoUrl("http://pic-src.tenzhao.com/share/shareLogo.png");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setBelled(true);
	    template.setVibrationed(true);
	    template.setCleared(true);
	    // 设置弹框标题与内容
	    template.setPopTitle("弹框标题");
	    template.setPopContent("弹框内容");
	    // 设置弹框显示的图片
	    template.setPopImage("");
	    template.setPopButton1("下载");
	    template.setPopButton2("取消");
	    // 设置下载标题
	    template.setLoadTitle("下载标题");
	    template.setLoadIcon("file://icon.png");
	    //设置下载地址        
	    template.setLoadUrl("http://gdown.baidu.com/data/wisegame/80bab73f82cc29bf/shoujibaidu_16788496.apk");
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	
	public static void main(String[] args) {
		notyPopLoadTemplateDemo(APP_ID, APP_KEY);
	}
}

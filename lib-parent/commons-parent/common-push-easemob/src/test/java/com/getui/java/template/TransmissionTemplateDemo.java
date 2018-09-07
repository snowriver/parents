package com.getui.java.template;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class TransmissionTemplateDemo implements GetuiInfoTest {
 
	public static final String CID = "32e81d3fe6e6ce94b13fe1e16399fcb3623f29204e45c3f3fa84a0c0b7c494ff";
	
	public static TransmissionTemplate transmissionTemplateDemo() {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(APP_ID);
	    template.setAppkey(APP_KEY);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);
	    template.setTransmissionContent("请输入需要透传的内容");
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	
	public static void main(String[] args) {
		transmissionTemplateDemo();
	}
	
}

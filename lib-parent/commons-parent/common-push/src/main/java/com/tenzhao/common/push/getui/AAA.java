package com.tenzhao.common.push.getui;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class AAA {
	
	static final String APP_ID = "4w20jkrbONAWlpI9wlt4S6";
	static final String APP_KEY = "O24DCRZ7YW7qNPQvLg4CG2";
	static final String APP_SECRET ="uCg1MNUpVlAbnVSjQE0YI2" ; 
	static final String MASTER_SECRET="CtpcXuHLQ77lwUS4FZegn7";
	public static void main(String[] args) {
		IGtPush push = new IGtPush(APP_KEY, MASTER_SECRET, true);
		// 此处true为https域名， false为http， 默认为false。 Java语言推荐使用此方式
		// IGtPush push = new IGtPush(host, appkey, master);
		// host为域名， 根据域名区分是http协议/https协议
		LinkTemplate template = linkTemplateDemo();
//		NotyPopLoadTemplate template = notyPopLoadTemplateDemo("","");
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间， 单位为毫秒， 可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		message.setPushNetWorkType(0); //
		//可选， 判断是否客户端是否wifi环境下推送， 1为在WIFI环境下， 0为不限制网络环境。
		Target target = new Target();
		target.setAppId(APP_ID);
		target.setClientId("35565a3c830559b1b084dce42addc033");
		// 用户别名推送， cid和用户别名只能2者选其一
		// String alias = "个";
		// target.setAlias(alias);
		IPushResult ret = null;
		try {
		ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
		e.printStackTrace();
		ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
		System.out.println(ret.getResponse().toString());
		} else {
		System.out.println("服务器响应异常");
		}
	}
	public static LinkTemplate linkTemplateDemo() {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(APP_ID);
		template.setAppkey(APP_KEY);
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("助商通链接推送");
		style.setText("链接的是什么东西");
		// 配置通知栏图标
	//	style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("http://pic-src.tenzhao.com/share/shareLogo.png");
		// 设置通知是否响铃， 震动， 或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		// 设置打开的网址地址
		template.setUrl("https://detail.tmall.com/item.htm?id=537696347867&ali_trackid=2:mm_126678695_37240069_135528162:1510279789_210_1729938205&clk1=16f772788762706844f81dba9293d9be&upsid=16f772788762706844f81dba9293d9be");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	public static NotyPopLoadTemplate notyPopLoadTemplateDemo(String appId, String appKey) {
		NotyPopLoadTemplate template = new NotyPopLoadTemplate();
		// 设置APPID与APPKEY
		template.setAppId(APP_ID);
		template.setAppkey(APP_KEY);
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
	//	style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("http://pic-src.tenzhao.com/share/shareLogo.png");
		// 设置通知是否响铃， 震动， 或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		// 设置弹框标题与内容
		template.setPopTitle("弹框标题");
		template.setPopContent("弹框内容");
		// 设置弹框显示的图片
		template.setPopImage("http://pic-src.tenzhao.com/share/shareLogo.png");
		template.setPopButton1("关闭");
		template.setPopButton2("取消");
		// 设置下载标题
		template.setLoadTitle("下载标题");
		template.setLoadIcon("file://icon.png");
		//设置下载地址
		template.setLoadUrl("http://www.baidu.com");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
		}
	
}

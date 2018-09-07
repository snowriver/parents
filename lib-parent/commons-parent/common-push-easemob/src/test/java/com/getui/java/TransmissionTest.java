package com.getui.java;

import java.io.IOException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class TransmissionTest {

	// 定义常量, appId、 appKey、 masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
	private static String appId = GetuiInfoTest.APP_ID;
	private static String appKey = GetuiInfoTest.APP_KEY;
	private static String masterSecret = GetuiInfoTest.MASTER_SECRET;
	private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

	public static void main(String[] args) throws IOException {
		IGtPush push = new IGtPush(appKey, masterSecret);
//		LinkTemplate template = linkTemplateDemo();
		
		TransmissionTemplate template = getTemplate();
		
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId("23f1b5b5d633b7b55d6909c588aef9c1");//"7123f9b16b2280807df1a1cb110bcf8c"
		// target.setAlias(Alias);
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
		template.setAppId(appId);
		template.setAppkey(appKey);
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
//		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("http://pic-src.tenzhao.com/share/shareLogo.png");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		// 设置打开的网址地址
		template.setUrl("http://www.baidu.com");
		return template;
	}
	
	
	
	//
	public static TransmissionTemplate getTemplate() {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent("透传内容");
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		//在已有数字基础上加1显示， 设置为-1时， 在已有数字上减1显示， 设置为数字时， 显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("$由客户端定义");
		//简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
		//字典模式使用APNPayload.DictionaryAlertMsg
		//payload.setAlertMsg(getDictionaryAlertMsg());
		// 添加多媒体资源
//		payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
//		.setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
//		.setOnlyWifi(true));
		template.setAPNInfo(payload);
		return template;
		}
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody("body");
		alertMsg.setActionLocKey("ActionLockey");alertMsg.setLocKey("LocKey");
		alertMsg.addLocArg("loc-args");
		alertMsg.setLaunchImage("launch-image");
		// iOS8.2以上版本支持
		alertMsg.setTitle("Title");
		alertMsg.setTitleLocKey("TitleLocKey");
		alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
		}
}

package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
public class Noname1 {
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "4w20jkrbONAWlpI9wlt4S6";
    private static String appKey = "O24DCRZ7YW7qNPQvLg4CG2";
    private static String masterSecret = "CtpcXuHLQ77lwUS4FZegn7";

    static String CID1 = "6c6d6a4c8e0d736d77c88801704648ec";
    static String CID2 = "f39d8b70ad0c9ecdf68695537876f514";
    //别名推送方式
    // static String Alias1 = "";
    // static String Alias2 = "";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) throws Exception {
        // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin_pushList_needDetails", "true");
    // 配置返回每个别名及其对应cid的用户状态，可选
    // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        // 通知透传模板
//        NotificationTemplate template = notificationTemplateDemo();
        TransmissionTemplate template = getTemplate();
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List targets = new ArrayList();
        Target target1 = new Target();
        Target target2 = new Target();
        target1.setAppId(appId);
        target1.setClientId(CID1);
   //     target1.setAlias(Alias1);
        target2.setAppId(appId);
        target2.setClientId(CID2);
   //     target2.setAlias(Alias2);
        targets.add(target1);
        targets.add(target2);
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        System.out.println(ret.getResponse().toString());
    }
    public static NotificationTemplate notificationTemplateDemo() {
        NotificationTemplate template = new NotificationTemplate();
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
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }
    
    public static TransmissionTemplate getTemplate() {
    	TransmissionTemplate template = new TransmissionTemplate();
    	template.setAppId(appId);
    	template.setAppkey(appKey);
    	template.setTransmissionContent("透传内容");
    	template.setTransmissionType(2);
    	APNPayload payload = new APNPayload();
    	payload.setBadge(1);
//    	payload.setContentAvailable(1);
    	payload.setSound("default");
    	payload.setCategory("$由客户端定义");
    	//简单模式APNPayload.SimpleMsg
    	payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
    	//字典模式使用下者
    	//payload.setAlertMsg(getDictionaryAlertMsg());
    	template.setAPNInfo(payload);
    	return template;
    	}
    	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
    	APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
    	alertMsg.setBody("body");
    	alertMsg.setActionLocKey("ActionLockey");
    	alertMsg.setLocKey("LocKey");
    	alertMsg.addLocArg("loc-args");
    	alertMsg.setLaunchImage("launch-image");
    	// IOS8.2以上版本支持
    	alertMsg.setTitle("Title");
    	alertMsg.setTitleLocKey("TitleLocKey");
    	alertMsg.addTitleLocArg("TitleLocArg");
    	return alertMsg;
    	}
    	
	    public static TransmissionTemplate getTransmissionTemplate(String str,String transmissionMsg) {
	    	TransmissionTemplate template = new TransmissionTemplate();
	    	template.setAppId(appId);
	    	template.setAppkey(appKey);
	    	template.setTransmissionContent(transmissionMsg);
	    	template.setTransmissionType(2);
	    	APNPayload payload = new APNPayload();
	    	// payload.setContentAvailable(1); //apns静默推送
	    	payload.setSound("default");
	    	payload.setAlertMsg(getDictionaryAlertMsg(null));
	    	template.setAPNInfo(payload);
	    	return template;
	    }
	    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String pushModel){
	    	APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    	alertMsg.setBody("body");
	    	// IOS8.2以上版本支持
	    	alertMsg.setTitle("助商通");
	    	return alertMsg;
	    }
}

	
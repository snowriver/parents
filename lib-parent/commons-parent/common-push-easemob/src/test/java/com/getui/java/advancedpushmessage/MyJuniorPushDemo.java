package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.tenzhao.common.push.PushModel;

public class MyJuniorPushDemo implements GetuiInfoTest {
	static IGtPush push = new IGtPush(APP_KEY, MASTER_SECRET, true);

	public static void main(String[] args) throws Exception {
		PushModel pushModel = new PushModel();
		pushModel.setTitle("标题");

		List<Target> listTarget = new ArrayList<Target>();
		Target target = new Target();
		target.setAppId(APP_ID);
		target.setClientId("6c6d6a4c8e0d736d77c88801704648ec");
		Target target1 = new Target();
		target1.setAppId(APP_ID);
		target1.setAlias("18768151122");
		//target1.setClientId(CID1);
		listTarget.add(target);
		listTarget.add(target1);
		iosPush(pushModel, "看到了透传内容吗111", listTarget);
	}

	@Deprecated
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg() {
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

	public static void iosPush(PushModel pushModel, String transmissionMsg, List<Target> listTarget) {
		TransmissionTemplate apnTemplate = getTransmissionTemplate(pushModel, transmissionMsg);
		ListMessage message = new ListMessage();
		message.setData(apnTemplate);
		// 设置消息离线，并设置离线时间 ,此处不设置IOS关闭无法收到APNS透传
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
		String contentId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(contentId, listTarget);
		System.out.println(ret.getResponse().toString());
	}

	public static TransmissionTemplate getTransmissionTemplate(PushModel pushModel, String transmissionMsg) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(APP_ID);
		template.setAppkey(APP_KEY);
		template.setTransmissionContent(transmissionMsg);
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		// payload.setContentAvailable(1); //apns静默推送
		payload.setSound("default");
		payload.setAlertMsg(getDictionaryAlertMsg(pushModel));
		template.setAPNInfo(payload);
		return template;
	}

	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(PushModel pushModel) {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody(pushModel.getTitle());
//		 alertMsg.setActionLocKey("ActionLockey");
//		    alertMsg.setLocKey("LocKey");
//		    alertMsg.addLocArg("loc-args");
//		    alertMsg.setLaunchImage("launch-image");
		// IOS8.2以上版本支持
		alertMsg.setTitle("助商通");
//		 alertMsg.setTitleLocKey("TitleLocKey");
//		 alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}

}
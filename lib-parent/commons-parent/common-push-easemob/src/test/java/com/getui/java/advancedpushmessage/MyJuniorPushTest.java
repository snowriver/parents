package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.tenzhao.common.push.getui.GetuiInfo;
public class MyJuniorPushTest implements GetuiInfo{
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换 

	static IGtPush push = new IGtPush(APP_KEY, MASTER_SECRET,true);
	static String devicetoken = "c3cec42bb80fe1224afd3adb10df88afe5a006107cf0c5a7ca1da6919137f60d";
    public static void apnpush() throws Exception {

       APNTemplate t = new APNTemplate();
       APNPayload apnpayload = new APNPayload();
       apnpayload.setSound("");
       APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
       //通知文本消息标题
       alertMsg.setTitle("aaaaaa");
       //通知文本消息字符串
       alertMsg.setBody("bbbb");
       //对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
       alertMsg.setTitleLocKey("ccccc");
       //指定执行按钮所使用的Localizable.strings
       alertMsg.setActionLocKey("ddddd");
       apnpayload.setAlertMsg(alertMsg);
       
       t.setAPNInfo(apnpayload);  
       ListMessage message = new ListMessage();
       message.setData(t);
       String contentId = push.getAPNContentId(APP_ID, message);
       System.out.println(contentId);
       List<String> dtl = new ArrayList<String>();
       dtl.add(devicetoken);
     //  System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
      IPushResult ret = push.pushAPNMessageToList(APP_ID, contentId, dtl);
       System.out.println(ret.getResponse());
    }

    public static void main(String[] args) throws Exception {
       apnpush();
    }
}
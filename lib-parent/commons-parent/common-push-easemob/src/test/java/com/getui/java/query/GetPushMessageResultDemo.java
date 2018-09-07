package com.getui.java.query;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetPushMessageResultDemo {
	//您应用的appkey
	static String appKey = "rAnoicfrNX7915IxPocAL2";
	//您应用的mastersecret
	static String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";   
    //您要查询的taskid
    private static final String TASKID = "OSL-0406_YOKKckfqBeAjURXnzt7L04";

    static String host ="http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) {

        IGtPush push = new IGtPush(host,appKey, masterSecret);
        System.out.println(push.getPushResult(TASKID).getResponse());
        IPushResult result=push.getPushResult(TASKID);
        String msgTotal =result.getResponse().get("msgTotal").toString();
        String clickNum=result.getResponse().get("clickNum").toString();
        String msgProcess =result.getResponse().get("msgProcess").toString();
        System.out.println("总下发数:"+msgTotal+"|点击数:"+clickNum+"|下发的消息总数:"+msgProcess);

    }

}
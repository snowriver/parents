package com.getui.java.query;
import java.util.Map;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetSingalDayPushMessageResultDemo {
	//您应用的appId
	private static String APPID = "TxzlIyCcfS9KuENjjP4ux1";
	//您应用的appkey
	private static String APPKEY = "rAnoicfrNX7915IxPocAL2";
	//您应用的mastersecret
	private static String MASTERSECRET = "KFDNBNKAVj9bgykwvqgeA5"; 

    static String host ="http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) {

        IGtPush push = new IGtPush(host,APPKEY, MASTERSECRET);

        IQueryResult result = push.queryAppPushDataByDate(APPID, "20150525");
        Map<String, Object> data = (Map<String, Object>)result.getResponse().get("data");
        System.out.println(data);
        System.out.println("发送总数:"+data.get("sendCount"));
        System.out.println("在线下发数:"+data.get("sendOnlineCount"));
        System.out.println("接收总数:"+data.get("receiveCount"));
        System.out.println("展示总数:"+data.get("showCount"));
        System.out.println("点击总数:"+data.get("clickCount"));     
    }

}
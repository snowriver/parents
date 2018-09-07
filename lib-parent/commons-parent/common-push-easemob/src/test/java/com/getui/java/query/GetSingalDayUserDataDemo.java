package com.getui.java.query;

import java.util.Map;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetSingalDayUserDataDemo {
	//您应用的appId
	private static String APPID = "TxzlIyCcfS9KuENjjP4ux1";
	//您应用的appkey
	private static String APPKEY = "rAnoicfrNX7915IxPocAL2";
	//您应用的mastersecret
	private static String MASTERSECRET = "KFDNBNKAVj9bgykwvqgeA5";   
	
    static String host ="http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) {

        IGtPush push = new IGtPush(host,APPKEY, MASTERSECRET);

        IQueryResult result = push.queryAppUserDataByDate(APPID, "20150525");
        Map<String, Object> data = (Map<String, Object>)result.getResponse().get("data");
        System.out.println(result.getResponse().toString());

          System.out.println("新用户注册总数:"+data.get("newRegistCount"));
          System.out.println("用户注册总数:"+data.get("registTotalCount"));
          System.out.println("活跃用户数:"+data.get("activeCount"));
          System.out.println("在线用户数:"+data.get("onlineCount"));

    }

}
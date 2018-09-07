package com.getui.java.query;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetTaskGroupNamePushResultDemo {
	
    public static String host = "";
    //您应用的mastersecret
    public static  String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";
    //您应用的appkey
    public static  String appkey = "rAnoicfrNX7915IxPocAL2";
    //您应用的appid
    public static  String appId = "TxzlIyCcfS9KuENjjP4ux1";
    
    public static String groupName = "任务别名_toApp";
    
    public static void testPushResultByGroupName() {
        final String masterSecret = "TBokfpttQJ6aHIhBE9y867";
        IGtPush push = new IGtPush(appkey, masterSecret);
        IQueryResult queryResult = push.getPushResultByGroupName(appId, groupName);
        System.out.println(queryResult);
        long msgTotal = (Integer)queryResult.getResponse().get("msgTotal");
        long clickNum = (Integer)queryResult.getResponse().get("clickNum");
        long onlineNum = (Integer)queryResult.getResponse().get("onlineNum");
        long msgRecive = (Integer)queryResult.getResponse().get("msgProcess");
        long showNum = (Integer)queryResult.getResponse().get("showNum");
        System.out.println("百日内活跃用户数：" + msgTotal + "|实际下发数"+ onlineNum + "|消息接收数" + msgRecive + "展示数|" + showNum + "|消息点击数" + clickNum);
    }

    public static void main(String[] args) {
        testPushResultByGroupName();
    }
}
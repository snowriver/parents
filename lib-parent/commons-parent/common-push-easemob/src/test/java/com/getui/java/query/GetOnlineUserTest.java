package com.getui.java.query;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;
public class GetOnlineUserTest {
    //您应用的mastersecret
    public static  String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";
    //您应用的appkey
    public static  String appkey = "rAnoicfrNX7915IxPocAL2";
    //您应用的appid
    public static  String appId = "TxzlIyCcfS9KuENjjP4ux1";
    public static String groupName = "任务别名_toApp";
    public static void testGetOnlineUser() {
        IGtPush push = new IGtPush(appkey, masterSecret);
        IQueryResult queryResult = push.getLast24HoursOnlineUserStatistics(appId);
        System.out.println(queryResult);
        System.out.println(queryResult.getResponse().get("appId"));
        System.out.println(queryResult.getResponse().get("onlineStatics"));
    }

    public static void main(String[] args) {
        testGetOnlineUser();
    }
}
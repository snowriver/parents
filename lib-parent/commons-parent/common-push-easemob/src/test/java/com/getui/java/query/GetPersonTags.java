package com.getui.java.query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetPersonTags {
    List<String> cidList = new ArrayList<String>();

    //您应用的mastersecret
    public static  String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";
    //您应用的appkey
    public static  String appkey = "rAnoicfrNX7915IxPocAL2";
    //您应用的appid
    public static  String appId = "TxzlIyCcfS9KuENjjP4ux1";

    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    //使用https的域名
  //static String host = "https://api.getui.com/apiex.htm";

    public static void main(String[] args) throws Exception {
        IGtPush push = new IGtPush(host, appkey, masterSecret);
        push.connect();
        GetUserStatus.getUserStatus();
    }

    public static void getPersonaTags() {
        IGtPush push = new IGtPush(host, appkey, masterSecret);
        IQueryResult abc = push.getPersonaTags(appId);
        System.out.println(abc.getResponse());
    }
}
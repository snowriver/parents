package com.getui.java.query;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetUserStatus {
	//您应用的appId
	static String appId = "TxzlIyCcfS9KuENjjP4ux1";
	//您应用的appkey
	static String appKey = "rAnoicfrNX7915IxPocAL2";
	//您应用的mastersecret
	static String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";   
	static String CID = "e605a0db5ce3cca9b76b012978064940";

    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) throws Exception {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        push.connect();
        getUserStatus();
    }

    public static void getUserStatus() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        IQueryResult abc = push.getClientIdStatus(appId, CID);
        System.out.println(abc.getResponse());
    }
}
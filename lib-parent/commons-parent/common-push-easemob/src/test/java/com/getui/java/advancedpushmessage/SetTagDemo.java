package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class SetTagDemo {
	static String appId = "TxzlIyCcfS9KuENjjP4ux1";
	static String appKey = "rAnoicfrNX7915IxPocAL2";
	static String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";   
	static String CID = "e605a0db5ce3cca9b76b012978064940";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws Exception {
        setTag();
        System.out.println(CID);

    }
    public static void setTag() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        List<String> tagList = new ArrayList<String>();
                tagList.add(String.valueOf("18-20"));
                tagList.add("杭州");
                tagList.add("美女");
        IQueryResult ret = push.setClientTag(appId, CID, tagList);
        System.out.println(ret.getResponse().toString());
    }
}
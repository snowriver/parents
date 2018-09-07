package com.getui.java.advancedpushmessage;

import com.gexin.rp.sdk.http.IGtPush;

public class StopTaskDemo {
	static String appId = "TxzlIyCcfS9KuENjjP4ux1";
	static String appKey = "rAnoicfrNX7915IxPocAL2";
	static String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";   
    static String taskid = "taskid-";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void stopTask() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        boolean result = push.stop(taskid);
        System.out.println(result);
    }
    public static void main(String[] args) {
        stopTask();
    }
}
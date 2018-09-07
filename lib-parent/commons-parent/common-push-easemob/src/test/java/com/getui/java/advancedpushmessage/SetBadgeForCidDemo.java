package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;
public class SetBadgeForCidDemo implements GetuiInfoTest{
	//请填入IOS用户唯一标识
    public static String deviceToken = "5b6f8c6cdc5ab8352a0f7357888308c17fe115d9d162c2a638ad336bc9f50f85";
	
    //请填入你的clientid
    public static String cid = "548e1e5a08ce0380d31faba6256e9eb7";
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    public static String masterSecret = "5vjiwMEaij5VvYf7VhlGM4";
    public static String appId = "nSJtifqVSI7HkPrKHlxhD6";
    public static String appkey = "WlZGdJlcUB8ds32Y2Thn91";
    public static void setBadgeForCid() {
        List<String> cidList = new ArrayList<String>();
        //用户应用icon上显示的数字
        String badge = "";
        cidList.add( cid );
        IGtPush push = new IGtPush(appkey, masterSecret);
        //"+1"即在原有badge上加1；具体详情使用请参考该接口描述
        badge = "+1";
        //"-1"即在原有badge上减1；具体详情使用请参考该接口描述
        //badge = "-1";
        //直接设置badge数值，会覆盖原有数值；具体详情使用请参考该接口描述
        //badge = "1";
        IQueryResult res = push.setBadgeForCID(badge, appId, cidList) ; 
        System.out.println(res);
    }

    public static void main(String[] args) {
        setBadgeForCid();
    }
}

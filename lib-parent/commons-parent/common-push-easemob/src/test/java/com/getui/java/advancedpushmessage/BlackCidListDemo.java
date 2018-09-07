package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;
/**
 * 黑名单处理 
 * @author chenxj
 */
public class BlackCidListDemo implements GetuiInfoTest {
	
	static final String CLIENT_ID_1 = "" ;
	static final String CLIENT_ID_2 = "" ;
	static IGtPush push = new IGtPush( APP_KEY, MASTER_SECRET,true);
	/**
	 * 增加黑名单
	 */
    public static void addBlackCidList(List<String> cidList) {
        cidList.add(CLIENT_ID_1);
        cidList.add(CLIENT_ID_2);
        IPushResult pushResult1 = push.addCidListToBlk(APP_ID, cidList);
        System.out.println("黑名单增加结果：" + pushResult1.getResultCode());
    }
    
    /**
     * 删除黑名单--从黑名单中还原
     * @param args
     */
    public static void delRestoreCidList(List<String> cidList) {
        cidList.add(CID1);
        IPushResult pushResult = push.restoreCidListFromBlk(APP_ID, cidList);
        System.out.println("黑名单删除结果：" + pushResult.getResultCode());
    }
    public static void main(String[] args) {
    	addBlackCidList(new ArrayList<String>());
    }
}
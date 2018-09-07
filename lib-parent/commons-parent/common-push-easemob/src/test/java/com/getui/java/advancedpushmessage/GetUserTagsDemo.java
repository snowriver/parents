package com.getui.java.advancedpushmessage;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;

/**
 * 获取用户标签
 * @author chenxj
 */
public class GetUserTagsDemo implements GetuiInfoTest {
	static IGtPush push = new IGtPush( APP_KEY, MASTER_SECRET,true);
    public static void main(String[] args) throws Exception {
        getUserTags();
    }

    public static void getUserTags() {
        IPushResult result = push.getUserTags(APP_ID, CID1);
        System.out.println(result.getResponse().toString());
    }
}
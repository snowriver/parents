package com.getui.java.advancedpushmessage;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;

public class PushToAPP implements GetuiInfoTest {
  //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
   static IGtPush push = new IGtPush( APP_KEY, MASTER_SECRET,true);
   public static void main(String[] args) throws Exception {

      AppMessage message = new AppMessage();
      message.setSpeed(100);//全量推送个推控制下发速度在100条/秒，只有toApp支持定速推送。
//      message.setData(data);
   }
}
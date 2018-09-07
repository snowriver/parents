package com.getui.java.advancedpushmessage;

import java.util.ArrayList;
import java.util.List;

import com.getui.java.GetuiInfoTest;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;

/**
 * 绑定别名
 * @author chenxj
 */
public class AliasFunction implements GetuiInfoTest{
   //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	
    static IGtPush push = new IGtPush( APP_KEY, MASTER_SECRET,true);
	public static void main(String[] args) throws Exception {
		String alias = "15924186464" ;
		//bindAlias(alias,"b7921036be03882e25ac91ba167d4349");
		getCIDByAlias(alias);
//		aliasUnBind(alias);
	
	}
	
	/**
	 * 给客户端id绑定一个别名
	 * @param alias
	 * @param clientId
	 */
	static void bindAlias(String alias,String clientId){
		 IAliasResult bindSCid = push.bindAlias(APP_ID, alias, clientId);
		 System.out.println("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
	}
	
	/**
	 * 同时给多个用户绑定别名
	 * @param target
	 */
	static void bindAlias(List<Target> lstTarget){
		List<Target> Lcids = new ArrayList<Target>();
		Target target1 = new Target();
		target1.setClientId(CID1);
		target1.setAlias("个推1");
//		Target target2 = new Target();
//		target2.setClientId(CID2);
//		target2.setAlias("个推2");
		Lcids.add(target1);
//		Lcids.add(target2);
		IAliasResult bindLCid = push.bindAlias(APP_ID, Lcids);
		System.out.println(bindLCid.getResult());
		System.out.println(bindLCid.getErrorMsg());	
	}
	
	/**
	 * 根据别名获取CID
	 * @param alias
	 */
	static void getCIDByAlias(String alias){
		IAliasResult queryClient = push.queryClientId(APP_ID, alias);
	    System.out.println("根据别名获取的CID：" + queryClient.getClientIdList());
	}
	
	/**
	 * 根据clientId获取别名
	 * @param clientId
	 */
	static void getAliasByCID(String clientId){
		 IAliasResult queryAlias = push.queryAlias(APP_ID, clientId);
	     System.out.println("根据CID获取的别名：" + queryAlias.getAlias());
	}
	
	/**
	 * 解除别名绑定
	 * @throws Exception
	 */
	static void aliasUnBind(String alias,String clientId) throws Exception {
        IAliasResult AliasUnBind = push.unBindAlias(APP_ID, alias, clientId);
        System.out.println("解除绑定结果:" + AliasUnBind.getResult());
    }
	
	/**
	 * 解除别名绑定,
	 * @throws Exception
	 */
	static void aliasUnBind(String alias){
		 IAliasResult AliasUnBindAll = push.unBindAliasAll(APP_ID, alias);
	     System.out.println("解除绑定结果:" + AliasUnBindAll.getResult());
	}
}
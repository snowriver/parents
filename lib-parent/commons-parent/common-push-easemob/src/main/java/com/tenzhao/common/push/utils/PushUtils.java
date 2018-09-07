package com.tenzhao.common.push.utils;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.http.IGtPush;
import com.tenzhao.common.push.EnumApps;
import com.tenzhao.common.push.getui.GetuiPush;
import com.tenzhao.common.push.getui.PushApps;

public class PushUtils {

	/**
	 * @param clientId 推送平台对应的每个终端的id 标识
	 * @param alias 使用手机号码
	 */
	public static void bindAlias(String clientId,String alias){
		IGtPush gtPush = PushApps.getUserPush();
		gtPush.bindAlias(GetuiPush.APP_ID,alias, clientId) ;
	}
	
	public static void unBindAlias(String alias){
		IGtPush gtPush = PushApps.getUserPush();
		gtPush.unBindAliasAll(GetuiPush.APP_ID,alias) ;
	}
	
	public static String getCIDByAlias(String alias){
		IGtPush gtPush = PushApps.getUserPush();
		IAliasResult queryClient = gtPush.queryClientId(GetuiPush.APP_ID, alias);
	    if(queryClient.getClientIdList() !=null && !queryClient.getClientIdList().isEmpty()){
	    	return queryClient.getClientIdList().get(0);
	    }else{
	    	return "";
	    }
	}
	public static void bindAlias(String clientId,String alias,EnumApps app){
		IGtPush gtPush = app.getIGtPush();
		gtPush.bindAlias(GetuiPush.APP_ID,alias, clientId) ;
	}
	
	public static void unBindAlias(String alias,EnumApps app){
		IGtPush gtPush = app.getIGtPush();
		gtPush.unBindAliasAll(GetuiPush.APP_ID,alias) ;
	}
	
	public static String getCIDByAlias(String alias,EnumApps app){
		IGtPush gtPush = app.getIGtPush();
		IAliasResult queryClient = gtPush.queryClientId(GetuiPush.APP_ID, alias);
		if(queryClient.getClientIdList() !=null && !queryClient.getClientIdList().isEmpty()){
			return queryClient.getClientIdList().get(0);
		}else{
			return "";
		}
	}
	
	public static void main(String[] args){
		PushUtils.getCIDByAlias("18768151122", EnumApps.SHOP_APP);
		PushUtils.unBindAlias("18768151122", EnumApps.SHOP_APP);
	}
}

package com.tenzhao.common.push.getui;

import com.tenzhao.common.push.EnumPushMode;

public class PushApps {
	
   private static MinePush userPush = null ;
   private static MinePush shopPush = null ;
   private static MinePush kktPush = null ;
	
   public static MinePush getUserPush() {
	  return getUserPush(null);
   }
   public static MinePush getShopPush() {
	   return getShopPush(null);
   }
	public static MinePush getUserPush(Short mediaType) {
		if(userPush == null){
			synchronized(GetuiPush.class){
				userPush = new MinePush(GetuiInfo.APP_ID,GetuiInfo.APP_KEY,GetuiInfo.APP_SECRET, GetuiInfo.MASTER_SECRET,getPushMode(mediaType));
			}
		}
		return userPush;
	}
	
	public static MinePush getShopPush(Short mediaType) {
		if(shopPush == null){
			synchronized(GetuiPush.class){
				shopPush = new MinePush(GetuiInfo.SHOP_APP_ID,GetuiInfo.SHOP_APP_KEY,GetuiInfo.SHOP_APP_SECRET, GetuiInfo.SHOP_MASTER_SECRET,getPushMode(mediaType));
			}
		}
		return shopPush;
	}
	public static MinePush getKktPush(Short mediaType) {
		if(kktPush == null){
			synchronized(GetuiPush.class){
				kktPush = new MinePush(GetuiInfo.KKT_APP_ID,GetuiInfo.KKT_APP_KEY,GetuiInfo.KKT_APP_SECRET, GetuiInfo.KKT_MASTER_SECRET,getPushMode(mediaType));
			}
		}
		return kktPush;
	}
	
	private static EnumPushMode getPushMode(Short mediaType){
		return EnumPushMode.TRANSMISSION ;
	}
	
	static{
		getUserPush(null);
		getShopPush(null);
		getKktPush(null);
	}
}

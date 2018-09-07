package com.tenzhao.common.push;

import com.tenzhao.common.push.getui.MinePush;
import com.tenzhao.common.push.getui.PushApps;

public enum EnumApps {
	USER_APP,SHOP_APP,KKT_APP;
	public MinePush getIGtPush(Short mediaType){
		MinePush push = null;
		if(this == USER_APP){
			push = PushApps.getUserPush(mediaType);
		}else if(this == SHOP_APP){
			push = PushApps.getShopPush(mediaType);
		}else if(this == KKT_APP){
			push = PushApps.getKktPush(mediaType);
		}
		return push ;
	}
	public MinePush getIGtPush(){
		return getIGtPush(null) ;
	}
}

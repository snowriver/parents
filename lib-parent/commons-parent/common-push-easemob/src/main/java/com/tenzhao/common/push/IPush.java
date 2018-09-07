package com.tenzhao.common.push;

import java.util.List;

import com.gexin.rp.sdk.base.impl.Target;
import com.tenzhao.common.push.getui.MinePush;

public interface IPush {
	/**
	 * 
	 * @param msg       消息体内容
	 * @param msgdigest 离线消息摘要
	 * @param receivers 接收人
	 * @param clazz
	 * @return
	 */
	public <T> T push(PushModel pushModel,String[] receivers,Class<T> clazz);
	public void push(PushModel pushModel,List<? extends Target> androidUsers,List<? extends Target> iosUsers,MinePush push);
	public void push(PushModel pushModel,List<? extends Target> androidUsers,List<? extends Target> iosUsers,EnumApps app);
	public final static Integer maxPush = 20 ;  
}

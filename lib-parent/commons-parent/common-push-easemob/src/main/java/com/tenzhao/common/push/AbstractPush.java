package com.tenzhao.common.push;

import java.util.List;

import com.gexin.rp.sdk.base.impl.Target;
import com.tenzhao.common.push.getui.MinePush;

public abstract class AbstractPush implements IPush {

	@Override
	public <T> T push(PushModel pushModel, String[] receivers, Class<T> clazz) {
		return null;
	}
	
	@Override
	public void push(PushModel pushModel,List<? extends Target> androidUsers,List<? extends Target> iosUsers,MinePush push){}
	@Override
	public void push(PushModel pushModel,List<? extends Target> androidUsers,List<? extends Target> iosUsers,EnumApps app){}
}

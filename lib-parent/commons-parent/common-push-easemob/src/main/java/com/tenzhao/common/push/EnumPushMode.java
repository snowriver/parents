package com.tenzhao.common.push;
/***
 * 推送方式选择：链接推送、弹出框推送、静默推送
 * @author chenxj
 */
public enum EnumPushMode {
	/** 在通知栏显示一条含图标、 标题等的通知， 用户点击可打开您指定的网页。*/
	LINK,
	/** 消息以弹框的形式展现， 点击弹框内容可启动下载任务。*/
	NOTYPOPLOAD,
	/**透传消息是指消息传递到客户端只有消息内容， 展现形式由客户端自行定义。 客户端可自定义通知的展现形式， 也可自定义通知到达之后的动作， 或者不做任何
                  展现。 iOS推送也使用该模板。*/
	TRANSMISSION
	;
	
}

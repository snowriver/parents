package com.getui.java;

import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;

public interface IGetuiPush {
	/**
	 * 
	 * @param contentId 消息
	 * @param clientIdList 客户端ID
	 */
	public IPushResult pushMessageToListWithCID(String contentId, List<String> clientIdList);

}

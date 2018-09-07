package com.getui.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.PushResult;
import com.gexin.rp.sdk.http.GtPush;
import com.gexin.rp.sdk.http.utils.GTConfig;
public class GetuiPushTest extends GtPush implements IGetuiPush {
	private String appKey;
	  private String host;
	  private String masterSecret;
	  private Boolean useSSL;
	public GetuiPushTest(String domainUrl, String appKey, String masterSecret, Boolean useSSL) {
		super(domainUrl, appKey, masterSecret, useSSL);
		this.appKey = appKey;
	    this.masterSecret = masterSecret;
	    this.useSSL = Boolean.valueOf(useSSL == null ? false : useSSL.booleanValue());
	}
	
	public IPushResult pushMessageToListWithCID(String contentId, List<String> clientIdList){
		Map<String,Object> postData = new HashMap<String,Object>();
	    PushResult pushResult = new PushResult();
	    postData.put("action", "pushMessageToListAction");
	    postData.put("appkey", this.appKey);
	    postData.put("contentId", contentId);
	    boolean needDetails = GTConfig.isPushListNeedDetails();
	    postData.put("needDetails", Boolean.valueOf(needDetails));
	    boolean async = GTConfig.isPushListAsync();
	    postData.put("async", Boolean.valueOf(async));
	    boolean needAliasDetails = GTConfig.isPushListNeedAliasDetails();
	    postData.put("needAliasDetails", Boolean.valueOf(needAliasDetails));
	    int limit;
	    if ((async) && (!needDetails))
	      limit = GTConfig.getAsyncListLimit();
	    else {
	      limit = GTConfig.getSyncListLimit();
	    }
	    if (clientIdList.size() > limit) {
	      Map<String,Object> response = new HashMap<String,Object>(1);
	      response.put("result", "target size:" + clientIdList.size() + " beyond the limit:" + limit);
	      pushResult.setResponse(response);
	      return pushResult;
	    }

	    List<String> aliasList = new ArrayList<String>(0);
	    String appId = null;
	    postData.put("appId", appId);
	    postData.put("clientIdList", clientIdList);
	    postData.put("aliasList", aliasList);

	    postData.put("type", Integer.valueOf(2));
	    Map<String,Object> response = httpPostJSON(this.host, postData, true);

	    pushResult.setResponse(response);
	    return pushResult;
	}
}

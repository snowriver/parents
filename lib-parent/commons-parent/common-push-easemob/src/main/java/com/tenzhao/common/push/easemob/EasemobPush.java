package com.tenzhao.common.push.easemob;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.easemob.server.example.api.SendMessageAPI;
import com.easemob.server.example.comm.ClientContext;
import com.easemob.server.example.comm.EasemobRestAPIFactory;
import com.easemob.server.example.comm.body.TextMessageBody;
import com.easemob.server.example.comm.wrapper.ResponseWrapper;
import com.tenzhao.common.push.AbstractPush;
import com.tenzhao.common.push.PushException;
import com.tenzhao.common.push.PushModel;

public class EasemobPush extends AbstractPush{
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EasemobPush.class);
	
	@SuppressWarnings("unchecked")
	//@Override
	public <T> T push(PushModel pushModel, String[] receivers,Class<T> clazz) {
		String msg = JSONObject.toJSONString(pushModel);
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_CLASS).getAPIFactory();
		final SendMessageAPI sendMessage = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
		try{
			final Map<String,String> extMap = new HashMap<String,String>();
			extMap.put("pushType", pushModel.getPushType().toString());
			extMap.put("pushCont",msg);
			extMap.put("pushLogId",pushModel.getId());
			
			String remindMsg = StringUtils.EMPTY ;
			if(pushModel.getPushType() == 0 ){
				remindMsg = "您有一条商家消息";
			}else if(1 == pushModel.getPushType()){
				remindMsg = "您有一条系统消息";
			}else{
				remindMsg = "您有一条代理商消息";
			}
			extMap.put("em_apns_ext",remindMsg);
			final String remindMsg_ = remindMsg;
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100); 
			int totalPages = 0 ;
			if ((receivers.length % maxPush) == 0) { 
				totalPages = receivers.length / maxPush; 
			} else { 
				totalPages = receivers.length / maxPush + 1; 
			} 
			
			for(int i=0;i<totalPages;i++){
				String[] tmp = null ;
				if((i+1) == totalPages){
					tmp= Arrays.copyOfRange(receivers, maxPush * i, receivers.length);
				}else{
					tmp= Arrays.copyOfRange(receivers, maxPush * i, maxPush*(i+1));
				}
				final String[] tempReceivers = tmp ;
				fixedThreadPool.execute(new Runnable() {
					@Override
					public void run() {  
						TextMessageBody txtMessageBody = new TextMessageBody(
								"users", 
								tempReceivers, 
								"admin", 
								extMap, 
								remindMsg_);
						ResponseWrapper responseWrapper =(ResponseWrapper) sendMessage.sendMessage(txtMessageBody);
						if(responseWrapper.getResponseStatus() != Response.Status.OK.getStatusCode()){
							LOGGER.error("推送失败："+responseWrapper.getResponseStatus()+responseWrapper.getResponseBody().toString());
							throw new PushException();
						}
					} 
				}); 
			}
		}catch(RuntimeException e){
			throw new PushException();
		}
		return (T)"";
	}
}

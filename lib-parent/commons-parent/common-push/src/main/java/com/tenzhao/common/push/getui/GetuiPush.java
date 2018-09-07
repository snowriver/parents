package com.tenzhao.common.push.getui;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.tenzhao.common.push.AbstractPush;
import com.tenzhao.common.push.AppConfig;
import com.tenzhao.common.push.PushModel;

/**
 *  
 * <pre>
 * Spring 依赖注入示例<br>
 *{@code
   <bean id="hpyxshPush" class="com.tenzhao.common.push.getui.GetuiPush" lazy-init="true">
		<property name="appConfig">
			<bean class="com.tenzhao.common.push.AppConfig" >
				<property name="appID" value="4w20jkrbONAWlpI9wlt4S6" />
				<property name="appSecret" value="uCg1MNUpVlAbnVSjQE0YI2" />
				<property name="appKey" value="O24DCRZ7YW7qNPQvLg4CG2" />
				<property name="masterSecret" value="CtpcXuHLQ77lwUS4FZegn7" /> 
				<property name="appName" value="环朋优选生活" /> 
			</bean>
		</property>
		<property name="iGtPush">
			<bean class="com.gexin.rp.sdk.http.IGtPush" >
			<constructor-arg  type="java.lang.String" name="appKey" value="O24DCRZ7YW7qNPQvLg4CG2"></constructor-arg>
		 	<constructor-arg  type="java.lang.String" name="masterSecret" value="CtpcXuHLQ77lwUS4FZegn7"></constructor-arg>
		 	<constructor-arg  type="boolean" name="useSSL" value="true"></constructor-arg>
			</bean>
		</property>
	</bean>
 }
 </pre>
 * @author chenxj
 */
public class GetuiPush extends AbstractPush{
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GetuiPush.class);
	private AppConfig appConfig ;
	private IGtPush iGtPush ;
	
	@Override
	public void push(PushModel pushModel,List<? extends Target> androidUsers,List<? extends Target> iosUsers) {
		String transmissionMsg = JSONObject.toJSONString(pushModel);
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		boolean flag = false ;
		if(!androidUsers.isEmpty()){
			androidPush(pushModel,transmissionMsg,androidUsers);
			flag = true;
		}
		if(!iosUsers.isEmpty()){
			iosPush(pushModel,transmissionMsg,iosUsers);
			flag = true;
		}
		if(flag){
			LOGGER.info("系统推送内容：{}",transmissionMsg);
		}
	}
	
	public  void androidPush(final PushModel pushModel,final String transmissionMsg,List<? extends Target> receivers){
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100); 
		for(int i=0;i<receivers.size();){
			final TmpUsers tmpUsers = new TmpUsers() ;
			if((i+75)>receivers.size()){
				tmpUsers.setTmpUsers(receivers.subList(i,receivers.size()));
			    i+=75;
			    _androidPush(transmissionMsg, fixedThreadPool, tmpUsers);
			}
			else if(i%75 == 0){
				tmpUsers.setTmpUsers(receivers.subList(i,i+=75));
				_androidPush(transmissionMsg, fixedThreadPool, tmpUsers);
			}
		}
		fixedThreadPool.shutdown();
    }

	private void _androidPush(final String transmissionMsg, ExecutorService fixedThreadPool, final TmpUsers tmpUsers) {
		fixedThreadPool.execute(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {  
				// 通知透传模板
				try {
					TransmissionTemplate template = getBaseTransmissionTemplate(transmissionMsg);
					ListMessage message = new ListMessage();
					message.setData(template);
					message.setOffline(true);
					message.setOfflineExpireTime(24 * 1000 * 3600);
					List<Target> lstTarget = (List<Target>) tmpUsers.getTmpUsers() ;
					String contentId = iGtPush.getContentId(message) ;
					IPushResult ret = iGtPush.pushMessageToList(contentId, lstTarget);
					LOGGER.info("个推响应,contentId:{}-android：{}",contentId,ret.getResponse().toString());
				} catch (Exception e) {
					LOGGER.error("个推响应-android：{}",e);
					e.printStackTrace();
				}
			} 
		});
	}
    public NotificationTemplate notificationTemplateDemo(PushModel pushModel,String transmissionMsg, AppConfig push) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(push.getAppID());
        template.setAppkey(push.getAppKey());
        Style0 style = new Style0();
      //  style.setTitle(pushModel.getTitle());              // 设置通知栏标题
        style.setText(pushModel.getTitle()); // 设置通知栏内容
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        template.setTransmissionType(2); // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionContent(transmissionMsg);
        return template;
    }
	  
    public  void iosPush(final PushModel pushModel,final String transmissionMsg,List<? extends Target> iosUsers){
    	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100); 
		for(int i=0;i<iosUsers.size();){
			final TmpUsers tmpUsers = new TmpUsers() ;
			if((i+75)>iosUsers.size()){
				tmpUsers.setTmpUsers(iosUsers.subList(i,iosUsers.size()));
				i+=75 ;
				_iosPush(pushModel, transmissionMsg, fixedThreadPool, tmpUsers); 
			}
			else if(i%75 == 0){
				tmpUsers.setTmpUsers(iosUsers.subList(i,i+=75));
				_iosPush(pushModel, transmissionMsg, fixedThreadPool, tmpUsers); 
			}
		}
		fixedThreadPool.shutdown();
    }

	private void _iosPush(final PushModel pushModel, final String transmissionMsg, ExecutorService fixedThreadPool,
			final TmpUsers tmpUsers) {
		fixedThreadPool.execute(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				TransmissionTemplate apnTemplate = getTransmissionTemplate(pushModel,transmissionMsg);
		    	ListMessage message = new ListMessage();
		    	message.setData(apnTemplate);
		        message.setOffline(true);  // 设置消息离线,此处不设置IOS关闭无法收到APNS透传
		        message.setOfflineExpireTime(24 * 1000 * 3600);  // 离线有效时间，单位为毫秒，可选
		    	String contentId = iGtPush.getContentId( message);
		    	List<Target> lstTarget = (List<Target>) tmpUsers.getTmpUsers() ;
		    	IPushResult ret = iGtPush.pushMessageToList(contentId, lstTarget);
		    	LOGGER.info("个推响应,contentId:{},-ios：{}",contentId,ret.getResponse().toString());
			} 
		});
	}
    public TransmissionTemplate getTransmissionTemplate(PushModel pushModel,String transmissionMsg) {
    	TransmissionTemplate template = getBaseTransmissionTemplate(transmissionMsg);
    	APNPayload payload = new APNPayload();
    //	payload.addCustomMsg("href", pushModel.getUrl()); 自定义apns的消息体内容
    	//payload.setContentAvailable(1);
    	payload.setSound("default");
    	payload.setAlertMsg(getDictionaryAlertMsg(pushModel));
    	template.setAPNInfo(payload);
    	return template;
    }
    
    public TransmissionTemplate getBaseTransmissionTemplate(String transmissionMsg){
    	TransmissionTemplate template = new TransmissionTemplate();
    	template.setAppId(appConfig.getAppID());
    	template.setAppkey(appConfig.getAppKey());
    	template.setTransmissionContent(transmissionMsg);
    	template.setTransmissionType(2);
    	return template ;
    }
    private  APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(PushModel pushModel){
    	APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
    	alertMsg.setBody(pushModel.getTitle());
    	
    	// IOS8.2以上版本支持
    	//alertMsg.setTitle(pushModel.getTitle());
    	return alertMsg;
    }
	
	public static void main(String[] args){
		
	}
	
	public LinkTemplate linkTemplateDemo(String appId, String appKey) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃， 震动， 或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		// 设置打开的网址地址
		template.setUrl("http://www.getui.com");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}
	
	public AppConfig getAppConfig() {
		return appConfig;
	}
	public void setAppConfig(AppConfig push) {
		this.appConfig = push;
	}
	
	public IGtPush getIGtPush() {
		return iGtPush;
	}
	public void setIGtPush(IGtPush iGtPush) {
		this.iGtPush = iGtPush;
	}

	@Override
	public void bindAlias(String clientId, String alias) {
		iGtPush.bindAlias(appConfig.getAppID(), alias, clientId);
	}

	@Override
	public void unBindAlias(String alias) {
		iGtPush.unBindAliasAll(appConfig.getAppID(), alias);
	}

	@Override
	public String getCIDByAlias(String alias) {
		IAliasResult queryClient = iGtPush.queryClientId(appConfig.getAppID(), alias);
		if(queryClient.getClientIdList() !=null && !queryClient.getClientIdList().isEmpty()){
			return queryClient.getClientIdList().get(0);
		}else{
			return "";
		}
	}
}

class TmpUsers{
	private List<? extends Target> tmpUsers ;

	public List<? extends Target> getTmpUsers() {
		return tmpUsers;
	}

	public void setTmpUsers(List<? extends Target> tmpUsers) {
		this.tmpUsers = tmpUsers;
	}

}


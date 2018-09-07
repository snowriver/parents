package com.tenzhao.commons.wx.pay.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.tenpay.util.WXUtil;
import com.tenzhao.commons.wx.exception.WxPayException;
import com.tenzhao.commons.wx.exception.WxRefundException;
import com.tenzhao.commons.wx.model.WxPayRequestParam;
import com.tenzhao.commons.wx.model.WxServiceNO;
import com.tenzhao.commons.wx.model.WxUnifiedorderResponse;
import com.tenzhao.commons.wx.pay.IAppPayService;
import com.tenzhao.commons.wx.pay.utils.WXSignUtils;
import com.tenzhao.commons.wx.pay.utils.XmlUtils;
import com.tenzhao.commons.wx.utils.JerseyUtils;
import com.thoughtworks.xstream.XStream;

import me.chanjar.weixin.common.util.StringUtils;

public class AppPayServiceImpl implements IAppPayService {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AppPayServiceImpl.class);
	protected WxServiceNO serviceNO ;
	
	public void setServiceNO(WxServiceNO serviceNO) {
		this.serviceNO = serviceNO;
	}

	/**
	 * 
	 */
	@Override
	public WxUnifiedorderResponse payUnifiedorder(WxPayRequestParam requestParam)throws WxPayException {
		String url =  "https://api.mch.weixin.qq.com/pay/unifiedorder";
		requestParam.setAppid(serviceNO.getAppid());
		requestParam.setMch_id(serviceNO.getMch_id());
		SortedMap<String,String> form = new TreeMap<String,String>();
		form.put("appid", requestParam.getAppid());
		form.put("mch_id",requestParam.getMch_id()); //微信支付分配的商户号
		form.put("device_info",requestParam.getDevice_info());
		form.put("nonce_str", requestParam.getNonce_str());
		form.put("body", requestParam.getBody());
		form.put("sign_type",requestParam.getSign_type());
		form.put("attach", requestParam.getAttach());
		form.put("out_trade_no", requestParam.getOut_trade_no()); //商户系统内部的订单号
		form.put("fee_type",requestParam.getFee_type());
		form.put("total_fee",requestParam.getTotal_fee()+""); //单位分
		form.put("spbill_create_ip",requestParam.getSpbill_create_ip()); 
		form.put("time_start", requestParam.getTime_start());
		form.put("time_expire", requestParam.getTime_expire());
		form.put("goods_tag", requestParam.getGoods_tag());
		form.put("notify_url", requestParam.getNotify_url());
		form.put("trade_type", requestParam.getTrade_type());
		form.put("limit_pay", requestParam.getLimit_pay());
		form.put("sign", WXSignUtils.createSign(form));//服务端预支付签名
		String reqParams =  XmlUtils.builtXmlArgs(form) ;
		LOGGER.debug("微信支付获取订单参数："+reqParams);
		ClientResponse response = JerseyUtils.post(url,reqParams);
		WxUnifiedorderResponse wxUnifiedorderResponse = null ;
		String xml ="";
		try{
			xml= response.getEntity(String.class);
			XStream xstream = new XStream() ;
			xstream.alias("xml", WxUnifiedorderResponse.class);
			wxUnifiedorderResponse = (WxUnifiedorderResponse) xstream.fromXML(xml);

			if(wxUnifiedorderResponse.getReturn_code().equals("SUCCESS") && StringUtils.isNotEmpty(wxUnifiedorderResponse.getPrepay_id())){//APP调起支付时的签名
				SortedMap<String,String> form_2 = new TreeMap<String,String>();
				form_2.put("appid", requestParam.getAppid());
				//	form_2.put("appkey", this.serviceNO.getApiKey());
				form_2.put("partnerid",requestParam.getMch_id()); //微信支付分配的商户号
				form_2.put("noncestr", wxUnifiedorderResponse.getNonce_str());
				form_2.put("prepayid", wxUnifiedorderResponse.getPrepay_id());  
				form_2.put("package","Sign=WXPay");
				form_2.put("timestamp", WXUtil.getTimeStamp()); 
				wxUnifiedorderResponse.setSign(WXSignUtils.createSign(form_2)); 
				wxUnifiedorderResponse.setPackage_str(form_2.get("package").toString());
				wxUnifiedorderResponse.setTimestamp(Integer.valueOf(form_2.get("timestamp").toString()));
			}else{
				throw new WxPayException(String.format("Response Status:%d,return_code:%s,return_msg:%s", response.getStatus(),wxUnifiedorderResponse.getReturn_code(),wxUnifiedorderResponse.getReturn_msg()));
			}
		}catch(RuntimeException e){
			throw new WxPayException(e.getMessage());
		}
		return wxUnifiedorderResponse;
	}
	
	 public CloseableHttpClient loadCert() throws IOException   {
		 FileInputStream instream = null;
		 try { 
			 //指定读取证书格式为PKCS12  
			 KeyStore keyStore = KeyStore.getInstance("PKCS12"); 
			 //读取本机存放的PKCS12证书文件  
			 instream = new FileInputStream(new File(System.getProperty("catalina.home")+File.separator+"conf"+File.separator+"apiclient_cert.p12")); 
			 //指定PKCS12的密码(默认商户ID)  
			 keyStore.load(instream, serviceNO.getMch_id().toCharArray()); 
			 SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, serviceNO.getMch_id().toCharArray()).build();
			 //指定TLS版本 
			 SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext,new String[] { "TLSv1" }
			 ,null
			 ,  SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); 
			 //设置httpclient的SSLSocketFactory  
			 CloseableHttpClient httpclient = HttpClients.custom() .setSSLSocketFactory(sslsf) .build(); 
			 return httpclient;
		 }catch(KeyStoreException e){
			 e.printStackTrace();
		 }catch(Exception e){
			 e.printStackTrace();
		 } finally {  
			 instream.close();
		 }
		return null;  

	 }

	@Override
	public WxServiceNO getServiceNO() {
		return serviceNO;
	}

	@Override
	public String refundFee(String out_trade_no, Integer total_fee, Integer refund_fee)throws WxRefundException {
		SortedMap<String,String> form = new TreeMap<String,String>();
		form.put("appid", serviceNO.getAppid());
		form.put("mch_id",serviceNO.getMch_id()); //微信支付分配的商户号
		form.put("nonce_str", WXUtil.getNonceStr());
		form.put("out_trade_no",  out_trade_no);
		form.put("out_refund_no",out_trade_no);
		form.put("total_fee", total_fee+"");
		form.put("refund_fee", refund_fee+"");
		form.put("op_user_id", serviceNO.getMch_id());
		form.put("sign", WXSignUtils.createSign(form));//服务端预支付签名
    	CloseableHttpClient httpclient= null ;
    	try {
    		httpclient = loadCert();
		} catch (IOException e) {
			throw new WxPayException("微信签名证书加载错误,IO异常") ;
		}
    	HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");  
		String xml = XmlUtils.builtXmlArgs(form);  
		String returnCode = "";
		try {  
			StringEntity se = new StringEntity(xml);  
			httppost.setEntity(se);  
//			System.out.println("executing request" + httppost.getRequestLine());  
			CloseableHttpResponse responseEntry = httpclient.execute(httppost);  
			try {  
				HttpEntity entity = responseEntry.getEntity();  
//				System.out.println(responseEntry.getStatusLine()); 
				if (entity != null) {  
					SAXReader saxReader = new SAXReader();  
					Document document = saxReader.read(entity.getContent());  
					Element rootElt = document.getRootElement();  
//					System.out.println("根节点：" + rootElt.getName());  
//					System.out.println("==="+rootElt.elementText("return_code"));  
//					System.out.println("==="+rootElt.elementText("result_code"));  
//					System.out.println("==="+rootElt.elementText("transaction_id"));  
//					System.out.println("==="+rootElt.elementText("out_trade_no"));  
//					System.out.println("==="+rootElt.elementText("out_refund_no"));  
//					System.out.println("==="+rootElt.elementText("refund_id"));  
//					System.out.println("==="+rootElt.elementText("refund_channel"));  
//					System.out.println("==="+rootElt.elementText("refund_fee"));  
//					System.out.println("==="+rootElt.elementText("coupon_refund_fee"));  
//					System.out.println("===return_msg:"+rootElt.elementText("return_msg"));  
					returnCode = rootElt.elementText("return_code");  
					if(returnCode.equals(WXUtil.NOTIFY_SUCCESS)){
						if(StringUtils.isNotEmpty(rootElt.elementText("err_code"))){
							returnCode = WXUtil.NOTIFY_FAIL ;
							throw new WxRefundException(rootElt.elementText("err_code_des")+"["+rootElt.elementText("err_code")+"]");
						}
					}else{
						throw new WxRefundException(rootElt.elementText("return_msg")+"[FAIL]");
					}  
				}  
				EntityUtils.consume(entity);  
			}catch(Exception e){
				throw new WxRefundException(e.getMessage());
			}  
			finally {  
				responseEntry.close();  
			}  
		}catch(Exception e){
			throw new WxRefundException(e.getMessage());
		}  
		finally {  
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new WxPayException("IO异常");
			}  
		} 
		return returnCode ;
	}
	
}

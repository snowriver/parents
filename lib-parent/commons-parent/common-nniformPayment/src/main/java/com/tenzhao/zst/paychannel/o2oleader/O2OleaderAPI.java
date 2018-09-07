package com.tenzhao.zst.paychannel.o2oleader;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.tenzhao.zst.paychannel.adapter.returnparam.ICreatepayparam;
import com.tenzhao.zst.paychannel.adapter.returnparam.IQueryPayStatusResult;
import com.tenzhao.zst.paychannel.adapter.returnparam.IQueryparam;
import com.tenzhao.zst.paychannel.adapter.returnparam.IRefundparam;
import com.tenzhao.zst.paychannel.o2oleader.constants.O2OleaderConstants;
import com.tenzhao.zst.paychannel.o2oleader.constants.O2OleaderConstants.Param_dynamic_type;
import com.tenzhao.zst.paychannel.o2oleader.constants.O2OleaderConstants.Param_service;
import com.tenzhao.zst.paychannel.o2oleader.constants.O2OleaderConstants.Param_trans_type;
import com.tenzhao.zst.paychannel.o2oleader.returnparam.Createpayparam;
import com.tenzhao.zst.paychannel.o2oleader.returnparam.QueryPayStatusResult;
import com.tenzhao.zst.paychannel.o2oleader.returnparam.Refundparam;

/**
 * 移领API  @linkplain http://api.020leader.com/index.php?s=/home/item/show/item_id/1
 * @author chenxj
 */
public class O2OleaderAPI {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(O2OleaderAPI.class);
	/**
	 * 通过移领去支付
	 * @param request
	 * @param response
	 * @return
	 * @see {@linkplain http://api.020leader.com/index.php?s=/home/item/show/item_id/1}  com.tenzhao.zst.controller.CashierController - 付款失败：{"result_code":"FAIL","result_msg":"受理机构必须传入sub_mch_id"}
	 */
	public static ICreatepayparam requestYLBarCodePay(String payStoreNum,String shopStoreId,String userName,float shouldPayMoney ,String payBarCode )throws Exception{
		/**  移领的支付类型 1微信 2支付宝,与系统定义的刚好相反,所以此处交换过来*/
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource(O2OleaderConstants.URL);
		wr.accept(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		MultivaluedMap<String,String> form = new MultivaluedMapImpl();
		form.add("service", Param_service.CREATEANDPAY.getValue());
		form.add("trans_type",Param_trans_type.BARCODE_OF_PAY.getValue());
//		form.add("dynamic_type",payWay+StringUtils.EMPTY);
//		form.add("pay_type",payWay+StringUtils.EMPTY);
		form.add("total_fee",shouldPayMoney+"");
		form.add("dynamic_id", payBarCode);
		form.add("merchant_num", payStoreNum);
		form.add("terminal_unique_no", shopStoreId);
		form.add("cashier_num", userName);
		form.add("client_type", "WEB");
		ClientResponse response = null ;
		response = wr.post(ClientResponse.class, form);
		//	if(response.getStatus() != Response.Status.OK.getStatusCode()){
		return com.alibaba.fastjson.JSONObject.parseObject(response.getEntity(String.class), Createpayparam.class);
	}
	
	/**
	 * 通过移领去退款
	 * @param request
	 * @param response
	 * @return
	 * @see {@linkplain http://api.020leader.com/index.php?s=/home/item/show/item_id/1}  com.tenzhao.zst.controller.CashierController - 付款失败：{"result_code":"FAIL","result_msg":"受理机构必须传入sub_mch_id"}
	 */
	public static  IRefundparam requestYLrefund(String payStoreNum,String shopStoreId,String traceNum,float actualPayMoney)throws Exception{
		/**  移领的支付类型 1微信 2支付宝,与系统定义的刚好相反,所以此处交换过来*/
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource(O2OleaderConstants.URL);
		wr.accept(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		MultivaluedMap<String,String> form = new MultivaluedMapImpl();
		form.add("service", Param_service.REFUND.getValue());
		form.add("trans_type",Param_trans_type.REFUND.getValue());
		
		form.add("merchant_num", payStoreNum);
		form.add("trace_num",traceNum);
		form.add("refund_amount", actualPayMoney+"");
		form.add("terminal_unique_no", shopStoreId);
		ClientResponse response = null ;
		response = wr.post(ClientResponse.class, form);
		String json = response.getEntity(String.class) ;
		return com.alibaba.fastjson.JSONObject.parseObject(json, Refundparam.class);
	}
	
	public static  IQueryPayStatusResult queryPaystatus(String payStoreNum,String shopStoreId,String traceNum){
		
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource(O2OleaderConstants.URL);
		wr.accept(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		MultivaluedMap<String,String> form = new MultivaluedMapImpl();
		form.add("service", Param_service.QUERY.getValue());
//		form.add("trans_type",Param_trans_type..getValue());
		
		form.add("merchant_num", payStoreNum);
		form.add("trace_num",traceNum);
		form.add("terminal_unique_no", shopStoreId);
		ClientResponse response = null ;
		response = wr.post(ClientResponse.class, form);
		String _result = response.getEntity(String.class);
		LOGGER.info("移领订单状态查询参数:{},结果:{}",JSONObject.toJSONString(form),_result);
		IQueryPayStatusResult json = JSONObject.parseObject(_result, QueryPayStatusResult.class);
		return json;
	}
	/**
	 * 
	 * @param staffIdentity
	 * @param offlineorderVO
	 */
	@Deprecated
	public static  void scanCodePay(String payStoreNum,String shopStoreId,String cashierNum){
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource(O2OleaderConstants.URL);
		wr.accept(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		MultivaluedMap<String,String> form = new MultivaluedMapImpl();
		form.add("service", Param_service.SCAN_CODE_PAY.getValue());
		
		form.add("total_fee", "0.01");
		form.add("dynamic_type",Param_dynamic_type.ALIPAY.getValue()+"");
		form.add("merchant_num", payStoreNum);
		form.add("terminal_unique_no", shopStoreId);
		form.add("cashier_num",cashierNum);
		form.add("client_type", "WEB");
		ClientResponse response = null ;
		response = wr.post(ClientResponse.class, form);
		String json = response.getEntity(String.class) ;
		System.out.println(json);
	}
	
	
	
	/**
	 * 获取统一自助支付模板信息
	 */
	public static String getSelfServicePayTemp(){
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource("http://alipay.020leader.com/index.php");
		wr.accept(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		MultivaluedMap<String,String> form = new MultivaluedMapImpl();
		form.add("g", "AppApi");
//		form.add("trans_type",Param_trans_type..getValue());
		
		form.add("m","Payfree");
		form.add("a","getfree");
		form.add("token", "uzwgdd1478505047");
		ClientResponse response = null ;
		response = wr.post(ClientResponse.class, form);
		String result = "";
		result = response.getEntity(String.class);
		return result ;
	}
	
	/**
	 * 获取商户门店收款二维码信息<br>
	 * https://alipay.020leader.com/index.php?g=Wap&m=CashierPayfreeApi&a=codePay&merchant_num=000000000013378&money=0.01
	 * @return
	 */
	public static String getCollectionCode(String payStoreNum) throws Exception{
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource(O2OleaderConstants.QR_IMG_SERVICE_URL+"/index.php?g=Wap&m=CashierPayfreeApi&a=codePay&merchant_num="+payStoreNum);
		wr.accept(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		ClientResponse response = null ;
		response = wr.get(ClientResponse.class);
		String result = "";
		result = response.getEntity(String.class);
		LOGGER.info("get Collection code result:{}",result);
		JSONObject json = JSONObject.parseObject(result) ;
		if(!"success".equals(json.getString("result_code"))){
			throw new O2OleaderException(json.getString("result_msg"));
		}
		return JSONObject.parseObject(json.getString("result_data").replaceAll("\\/", "/")).getString("img");
	}
	
	
	
}

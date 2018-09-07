package com.tenzhao.commons.wx.pay;

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;

import com.tenzhao.commons.wx.exception.WxPayException;
import com.tenzhao.commons.wx.exception.WxRefundException;
import com.tenzhao.commons.wx.model.WxPayRequestParam;
import com.tenzhao.commons.wx.model.WxServiceNO;
import com.tenzhao.commons.wx.model.WxUnifiedorderResponse;

public interface IAppPayService {
	/***
	 * app支付 统一下单接口
	 * @return
	 */
	public WxUnifiedorderResponse payUnifiedorder(WxPayRequestParam requestParam) throws WxPayException;
	
	public CloseableHttpClient loadCert() throws IOException ;
	
	public WxServiceNO getServiceNO() ;
	/**
	 * 退款
	 * @param out_trade_no
	 * @param total_fee
	 * @param refund_fee
	 * @return 微信退款的返回状态  SUCCESS/FAIL
	 */
	public String refundFee(String out_trade_no, Integer total_fee, Integer refund_fee) throws WxRefundException;
	
}

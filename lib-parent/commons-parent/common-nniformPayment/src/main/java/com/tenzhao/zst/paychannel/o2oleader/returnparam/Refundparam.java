package com.tenzhao.zst.paychannel.o2oleader.returnparam;

import java.io.Serializable;

import com.tenzhao.zst.paychannel.adapter.returnparam.IRefundparam;

public class Refundparam extends Baseparam implements IRefundparam,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dynamic_type	;
	private String trans_type	;
	private String merchant_name	;
	private String merchant_num	;
	private String terminal_num	;
	private String trans_card_num	;
	private String trace_num	;
	private String trans_time	;
	private String trans_amount	;
	private String total_fee	;
	private String refund_fee	;
	private String refund_amount    ;
	private String result_code  ;
	private String result_msg   ;
	public String getDynamic_type() {
		return dynamic_type;
	}
	public void setDynamic_type(String dynamic_type) {
		this.dynamic_type = dynamic_type;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getMerchant_num() {
		return merchant_num;
	}
	public void setMerchant_num(String merchant_num) {
		this.merchant_num = merchant_num;
	}
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	public String getTrans_card_num() {
		return trans_card_num;
	}
	public void setTrans_card_num(String trans_card_num) {
		this.trans_card_num = trans_card_num;
	}
	public String getTrace_num() {
		return trace_num;
	}
	public void setTrace_num(String trace_num) {
		this.trace_num = trace_num;
	}
	public String getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	public String getTrans_amount() {
		return trans_amount;
	}
	public void setTrans_amount(String trans_amount) {
		this.trans_amount = trans_amount;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	
}

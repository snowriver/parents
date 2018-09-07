package com.tenzhao.zst.paychannel.adapter.returnparam;

public interface ICreatepayparam extends IBaseparam{
	public String getDynamic_type();
	public String getTrans_type() ;
	public String getMerchant_name() ;
	public String getMerchant_num();
	public String getTerminal_num() ;
	public String getTrans_card_num() ;
	public String getTrace_num() ;
	public String getTrans_time() ;
	public String getTrans_amount() ;
	public String getTotal_fee();
	public String getCheck_code() ;
	public String getInput_fee();
	public String getExternal_num();
	public String getOut_trade_no() ;
	public String getTrade_no() ;
	public String getTotalAmount();
	
	
}

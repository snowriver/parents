package com.tenzhao.zst.paychannel.o2oleader.returnparam;

import com.tenzhao.zst.paychannel.adapter.returnparam.IBaseparam;

public class Baseparam implements IBaseparam {
	private String result_code ;
	private String result_msg ;
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

}

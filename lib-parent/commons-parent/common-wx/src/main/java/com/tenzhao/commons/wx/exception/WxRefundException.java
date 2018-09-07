package com.tenzhao.commons.wx.exception;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;
/**
 * 退款异常
 * @author chenxj
 */
public class WxRefundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	  private ClientResponse response;

	  public WxRefundException(){}
	  public WxRefundException(String msg){
		  super(msg);
	  }
	  public WxRefundException(ClientResponse response)
	  {
		  if(response == null){
			  response = new ClientResponse( Response.Status.INTERNAL_SERVER_ERROR, null, null,null) ;
		  }else{
			  this.response = response ;
		  }
	  }

	  public ClientResponse getResponse()
	  {
	    return this.response;
	  }
}

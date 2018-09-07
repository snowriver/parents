package com.tenzhao.commons.wx.exception;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;

public class WxPayException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	  private ClientResponse response;

	  public WxPayException(){}
	  public WxPayException(String msg){
		  super(msg);
	  }
	  public WxPayException(ClientResponse response)
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

package com.tenzhao.common.push;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;


/**
 * chenxj
 */
public class PushException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	  private ClientResponse response;

	  public PushException(){}

	  public PushException(ClientResponse response)
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

package com.tenzhao.zst.paychannel.o2oleader;

import javax.ws.rs.core.Response;

import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;

/**
 * 移领API  @linkplain http://api.020leader.com/index.php?s=/home/item/show/item_id/1
 * @author chenxj
 */
public class O2OleaderException extends Exception {
	  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(O2OleaderException.class);  
	  private static final long serialVersionUID = 1L;
		
	  public O2OleaderException(){}
	  public O2OleaderException(Exception e){
		  super(e);
	  }
	  public O2OleaderException(String e){
		  super(e);
	  }
	  private ClientResponse response;
	  public O2OleaderException(ClientResponse response)
	  {
		  if(response == null){
			  response = new ClientResponse( Response.Status.INTERNAL_SERVER_ERROR, null, null,null) ;
		  }else{
			  this.response = response ;
		  }
		  LOGGER.error("LinemgrException请求状态码{}",response.getStatus());
	  }

	  public ClientResponse getResponse()
	  {
	    return this.response;
	  }
	
}

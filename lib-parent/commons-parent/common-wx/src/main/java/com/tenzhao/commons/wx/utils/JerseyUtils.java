package com.tenzhao.commons.wx.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyUtils {
	
	public static ClientResponse post(String url,String xml){
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource wr = client.resource(url);
		return wr.post(ClientResponse.class, xml);
	}
	
}

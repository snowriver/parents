/*
 * Copyright 2014-2016 My current company name.
 */

package mine.snowriver.common.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import static mine.snowriver.common.oss.OSSClientConfig.*;
/**
 * OSSClient实例初始化 
 * @author chenxj
 */
public class OSSClientInit 
{
	/**
	 * 使用bucket自定义域名实例化OSSClient
	 * @return
	 */
	public static OSSClient getOSSClientByCname(){
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(120000);
		conf.setSupportCname(true);// 开启支持CNAME选项
		return new OSSClient(ENDPOINT_INTERNET_ALI, ACCESSKEY_ID,ACCESSKEY_SECRET,conf);
	}
}

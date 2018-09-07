/*
 * Copyright 2014-2016 My current company name.
 */

package mine.snowriver.common.oss;

/**
 * OSS常用配置项,默认的配置项见{@link ClientConfiguration}通过该类的setXX()方法可设置配置项的自定义值
 * @author chenxj
 */
public final class OSSClientConfig {
	
	public final static String HTTP_VALUE = "http://";
	
	public final static String HTTPS_VALUE = "https://";
	/** 存储空间 */
	public final static String BUCKET = "zhushangtong" ;
	/** 自定义域名  cname */
	public final static String ENDPOINT_CUSTOM = "http://static.tenzhao.com";
	/** bucket外网域名  endpoint*/
	public final static String ENDPOINT_INTERNET_ALI = "http://oss-cn-hangzhou.aliyuncs.com";
	/** bucket内网域名 */
	public final static String ENDPOINT_LAN_ALI = "http://oss-cn-hangzhou-internal.aliyuncs.com";
	/** OSS图片处理服务的域名 */
	public final static String OSS_IMG_SERVICE_DOMAIN = "http://img-cn-hangzhou.aliyuncs.com";
	/** OSS图片服务域名  CNAME */
	public final static String OSS_IMG_SERVICE_DOMAIN_CNAME = "http://pic.tenzhao.com";
	/** CDN加速的域名  ,没做CDN时暂时使用图片服务的域名 */
	public final static String OSS_IMG_SERVICE_DOMAIN_CDN = "http://zhushangtong.img-cn-hangzhou.aliyuncs.com";//"http://pic.tenzhao.com.w.kunlunca.com";
	
	public final static String ACCESSKEY_ID = "ti3Y9R3SPbn0Xsgz"; //"GAmc92Y0mTggVzLN";
	
	public final static String ACCESSKEY_SECRET = "J7SFlTprvufRA1Vpn54jCJzeZGIEMN" ;//"IcqB80Rz7Vze0IKjkmKH2nbpoVXFtr";
	
}

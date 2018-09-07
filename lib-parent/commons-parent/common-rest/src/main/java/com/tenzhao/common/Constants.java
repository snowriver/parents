package com.tenzhao.common;

public final class Constants {
		public final static String CHARSET_UTF8 = "UTF-8";
		public final static String CHARSET_GBK = "GBK";
		public static final String CTYPE_FORM_DATA = "application/x-www-form-urlencoded";
		public static final String CTYPE_FILE_UPLOAD = "multipart/form-data";
		public static final String CTYPE_TEXT_XML = "text/xml";
		public static final String CTYPE_TEXT_PLAIN = "text/plain";
		public static final String CTYPE_APP_JSON = "application/json";
	    
	    public final static String METHOD_POST ="POST"; 
	    
	    public final static String METHOD_GET="GET"; 
	    
	    public final static String METHOD_PUT="PUT"; 
	    
	    public final static String METHOD_DELETE="DELETE"; 
	    
		/** 响应编码 */
		public static final String ACCEPT_ENCODING = "Accept-Encoding";
		public static final String CONTENT_ENCODING = "Content-Encoding";
		public static final String CONTENT_ENCODING_GZIP = "gzip";

		/** 默认媒体类型 **/
		public static final String MIME_TYPE_DEFAULT = "application/octet-stream";

		/** 默认流式读取缓冲区大小 **/
		public static final int READ_BUFFER_SIZE = 1024 * 4;
		public static final String  TOP_HTTP_DNS_HOST  = "TOP_HTTP_DNS_HOST";
		/** 默认连接超时 15秒 */
		public static final int CONN_TIME_OUT_DEFAULT = 15000;
		/** 默认读取超时  30秒 */
		public static final int READ_TIME_OUT_DEFAULT = 30000;
}

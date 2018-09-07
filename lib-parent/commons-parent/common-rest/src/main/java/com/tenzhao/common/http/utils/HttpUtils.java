package com.tenzhao.common.http.utils;

import static com.tenzhao.common.Constants.CHARSET_UTF8;
import static com.tenzhao.common.Constants.CONN_TIME_OUT_DEFAULT;
import static com.tenzhao.common.Constants.CONTENT_ENCODING_GZIP;
import static com.tenzhao.common.Constants.CTYPE_APP_JSON;
import static com.tenzhao.common.Constants.METHOD_DELETE;
import static com.tenzhao.common.Constants.METHOD_GET;
import static com.tenzhao.common.Constants.METHOD_POST;
import static com.tenzhao.common.Constants.METHOD_PUT;
import static com.tenzhao.common.Constants.READ_TIME_OUT_DEFAULT;
import static com.tenzhao.common.Constants.TOP_HTTP_DNS_HOST;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.tenzhao.common.Constants;
import com.tenzhao.common.FileItem;

public abstract class HttpUtils {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static boolean ignoreSSLCheck = true; // 忽略SSL检查
	private static boolean ignoreHostCheck = true; // 忽略HOST检查

	public static class TrustAllTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}
	}

	public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
		HttpUtils.ignoreSSLCheck = ignoreSSLCheck;
	}

	public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
		HttpUtils.ignoreHostCheck = ignoreHostCheck;
	}

	public static String doPost(String url, Map<String, String> params,Map<String, String> headerMap) throws IOException{
		return doPost( url,params, DEFAULT_CHARSET, CONN_TIME_OUT_DEFAULT,
				READ_TIME_OUT_DEFAULT,headerMap, null);
	}
	
	private static String getCharSet(String charSet) {
		return StringUtils.isBlank(charSet)?DEFAULT_CHARSET:charSet;
	}
	
	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout)
			throws IOException {
		return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout,
			int readTimeout) throws IOException {
		return doPost(url, params, charset, connectTimeout, readTimeout, null, null);
	}

	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout,
			int readTimeout, Map<String, String> headerMap, Proxy proxy) throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		byte[] content = {};
		if (query != null) {
			content = query.getBytes(charset);
		}
		return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap, proxy);
	}

	public static String doPost(String url, String apiBody, String charset, int connectTimeout, int readTimeout,
			Map<String, String> headerMap) throws IOException {
		String ctype = "text/plain;charset=" + charset;
		byte[] content = apiBody.getBytes(charset);
		return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap, null);
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param ctype
	 *            请求类型
	 * @param content
	 *            请求字节数组
	 * @return 响应字符串
	 */
	public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout)
			throws IOException {
		return _doPost(url, ctype, content, connectTimeout, readTimeout, null, null);
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param ctype
	 *            请求类型
	 * @param content
	 *            请求字节数组
	 * @param headerMap
	 *            请求头部参数
	 * @return 响应字符串
	 */
	public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout,
			Map<String, String> headerMap, Proxy proxy) throws IOException {
		return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap, proxy);
	}

	private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout,
			Map<String, String> headerMap, Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap, proxy);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			out = conn.getOutputStream();
			out.write(content);
			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param textParams
	 *            文本请求参数
	 * @param fileParams
	 *            文件请求参数
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams,
			int connectTimeout, int readTimeout) throws IOException {
		if (fileParams == null || fileParams.isEmpty()) {
			return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
		} else {
			return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
		}
	}

	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams,
			String charset, int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, null);
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param textParams
	 *            文本请求参数
	 * @param fileParams
	 *            文件请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @param headerMap
	 *            需要传递的header头，可以为空
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams,
			String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
		if (fileParams == null || fileParams.isEmpty()) {
			return doPost(url, params, charset, connectTimeout, readTimeout, headerMap, null);
		} else {
			return _doPostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
		}
	}

	private static String _doPostWithFile(String url, Map<String, String> params, Map<String, FileItem> fileParams,
			String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
		String boundary = String.valueOf(System.nanoTime()); // 随机分隔线
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			String ctype = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
			conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap, null);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			out = conn.getOutputStream();
			byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);

			// 组装文本请求参数
			Set<Entry<String, String>> textEntrySet = params.entrySet();
			for (Entry<String, String> textEntry : textEntrySet) {
				byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), charset);
				out.write(entryBoundaryBytes);
				out.write(textBytes);
			}

			// 组装文件请求参数
			Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
			for (Entry<String, FileItem> fileEntry : fileEntrySet) {
				FileItem fileItem = fileEntry.getValue();
				if (!fileItem.isValid()) {
					throw new IOException("FileItem is invalid");
				}
				byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(),
						charset);
				out.write(entryBoundaryBytes);
				out.write(fileBytes);
				fileItem.write(out);
			}

			// 添加请求结束标志
			byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
			out.write(endBoundaryBytes);
			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset)
			throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param url 请求地址
	 * @return 响应字符串
	 */
	public static String doGet(String url) throws IOException {
		return doGet(url, null);
	}
	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param headerMap
	 *            请求头
	 * @return 响应字符串
	 */
	public static String doGet(String url, Map<String, String> headerMap) throws IOException {
		return doGet(url,headerMap, null);
	}
	public static String doGet(String url, Map<String, String> headerMap, Map<String, String> params) throws IOException {
		return doGet(url,headerMap, params, DEFAULT_CHARSET);
	}
	public static String doGet(String url, Map<String, String> headerMap, Map<String, String> params,String charset) throws IOException {
		return doGet(url,headerMap, params, charset,null);
	}
	
	public static String doGet(String url, Map<String, String> headerMap, Map<String, String> params,String charset,Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;
		try {
			String ctype = "application/x-www-form-urlencoded;charset=" + charset;
			String query = buildQuery(params, charset);
			conn = getConnection(buildGetUrl(url, query),METHOD_GET, ctype, headerMap, proxy);
			rsp = getResponseAsString(conn);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rsp;
	}

	private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap,
			Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		if (proxy == null) {
			conn = (HttpURLConnection) url.openConnection();
		} else {
			conn = (HttpURLConnection) url.openConnection(proxy);
		}
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection connHttps = (HttpsURLConnection) conn;
			if (ignoreSSLCheck) {
				try {
					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(null, new TrustManager[] { new TrustAllTrustManager() }, new SecureRandom());
					connHttps.setSSLSocketFactory(ctx.getSocketFactory());
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				} catch (Exception e) {
					throw new IOException(e.toString());
				}
			} else {
				if (ignoreHostCheck) {
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				}
			}
			conn = connHttps;
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (headerMap != null && headerMap.get(TOP_HTTP_DNS_HOST) != null) {
			conn.setRequestProperty("Host", headerMap.get(TOP_HTTP_DNS_HOST));
		} else {
			conn.setRequestProperty("Host", url.getHost());
		}
		conn.setRequestProperty("Accept", "application/xml,application/json");// text/html,text/javascript
		conn.setRequestProperty("User-Agent", "top-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		if (headerMap != null) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				if (!TOP_HTTP_DNS_HOST.equals(entry.getKey())) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
		}
		return conn;
	}

	private static URL buildGetUrl(String url, String query) throws IOException {
		if (StringUtils.isEmpty(query)) {
			return new URL(url);
		}

		return new URL(buildRequestUrl(url, query));
	}

	public static String buildRequestUrl(String url, String... queries) {
		if (queries == null || queries.length == 0) {
			return url;
		}

		StringBuilder newUrl = new StringBuilder(url);
		boolean hasQuery = url.contains("?");
		boolean hasPrepend = url.endsWith("?") || url.endsWith("&");

		for (String query : queries) {
			if (!StringUtils.isEmpty(query)) {
				if (!hasPrepend) {
					if (hasQuery) {
						newUrl.append("&");
					} else {
						newUrl.append("?");
						hasQuery = true;
					}
				}
				newUrl.append(query);
				hasPrepend = false;
			}
		}
		return newUrl.toString();
	}

	public static String buildQuery(Map<String, String> params, String charset) throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
	}

	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
			String contentEncoding = conn.getContentEncoding();
			if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
				return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
			} else {
				return getStreamAsString(conn.getInputStream(), charset);
			}
		} else {
			// OAuth bad request always return 400 status
			InputStream error = conn.getErrorStream();
			if (error != null) {
				return getStreamAsString(error, charset);
			}else {
				// Client Error 4xx and Server Error 5xx
				throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
			}
		}
	}

	public static String getStreamAsString(InputStream stream, String charset) throws IOException {
		try {
			Reader reader = new InputStreamReader(stream, charset);
			StringBuilder response = new StringBuilder();

			final char[] buff = new char[1024];
			int read = 0;
			while ((read = reader.read(buff)) > 0) {
				response.append(buff, 0, read);
			}

			return response.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * 使用默认的UTF-8字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 反编码后的参数值
	 */
	public static String decode(String value) {
		return decode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用默认的UTF-8字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 编码后的参数值
	 */
	public static String encode(String value) {
		return encode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用指定的字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 反编码后的参数值
	 */
	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 使用指定的字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 编码后的参数值
	 */
	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 从URL中提取所有的参数。
	 * 
	 * @param query
	 *            URL地址
	 * @return 参数映射
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}

	/**
	 * 默认Content-Type application/json ;charset:utf-8<br>
	 * 默认没有使用代理请求
	 * 
	 * @param _url
	 * @param params
	 * @param heads
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String _url, String params, Map<String, String> heads) throws IOException {
		return doPost(_url,  params,  heads, DEFAULT_CHARSET);
	}
	public static String doPost(String _url, String params, Map<String, String> heads,String charSet) throws IOException {
		return _doPost(_url, CTYPE_APP_JSON, params.getBytes(getCharSet(charSet)),
				CONN_TIME_OUT_DEFAULT, READ_TIME_OUT_DEFAULT, heads, null);
	}
	
	public static String doPut(String _url) throws IOException {
		return doPut(_url, null,null, DEFAULT_CHARSET);
	}

	/**
	 * 参数拼接到url后面
	 * 
	 * @param _url
	 * @param heads
	 * @return
	 * @throws IOException
	 */
	public static String doPut(String _url, Map<String, String> params) throws IOException {
		return doPut(_url, params,null, DEFAULT_CHARSET);
	}
	
	public static String doPut(String _url, Map<String, String> params,Map<String, String> heads) throws IOException {
		return doPut(_url, params,heads, DEFAULT_CHARSET);
	}

	public static String doPut(String url, Map<String, String> params, Map<String, String> heads, String charset) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;
		try {
			String query = buildQuery(params, charset);
			conn = getConnection(buildGetUrl(url, query), METHOD_PUT, Constants.CTYPE_APP_JSON, heads, null);
			rsp = getResponseAsString(conn);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return rsp;
	}
	
	public static String doDelete(String apiUrl, Map<String, String> params, Map<String, String> heads, String charset)
			throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;

		try {
			String ctype = "application/x-www-form-urlencoded;charset=" + charset;
			String query = buildQuery(params, charset);
			conn = getConnection(buildGetUrl(apiUrl, query), METHOD_DELETE, ctype, null, null);
			rsp = getResponseAsString(conn);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	public static String doDelete(String apiUrl, Map<String, String> heads) throws IOException {
		return doDelete(apiUrl, null, heads, DEFAULT_CHARSET);
	}
	
}

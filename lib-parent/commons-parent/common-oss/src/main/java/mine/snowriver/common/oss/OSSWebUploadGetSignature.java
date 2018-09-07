package mine.snowriver.common.oss;

import static mine.snowriver.common.oss.OSSClientConfig.ACCESSKEY_ID;
import static mine.snowriver.common.oss.OSSClientConfig.BUCKET;
import static mine.snowriver.common.oss.OSSClientConfig.ENDPOINT_INTERNET_ALI;
import static mine.snowriver.common.oss.OSSClientConfig.HTTP_VALUE;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;

import net.sf.json.JSONObject;

/**
 * OSS web直传时，异步动态获取签名
 * @author chenxj
 */
public class OSSWebUploadGetSignature {
	
	/**
	 * 响应异步请求的，动态签名。在前端Controller方法中调用 eg:<code><pre>
	 * @RequestMapping(value="/signature",method=RequestMethod.GET)
	 * public void getSignature(@RequestParam String dir,HttpServletRequest request,HttpServletResponse response){
     *  try { 	
     *   	OSSWebUploadGetSignature.getSignature(dir, request, response);
     *   } catch (Exception e) {
     *   	LOGGER.error("{}",e);
     *   }
     * }
	 * </pre></code>
	 * @param dir 指定要上传的目录,必须以斜杠结尾 eg:"advert/100/"
	 * @param respMap 响应到前端页面的结果键值对,默认包含key：accessid、policy、signature、dir、host、expire、endpoint;可在调用此方法前自行添加所需Key
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void getSignature( String dir,Map<String, String> respMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
        String host = ENDPOINT_INTERNET_ALI.replaceAll(HTTP_VALUE, HTTP_VALUE+BUCKET+".") ;
        OSSClient client = OSSClientInit.getOSSClientByCname();
        try { 	
        	long expireTime = 3000;
        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(OSSConstants.DEFAULT_CHARSET_NAME);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            respMap.put("accessid", ACCESSKEY_ID);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);//
            respMap.put("expire",String.valueOf(expireEndTime / 1000));
            respMap.put("endpoint", ENDPOINT_INTERNET_ALI);
//            respMap.put("cdnHost", OSS_IMG_SERVICE_DOMAIN_CNAME);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            response(request, response, JSONObject.fromObject(respMap).toString());
        } catch (Exception e) {
        	throw new Exception(e);
        }finally{
        	 client.shutdown();
        }
    }
	
	private static void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
		String callbackFunName = request.getParameter("callback");
		if (callbackFunName==null || callbackFunName.equalsIgnoreCase("")){
			response.getWriter().println(results);
		}else{
			response.getWriter().println(callbackFunName + "( "+results+" )");
		}
		response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
	}
}

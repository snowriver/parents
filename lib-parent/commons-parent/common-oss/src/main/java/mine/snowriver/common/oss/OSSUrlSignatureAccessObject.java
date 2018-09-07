package mine.snowriver.common.oss;

import java.net.URL;
import java.util.Date;

import com.aliyun.oss.OSSClient;

/**
 * 如果Bucket设置私有访问,在浏览器端访问图片时则需要<a href="https://help.aliyun.com/do
 * cument_detail/32016.html?spm=5176.doc31857.2.3.9jP6Br#使用URL签名授权访问">使用URL签名授权访问</a> <br>
 * @author chenxj
 */
public class OSSUrlSignatureAccessObject {

	/**
	 * ton为指定图片生成URL签名路径
	 * @param client
	 * @param objectkey eg:advert/100/4.jpg
	 * @return
	 */
	public static URL getSimpleSignatureURL(OSSClient client,String objectkey){
		// 设置URL过期时间为1小时
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		if(client == null ){
			client = OSSClientInit.getOSSClientByCname() ;
		}
		URL url = client.generatePresignedUrl(OSSClientConfig.BUCKET, objectkey, expiration);
		return url ;
	}
	
}

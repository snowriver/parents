package mine.snowriver.common.oss;

import java.io.File;

import com.aliyun.oss.OSSClient;

/**
 * Unit test for simple App.
 */
public class AppTest 
   
{
   public static void main(String[] args){
	// endpoint以杭州为例，其它region请按实际情况填写
	   String endpoint = OSSClientConfig.ENDPOINT_INTERNET_ALI ;
	   // accessKey请登录https://ak-console.aliyun.com/#/查看
	   String accessKeyId = OSSClientConfig.ACCESSKEY_ID ;
	   String accessKeySecret = OSSClientConfig.ACCESSKEY_SECRET ;
	   // 创建OSSClient实例
	   OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	   // 上传文件
	   ossClient.putObject(OSSClientConfig.BUCKET, "test/11.reg", new File("D:\\1.reg"));
	   // 关闭client
	   ossClient.shutdown();
	   
   }
}

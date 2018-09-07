/*
 * Copyright 2014-2016 My current company name.
 */

package mine.snowriver.common.oss;

import static mine.snowriver.common.oss.OSSClientConfig.ACCESSKEY_ID;
import static mine.snowriver.common.oss.OSSClientConfig.ACCESSKEY_SECRET;
import static mine.snowriver.common.oss.OSSClientConfig.BUCKET;
import static mine.snowriver.common.oss.OSSClientConfig.ENDPOINT_INTERNET_ALI;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;
public class OSSUploadUtils {
	
	/**
	 * 流式上传,并指定上传后的相对于bucket的文件路径名
	 * @param inputStream
	 * @param key object的对象key相当于文件的相对路径名  eg:advert/laksdwelsk.jpg
	 */
	public static void streamUpload(InputStream inputStream,String key){
		OSSClient ossClient = new OSSClient(ENDPOINT_INTERNET_ALI, ACCESSKEY_ID, ACCESSKEY_SECRET);
		ossClient.putObject(BUCKET, key, inputStream);
		ossClient.shutdown();
	}
	/**
	 * 重命名object对象
	 * @param sourceKey 原对象key eg:advert/laksdwelsk.jpg
	 * @param destinationKey 新对象key eg:advert/laksdwelsk123.jpg
	 */
	public static void renameFile(String sourceKey,String destinationKey){
		OSSClient ossClient = new OSSClient(ENDPOINT_INTERNET_ALI, ACCESSKEY_ID, ACCESSKEY_SECRET);
		ossClient.copyObject(BUCKET, sourceKey, BUCKET, destinationKey);
		ossClient.deleteObject(BUCKET, sourceKey);
		// 关闭OSSClient。
		ossClient.shutdown();
	}
}

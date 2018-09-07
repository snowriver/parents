package com.tenzhao.commons.wx.pay.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;

public class WXSignUtils {
	private static String Key = "32503e609939f40e23162e8fc0681fAC";
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WXSignUtils.class);

    /**
     * 生成获取预支付签名 sign 
     * @param characterEncoding
     * @param parameters
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) 
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Key);
        String signStr = DigestUtils.md5Hex(sb.toString()).toUpperCase() ;
        LOGGER.debug("获取prepayid的参数："+sb.toString()+"   prepayid签名字符串："+signStr);
        return signStr;
    }
   
}

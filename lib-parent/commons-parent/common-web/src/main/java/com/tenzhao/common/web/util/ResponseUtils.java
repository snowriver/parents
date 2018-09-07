package com.tenzhao.common.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public final class ResponseUtils {
	 private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);
    public static void writeJsonObject(HttpServletResponse response, Object object) {
        writeJsonObject(response, object, null);
    }

    public static void writeJsonObject(HttpServletResponse response, Object object, String jsoncallback) {
        String jsonStr = "{}";

        if (object != null) {
            jsonStr = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
        }

        StringBuilder sb = new StringBuilder();
        if (jsoncallback != null) {
            sb.append(jsoncallback).append("(").append(jsonStr).append(")");
        } else {
            sb.append(jsonStr);
        }

        writeJson(response, sb.toString());
    }

    public static void writeJsonArray(HttpServletResponse response, Object object) {
        writeJsonArray(response, object, null);
    }

    public static void writeJsonArray(HttpServletResponse response, Object object, String jsoncallback) {
        String jsonStr = "[]";

        if (object != null) {
            jsonStr = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
        }

        StringBuilder sb = new StringBuilder();
        if (jsoncallback != null) {
            sb.append(jsoncallback).append("(").append(jsonStr).append(")");
        } else {
            sb.append(jsonStr);
        }

        writeJson(response, sb.toString());
    }

    public static void writeJson(HttpServletResponse response, String jsonStr) {
    	writeJson(response, jsonStr,true);
    }
    /**
     * 
     * @param response
     * @param jsonStr
     * @param isWriteResponselog 响应数据是否写到日志文件，有些接口数据太大，可以不写就设置为false,默认写info级的日志
     */
    public static void writeJson(HttpServletResponse response, String jsonStr,boolean isWriteResponselog) {
    	write(response, "application/json; charset=UTF-8", jsonStr,isWriteResponselog);
    }

    public static void writeText(HttpServletResponse response, String text) {
    	writeText(response, text,true);
    }
    
    public static void writeText(HttpServletResponse response, String text,boolean isWriteResponselog) {
        write(response, "text/plain; charset=UTF-8", text,isWriteResponselog);
    }
    
    /**
     * 
     * @param response
     * @param htmlElement <code>&lt;script&gt;alert(1);&lt;/script&gt;</code>
     */
    public static void writeHtml(HttpServletResponse response, String htmlElement) {
    	write(response, "text/html; charset=UTF-8", htmlElement);
    }

    private static void write(HttpServletResponse response, String contentType, String s) {
    	write(response,contentType,s,true);
    }

    private static void write(HttpServletResponse response, String contentType, String s,boolean isWriteResponseLog){
    	response.setContentType(contentType);
    	PrintWriter out = null;
    	String requestUrl = response.getHeader("requestUrl")==null?"":response.getHeader("requestUrl");
    	try {
    		if(isWriteResponseLog){
    			LOGGER.info("响应Url>>>{} :{}",requestUrl,s);
    		}
    		out = response.getWriter();
    		out.write(s);
    	} catch (IOException e) {
    		throw new RuntimeException(e.getMessage(), e);
    	} finally {
    		IOUtils.closeQuietly(out);
    	}
    }
    private ResponseUtils() {
    }
}

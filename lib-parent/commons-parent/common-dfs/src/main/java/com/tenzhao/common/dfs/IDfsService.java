package com.tenzhao.common.dfs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;

public interface IDfsService {
    /**
     * 上传文件
     * 
     * @param data 本地文件内容
     * @param extension 文件扩展名
     * @return 文件系统生成的文件ID
     */
    String[] upload(List<byte[]> list, List<String> extension) throws IOException;

    /**
     * 上传文件
     * 
     * @param pathname 本地文件路径
     * @return 文件系统生成的文件ID
     */
    String[] upload(String...arrpathname) throws IOException;

    /**
     * 上传文件
     * 
     * @param file 本地文件
     * @return 文件系统生成的文件ID
     */
    String[] upload(File...arrfile) throws IOException;

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @return 文件内容
     */
    List<byte[]> download(String... fileId);

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @param pathname 本地文件路径 eg:e:\1.png
     */
    String[] download(String[] pathname ,String... fileId );

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @param file 本地文件
     */
    String[] download(File[] files,String ...fileId);

    /**
     * 删除文件
     * 
     * @param fileId 文件ID
     */
    void remove(String ... fileId);

    /**
     * 获取文件信息
     * 
     * @param fileId 文件ID
     * @return 文件信息
     */
    FileInfo getFileInfo(String fileId);

    /**
     * 获取文件元数据
     * 
     * @param fileId 文件ID
     * @return 文件元数据
     */
    NameValuePair[] getMetaData(String fileId);
}

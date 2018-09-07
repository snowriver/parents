package com.tenzhao.common.dfs;

import java.io.File;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;

public interface DfsService {
    /**
     * 上传文件
     * 
     * @param data 本地文件内容
     * @param extension 文件扩展名
     * @return 文件系统生成的文件ID
     */
    String upload(byte[] data, String extension);

    /**
     * 上传文件
     * 
     * @param pathname 本地文件路径
     * @return 文件系统生成的文件ID
     */
    String upload(String pathname);

    /**
     * 上传文件
     * 
     * @param file 本地文件
     * @return 文件系统生成的文件ID
     */
    String upload(File file);

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @return 文件内容
     */
    byte[] download(String fileId);

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @param pathname 本地文件路径
     */
    void download(String fileId, String pathname);

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @param file 本地文件
     */
    void download(String fileId, File file);

    /**
     * 删除文件
     * 
     * @param fileId 文件ID
     */
    void remove(String fileId);

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

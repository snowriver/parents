package com.tenzhao.common.dfs;

import java.io.File;
import java.net.InetSocketAddress;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.io.Files;

public class DefaultDfsService implements DfsService, InitializingBean {
    private static final int DEFAULT_CONNECT_TIMEOUT = 5;
    private static final int DEFAULT_NETWORK_TIMEOUT = 30;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int DEFAULT_TRACKER_HTTP_PORT = 8787;
    private static final String DEFAULT_SECRET_KEY = "FastDFS1234567890";

    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private int networkTimeout = DEFAULT_NETWORK_TIMEOUT;
    private String charset = DEFAULT_CHARSET;
    private int trackerHttpPort = DEFAULT_TRACKER_HTTP_PORT;
    private boolean antiStealToken;
    private String secretKey = DEFAULT_SECRET_KEY;
    private String trackerServers = "10.0.6.127:22122";

    @Override
    public String upload(byte[] data, String extension) {
        StorageClient1 sc = getStorageClient();
        try {
            return sc.upload_file1(data, extension, null);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String upload(String pathname) {
        StorageClient1 sc = getStorageClient();
        try {
            return sc.upload_file1(pathname, Files.getFileExtension(pathname), null);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String upload(File file) {
        return upload(file.getAbsolutePath());
    }

    @Override
    public byte[] download(String fileId) {
        StorageClient1 sc = getStorageClient();
        try {
            return sc.download_file1(fileId);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void download(String fileId, String pathname) {
        StorageClient1 sc = getStorageClient();
        try {
            sc.download_file1(fileId, pathname);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void download(String fileId, File file) {
        download(fileId, file.getAbsolutePath());
    }

    @Override
    public void remove(String fileId) {
        StorageClient1 sc = getStorageClient();
        try {
            sc.delete_file1(fileId);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public FileInfo getFileInfo(String fileId) {
        StorageClient1 sc = getStorageClient();
        try {
            return sc.get_file_info1(fileId);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public NameValuePair[] getMetaData(String fileId) {
        StorageClient1 sc = getStorageClient();
        try {
            return sc.get_metadata1(fileId);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientGlobal.setG_connect_timeout(connectTimeout * 1000);
        ClientGlobal.setG_network_timeout(networkTimeout * 1000);
        ClientGlobal.setG_charset(charset);
        ClientGlobal.setG_tracker_http_port(trackerHttpPort);
        ClientGlobal.setG_anti_steal_token(antiStealToken);
        ClientGlobal.setG_secret_key(secretKey);

        if (!Strings.isNullOrEmpty(trackerServers)) {
            String[] trackerServerList = trackerServers.split(";");
            InetSocketAddress[] trackerServers = new InetSocketAddress[trackerServerList.length];
            for (int i = 0; i < trackerServerList.length; ++i) {
                String[] ss = trackerServerList[i].split("\\:");
                if (ss.length != 2) {
                    throw new RuntimeException("The value of trackerServers is invalid, the correct format is host1:port1;host2:port2");
                }
                trackerServers[i] = new InetSocketAddress(ss[0], Integer.parseInt(ss[1]));
            }
            ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
        } else {
            throw new RuntimeException("No value of trackerServers");
        }
    }

    private static StorageClient1 getStorageClient() {
        try {
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();

            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            ProtoCommon.activeTest(trackerServer.getSocket());
            return storageClient;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getNetworkTimeout() {
        return networkTimeout;
    }

    public void setNetworkTimeout(int networkTimeout) {
        this.networkTimeout = networkTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getTrackerHttpPort() {
        return trackerHttpPort;
    }

    public void setTrackerHttpPort(int trackerHttpPort) {
        this.trackerHttpPort = trackerHttpPort;
    }

    public boolean isAntiStealToken() {
        return antiStealToken;
    }

    public void setAntiStealToken(boolean antiStealToken) {
        this.antiStealToken = antiStealToken;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTrackerServers() {
        return trackerServers;
    }

    public void setTrackerServers(String trackerServers) {
        this.trackerServers = trackerServers;
    }
}

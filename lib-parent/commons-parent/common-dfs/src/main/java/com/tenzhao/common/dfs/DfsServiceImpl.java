package com.tenzhao.common.dfs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.test.DownloadFileWriter;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * 
 * @author chenxj
 *
 */
public class DfsServiceImpl implements IDfsService, InitializingBean {

	private static String CONF_FILENAME = "config.properties";
	
    @Override
    public String[] upload(List<byte[]> listdata, List<String> extension) throws IOException {
        StorageClient1 sc = getStorageClient();
        List<String> list = Lists.newArrayList();
        String[] res = null ;
        String fileId = "" ;
        try {
        	
        	for(int i = 0 ;i<listdata.size() ;i++){
        		fileId = sc.upload_file1(listdata.get(i),extension.get(i) , null);
        		list.add(fileId) ;
        	 }
        } catch (Exception e) {
        	trackerServer.getInputStream().available() ;
        	//tracker.g
            throw Throwables.propagate(e);
        }
        if( null != list &&list.size()>0){
			res = list.toArray(new String[list.size()]);
			list.clear();
		}
        
        close();
		return res;
    }

    @Override
    public String[] upload(String ...arrPathname) throws IOException {
    	
    	StorageClient1 sc = getStorageClient();
        List<String> list = Lists.newArrayList();
        String[] res = null ;
        String fileId = "" ;
        try {
        	 for( String pathname : arrPathname ){
        		 
        		 fileId = sc.upload_file1(pathname, Files.getFileExtension(pathname), null);
        		 list.add(fileId) ;
        	 }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        if( null != list &&list.size()>0){
			res = list.toArray(new String[list.size()]);
			list.clear();
		}
        close();
		return res;
    }

    @Override
    public String[] upload(File ...arrfile) throws IOException {
    	String[] filepath = new String[arrfile.length];
    	for( int i = 0;i<arrfile.length;i++ ){
    		filepath[i] = arrfile[i].getAbsolutePath();
    	}
        return upload(filepath);
    }

    @Override
    public List<byte[]> download(String ... fileId) {
        StorageClient1 sc = getStorageClient();
        
        List<byte[]> list = Lists.newArrayList();
        try {
        	for(String fileid : fileId){
        		
        		list.add(sc.download_file1(fileid));

        	}
        	close();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        
        return list ;
    }

    @Override
    public String[] download(String[] pathname,String ...fileId) {
        StorageClient1 sc = getStorageClient();
        String dirTemp = "";
        List<String> list = Lists.newArrayList();
        try {
        	dirTemp = System.getProperty("java.io.tmpdir")+File.separator ;// fileId_temp.replaceAll("/", "-");	//临时目录
        
        	for( int i = 0 ;i<fileId.length ;i++){
        		try{
        			sc.download_file1( fileId[i] , pathname[i] );
        			list.add(pathname[i]) ;
        		}catch(Exception e){
        			dirTemp = dirTemp+fileId[i].replaceAll("/", "-");
        			sc.download_file1(fileId[i] , new DownloadFileWriter( dirTemp ) ) ;
        			list.add( dirTemp ) ;
        		}
        		
        	}
        	
        	close();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        String[] res = null; 
        if(null!=list && list.size()>0){
			res = list.toArray(new String[list.size()]);
			list.clear();
		}
        return res ;
    }

    @Override
    public String[] download(File[] files,String ...fileId ) {
    	String[] pathname = null ;
    	if( files != null ){
    		pathname = new String[files.length] ;
    	}
    	for ( int i = 0 ;i<files.length ;i++){
    		pathname[i] = files[i].getAbsolutePath() ;
    	}
       return  download(pathname,fileId);
    }

    @Override
    public void remove(String... fileids) {
        StorageClient1 sc = getStorageClient();
        try {
        	for( String fileid : fileids){
        		sc.delete_file1(fileid);
        	}
        	close();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public FileInfo getFileInfo(String fileId) {
        StorageClient1 sc = getStorageClient();
        try {
        	FileInfo fileinfo = sc.get_file_info1(fileId);
        	close();
            return fileinfo ;
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
    	String classPath = new File(DfsServiceImpl.class.getResource("/").getFile()).getCanonicalPath();
		ClientGlobal.init(classPath + File.separator + CONF_FILENAME);
		
		tracker = new TrackerClient();
		trackerServer = tracker.getConnection();
		StorageServer storageServer = null ;
		setStorageClient(new StorageClient1(trackerServer, storageServer));
		ProtoCommon.activeTest(trackerServer.getSocket());
    }
    
    /**
	 * 关闭连接
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (null != trackerServer) {
			trackerServer.close();
		}
	}

   public TrackerClient getTracker() {
		return tracker;
	}

	public void setTracker(TrackerClient tracker) {
		this.tracker = tracker;
	}

	public TrackerServer getTrackerServer() {
		return trackerServer;
	}

	public void setTrackerServer(TrackerServer trackerServer) {
		this.trackerServer = trackerServer;
	}



	private TrackerClient tracker ;
    private TrackerServer trackerServer ;
    private StorageClient1 storageClient ;
    public StorageClient1 getStorageClient() {
        return this.storageClient ;
    }

	public void setStorageClient(StorageClient1 storageClient) {
		this.storageClient = storageClient;
	}

}

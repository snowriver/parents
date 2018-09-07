package org.jitsi.util;
/**
 * 版本处理
 * @author chenxj
 */
public class VersionUtils {
	/**
	 * 判断用户的版本是否最新,是返回 0,-1用户版本低于市场版本,1用户版本高于市场版本(通常是测试版)
	 * @param onUsingVersion 用户当前在用的版本
	 * @param storeNewestVersion 应用市场刚上架的最新版本,存在数据库
	 * @return
	 */
	public static Integer compareVersion(String onUsingVersion,String storeNewestVersion) throws IllegalArgumentException{
		int isNewest = 0 ;
		String[] _onUsingVersion = onUsingVersion.split("\\.");
		String[] _storeNewestVersion = storeNewestVersion.split("\\.");
		for(int i = 0 ;i<_storeNewestVersion.length;i++){
			String _tmpOnUseingVersion = (i>=_onUsingVersion.length?"0":_onUsingVersion[i]);
			String _tmpStoreNewestVersion = _storeNewestVersion[i];
			if(Integer.parseInt(_tmpOnUseingVersion) > Integer.parseInt(_tmpStoreNewestVersion)){
				isNewest = 1;
			}else if(Integer.parseInt(_tmpOnUseingVersion)< Integer.parseInt(_tmpStoreNewestVersion)){
				isNewest = -1;
			}
		}
		return isNewest ;
	}
}

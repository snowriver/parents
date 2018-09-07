package com.tenzhao.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * 负载均衡算法
 * @author chenxj
 */
public class LoadBalancingAlgorithm {
	
	/**
	 * 加权随机法,根据权重算出当前应该使用的权重类型/值
	 * @param serverMap key为权重类型，value为该类型的权重值
	 * @return
	 */
	public static <T> T weightRandom(Map<T,Integer> serverMap) {
		List<T> serverList = new ArrayList<T>();
		serverMap.forEach((k,weight)->{
			for(int i = 0;i<weight ;i++) {
				serverList.add(k);
			}
		});
		Random random = new Random();
		return serverList.get(random.nextInt(serverList.size()));
	}
	
	
}

package com.tenzhao.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 红包算法
 * @author chenxj
 *
 */
public class RedpacketAlgorithm {
	/**
	 * 红包算法
	 * 总金额必须与最小领取金额单位一致
	 * @param total 红包总金额
	 * @param least 中奖的最低金额
	 * @param num 红包总数量
	 * @return
	 * @see #assignRedpacket(Integer, Integer, Integer) 推荐使用
	 */
	@Deprecated
	public static List<Double> assignRedpacket( Double total,Double least,Integer num) {
		List<Double> list = new ArrayList<Double>(num);
		for(int i=0;i<num;i++) {
			list.add(i, least);
			total = (new BigDecimal(total).subtract(new BigDecimal(least))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		for(double i=0 ;i<total;i+=least) {
			Random random = new Random();
			int index = random.nextInt(num);
			list.set(index, new BigDecimal(list.get(index)+least).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		return list ;
	}
	
	/**
	 * 红包算法
	 * 总金额必须与最小领取金额单位一致
	 * @param total 红包总金额,如果原来是小数点请转换成整数
	 * @param least 中奖的最低金额,将单位转换成整数
	 * @param num 红包总数量
	 * @return
	 */
	public static List<Integer> assignRedpacket( Integer total,Integer least,Integer num) {
		List<Integer> list = new ArrayList<Integer>(num);
		for(int i=0;i<num;i++) {
			list.add(i, least);
			total-=least;
		}
		for(double i=0 ;i<total;i+=least) {
			Random random = new Random();
			int index = random.nextInt(num);
			list.set(index, list.get(index)+least);
		}
		return list ;
	}
}

package com.tenzhao.common.criterion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 */
public class GroupProjection implements Projection {
	private static final long serialVersionUID = 1L;
	private List<String> propertiesName = new ArrayList<String>(2);
	private List<Criteria> oredCriteria = new ArrayList<>(2);
	
	public GroupProjection(String ... propertyName) {
		propertiesName.addAll(Arrays.asList(propertyName));
	}
    
	public Criteria createHavingCriteria() {
		Criteria criteria = Criteria.getInstance();
		oredCriteria.add(criteria);
		return criteria;
	}
	/**
	 * 方法仅支持与操作 eg: having state=1 and state like '%1';不支持或操作 eg:having state=1 or state =2
	 * @param expression
	 * @return
	 */
	public GroupProjection having(Criteria expression) {
		oredCriteria.add(expression);
		return this ;
	}
	
	public List<String> getPropertiesName(){
		return propertiesName ;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public static GroupProjection groupBy(String ... attrName) {
		return new GroupProjection(attrName);
	}
}

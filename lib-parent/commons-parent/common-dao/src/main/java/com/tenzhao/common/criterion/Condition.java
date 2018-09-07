package com.tenzhao.common.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.tenzhao.common.dao.OrderBy;
/**
 * 
 * 
 * 
 *              GroupProjection projection = GroupProjection.groupBy("grade") ;
				Criteria havingCriteria = projection.createHavingCriteria() ;
				havingCriteria.add(Restrictions.eq("state",1));
				
				Criteria orHavingCriteria = projection.createHavingCriteria() ;
				orHavingCriteria.add(Restrictions.eq("state",1));
				condition.addGroup(projection);
 * @author chenxj
 *
 */
public class Condition implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private List<Criteria> oredCriteria = new ArrayList<Criteria>(1); ;
	private Projection projection ;
	private Integer maxResults;
	private Integer pageNumber;
	private OrderBy orderBy;
	private LockMode lockMode = LockMode.NONE ;
	private String selectClause = "" ;
	private boolean isDistinct = false ;
	
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}
	private Condition() {};
	public Criteria createCriteria() {
		if(Objects.isNull(oredCriteria)) {
			oredCriteria = new ArrayList<Criteria>(2);
		}
		Criteria criteria = Criteria.getInstance();
		oredCriteria.add(criteria);
		return criteria;
	}

	public static Condition getInstance() {
		return new Condition();
	}
	public Projection getProjection() {
		return projection;
	}
	
	public Condition addGroup(Projection projection) {
		this.projection = projection ;
		return this;
	}
	public OrderBy getOrderBy() {
		return orderBy;
	}
	
	public Condition addOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy ;
		return this;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public Condition setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		return this ;
	}
	public Integer getMaxResults() {
		return maxResults;
	}
	public Condition setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
		return this ;
	}
	
	public LockMode getLockMode() {
		return lockMode ;
	}
	
	public Condition addLockMode(LockMode lockMode) {
		this.lockMode = lockMode ;
		return this ;
	}
	
	public String getSelectClause() {
		return selectClause;
	}

	public Condition addShowCols(String... customCols) {
		this.selectClause = Arrays.toString(customCols).replaceAll("\\[|\\]", "") ;
		return this;
	}
	public boolean isDistinct() {
		return isDistinct;
	}
	public void distinct() {
		this.isDistinct = true;
	}
	/**
	 * 还原至初始实例状态
	 */
	public void clear() {
		this.oredCriteria.clear();
		this.projection = null ;
		this.maxResults = null ;
		this.pageNumber = null ;
		this.orderBy = null ;
		this.lockMode = LockMode.NONE ;
		this.selectClause = "" ;
		this.isDistinct = false  ;
	}
}

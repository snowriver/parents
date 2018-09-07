package com.tenzhao.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tenzhao.common.criterion.Condition;
import com.tenzhao.common.criterion.UpdateEntry;
import com.tenzhao.common.dao.OrderBy;

public interface DAO<T, VO, PK extends Serializable> {
    void insert(T entity);

    T get(PK id);
    @Deprecated
    T get(VO condition);
    /**
     * 
     * @param cols 只查出指定列 格式如下：col1,col2,col3
     * @param id   指定id
     * @return
     */
    @Deprecated
    T get(String cols,PK id);

    List<T> find();
    @Deprecated
    List<T> find(OrderBy orderBy);
    /**
     * 
     * @param cols 查找指定列的所有数据
     * @return
     */
    List<T> find(String cols);
    /**
     * 
     * @param cols  查找指定列的数据并排序
     * @param orderBy
     * @return
     */
    List<T> find(String cols,OrderBy orderBy);
    List<T> find(int pageSize, int pageNumber);
    List<T> find(OrderBy orderBy, int pageSize, int pageNumber);
    List<T> find(VO condition);
    List<T> find(VO condition, OrderBy orderBy);
    List<T> find(VO condition, int pageSize, int pageNumber);
    List<T> find(VO condition, OrderBy orderBy, int pageSize, int pageNumber);
    List<T> find(String cols,VO condition, OrderBy orderBy, int pageSize, int pageNumber);
    List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy);
    List<T> findByIdList(List<PK> idList, VO condition);
    List<T> findByIdList(List<PK> idList, OrderBy orderBy);
    List<T> findByIdList(List<PK> idList);

    int count();
    int count(String column);
    int count(VO condition);
    int count(VO condition, String column);

    Map<String, Object> aggregate(String[] functions, String[] columns);
    Map<String, Object> aggregate(VO condition, String[] functions, String[] columns);
    int update(T entity, PK id);
    @Deprecated
    int update(Map<String, Object> entity, PK id);
    @Deprecated
    int update(T entity, VO condition);
    @Deprecated
    int updateByIdList(T entity, List<PK> idList, VO condition);
    @Deprecated
    int updateByIdList(T entity, List<PK> idList);
    @Deprecated
    int update(Map<String, Object> entity, VO condition);
    @Deprecated
    int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition);
    @Deprecated
    int updateByIdList(Map<String, Object> entity, List<PK> idList);
	/**
	 * 根据主键对指定字段进行加等于操作
	 * @param entity 封装指定字段及要增加的值
	 * @param id 主键
	 * @return
	 */
    @Deprecated
    int updateAddEquals(Map<String, Object> entity, PK id) ;
   
    /**
     * 根据条件对指定字段进行加等于操作
	 * @param entity 封装指定字段及要增加的值
	 * @param id 主键
     * @return
     */
    @Deprecated
    int updateAddEquals(Map<String, Object> entity, VO condition) ;
  
    /**
     * 根据条件及主键结合对指定字段进行加等于操作
	 * @param entity 封装指定字段及要增加的值
	 * @param id 主键
     * @return
     */
    @Deprecated
    int updateAddEquals(Map<String, Object> entity, List<PK> idList, VO condition) ;
    
    int remove(PK id);

	int getTimeForInt();

	long getTimeForLong();

	List<T> findByCondition(Condition condition);

	int findByConditionCount(Condition condition);

	/**
     * 条件删除
     * @param condition
     * @return
     */
    int remove(Condition condition);
    /**
     * 更新非空字段
     * @param entity
     * @param condition
     * @return
     */
    int update(T entity,Condition condition);
    /**
     * 强制更新
     * @param updateEntries
     * @param condition
     * @return
     */
    int update(List<UpdateEntry> updateEntries,Condition condition);
    int update(List<UpdateEntry> updateEntries,PK id);
}

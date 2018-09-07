package com.tenzhao.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tenzhao.common.dao.OrderBy;

public interface BaseMapper<T, VO, PK extends Serializable> {
	void insert(T entity);

    T get(PK id);
    
    
    
    T get(Map<String,Object> param) ;

    T get(String cols,PK id) ;
    
    T getById(PK id);

    List<T> find();
    
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

    int update(T entity, VO condition);
    int update(T entity, PK id);
    int updateByIdList(T entity, List<PK> idList, VO condition);
    int updateByIdList(T entity, List<PK> idList);

    int update(Map<String, Object> entity, VO condition);
    int update(Map<String, Object> entity, PK id);
    int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition);
    int updateByIdList(Map<String, Object> entity, List<PK> idList);

    int remove(VO condition);
    int remove(PK id);
    int removeByIdList(List<PK> idList, VO condition);
    int removeByIdList(List<PK> idList);
}

package com.tenzhao.common.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tenzhao.common.criterion.Condition;
import com.tenzhao.common.criterion.UpdateEntry;
import com.tenzhao.common.dao.DAO;
import com.tenzhao.common.dao.OrderBy;

public abstract class AbstractBO<T, VO, PK extends Serializable> implements BO<T, VO, PK> {
   

	protected abstract DAO<T, VO, PK> getDAO();

    @Override
    public void insert(T entity) {
        getDAO().insert(entity);
    }

    @Override
    public T get(VO condition) {
        return getDAO().get(condition);
    }

    @Override
    public T get(PK id) {
        return getDAO().get(id);
    }
    
    @Override
   	public T get(String cols, PK id) {
   		return getDAO().get(cols,id);
   	}

   	@Override
   	public List<T> find(String cols) {
   		return getDAO().find(cols);
   	}

   	@Override
   	public List<T> find(String cols, OrderBy orderBy) {
   		
   		return getDAO().find(cols,orderBy);
   	}

   	@Override
   	public List<T> find(String cols, VO condition, OrderBy orderBy,
   			int pageSize, int pageNumber) {
   		return getDAO().find(cols,condition,orderBy,pageSize,pageNumber);
   	}
    @Override
    public List<T> find() {
        return getDAO().find();
    }

    @Override
    public List<T> find(OrderBy orderBy) {
        return getDAO().find(orderBy);
    }

    @Override
    public List<T> find(int pageSize, int pageNumber) {
        return getDAO().find(pageSize, pageNumber);
    }

    @Override
    public List<T> find(OrderBy orderBy, int pageSize, int pageNumber) {
        return getDAO().find(orderBy, pageSize, pageNumber);
    }
    @Override
    public List<T> find(VO condition) {
        return getDAO().find(condition);
    }

    @Override
    public List<T> find(VO condition, OrderBy orderBy) {
        return getDAO().find(condition, orderBy);
    }

    @Override
    public List<T> find(VO condition, int pageSize, int pageNumber) {
        return getDAO().find(condition, pageSize, pageNumber);
    }
    @Override
    public List<T> find(VO condition, OrderBy orderBy, int pageSize, int pageNumber) {
        return getDAO().find(condition, orderBy, pageSize, pageNumber);
    }

//    @Override
//    public List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy) {
//        return getDAO().findByIdList(idList, condition, orderBy);
//    }
//
//    @Override
//    public List<T> findByIdList(List<PK> idList, VO condition) {
//        return getDAO().findByIdList(idList, condition);
//    }

    @Override
    public List<T> findByIdList(List<PK> idList, OrderBy orderBy) {
        return getDAO().findByIdList(idList, orderBy);
    }

    @Override
    public List<T> findByIdList(List<PK> idList) {
        return getDAO().findByIdList(idList);
    }

    @Override
    public int count() {
        return getDAO().count();
    }

    @Override
    public int count(VO condition) {
    	return getDAO().count(condition);
    }
    
//    @Override
//    public int count(String column) {
//        return getDAO().count(column);
//    }

//    @Override
//    public int count(VO condition, String column) {
//        return getDAO().count(condition, column);
//    }

    @Override
    public Map<String, Object> aggregate(String[] functions, String[] columns) {
        return getDAO().aggregate(functions, columns);
    }

    @Override
    public Map<String, Object> aggregate(VO condition, String[] functions, String[] columns) {
        return getDAO().aggregate(condition, functions, columns);
    }

    @Override
    public int update(T entity, VO condition) {
        return getDAO().update(entity, condition);
    }

    @Override
    public int update(T entity, PK id) {
        return getDAO().update(entity, id);
    }

    @Override
    public int updateByIdList(T entity, List<PK> idList, VO condition) {
        return getDAO().updateByIdList(entity, idList, condition);
    }

    @Override
    public int updateByIdList(T entity, List<PK> idList) {
        return getDAO().updateByIdList(entity, idList);
    }

    @Override
    public int update(Map<String, Object> entity, VO condition) {
        return getDAO().update(entity, condition);
    }

    @Override
    public int update(Map<String, Object> entity, PK id) {
        return getDAO().update(entity, id);
    }

    @Override
    public int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition) {
        return getDAO().updateByIdList(entity, idList, condition);
    }

    @Override
    public int updateByIdList(Map<String, Object> entity, List<PK> idList) {
        return getDAO().updateByIdList(entity, idList);
    }


    @Override
    public int remove(PK id) {
        return getDAO().remove(id);
    }

    @Override
    public int getTimeForInt(){
    	return getDAO().getTimeForInt();
    }
    @Override
    public long getTimeForLong(){
    	return getDAO().getTimeForLong();
    }
    @Override
    public  int updateAddEquals(Map<String, Object> entity, PK id){
    	return getDAO().updateAddEquals(entity,id);
    }
    @Override
    public int updateAddEquals(Map<String, Object> entity, VO condition){
    	return getDAO().updateAddEquals(entity,condition);
    }
    @Override
    public int updateAddEquals(Map<String, Object> entity, List<PK> idList, VO condition){
    	return getDAO().updateAddEquals(entity,idList,condition);
    }
    @Override
    public List<T> findByCondition(Condition condition){
    	return getDAO().findByCondition(condition);
    }
    @Override
    public int findByConditionCount(Condition condition){
    	return getDAO().findByConditionCount(condition);
    }
    /**
     * 条件删除
     * @param condition
     * @return
     */
    @Override
    public int remove(Condition condition) {
    	return getDAO().remove(condition);
    }
    /**
     * 更新非空字段
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public int update(T entity,Condition condition) {
    	return getDAO().update(entity,condition);
    }
    /**
     * 强制更新
     * @param updateEntries
     * @param condition
     * @return
     */
    @Override
    public int update(List<UpdateEntry> updateEntries,Condition condition) {
    	return getDAO().update(updateEntries,condition);
    }
    @Override
    public int update(List<UpdateEntry> updateEntries,PK id) {
    	return getDAO().update(updateEntries,id);
    }
}

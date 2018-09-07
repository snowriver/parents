package com.tenzhao.common.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;

import com.google.common.collect.Maps;
import com.tenzhao.common.criterion.Condition;
import com.tenzhao.common.criterion.UpdateEntry;

public abstract class AbstractDAO<T, VO, PK extends Serializable> implements DAO<T, VO, PK> {
    protected abstract SqlSessionTemplate getSqlSessionTemplate();

    protected abstract String getNamespace();

    @Override
    public void insert(T entity) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("entity", entity);

        getSqlSessionTemplate().insert(getNamespace() + ".insert", params);
    }

    @Override
    public T get(VO condition) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("condition", condition);

        return getSqlSessionTemplate().<T> selectOne(getNamespace() + ".get", params);
    }

    @Override
    public T get(PK id) {
       return getSqlSessionTemplate().<T> selectOne(getNamespace() + ".getById", id);
    }

    @Override
	public T get(String cols, PK id) {
    	 Map<String, Object> params = Maps.newHashMap();
    	 params.put("cols", cols);
         params.put("id", id);

         return getSqlSessionTemplate().<T> selectOne(getNamespace() + ".getByIdWithCols", params);
	}

	@Override
	public List<T> find(String cols) {
		return find(cols,null);
	}

	@Override
	public List<T> find(String cols, OrderBy orderBy) {
		 return find(cols,null,orderBy,-1,-1);
	}

	@Override
    public List<T> find() {
        return find((VO) null);
    }

    @Override
    public List<T> find(OrderBy orderBy) {
        return find(null, orderBy, -1, -1);
    }

    @Override
    public List<T> find(int pageSize, int pageNumber) {
        return find(null, null, pageSize, pageNumber);
    }

    @Override
    public List<T> find(OrderBy orderBy, int pageSize, int pageNumber) {
        return find(null, orderBy, pageSize, pageNumber);
    }

    @Override
    public List<T> find(VO condition) {
        return find(condition, -1, -1);
    }

    @Override
    public List<T> find(VO condition, OrderBy orderBy) {
        return find(condition, orderBy, -1, -1);
    }

    @Override
    public List<T> find(VO condition, int pageSize, int pageNumber) {
        return find(condition, null, pageSize, pageNumber);
    }

    @Override
    public List<T> find(String cols,VO condition, OrderBy orderBy, int pageSize, int pageNumber) {
    	Map<String, Object> params = Maps.newHashMap();
    	params.put("cols", cols);
    	params.put("condition", condition);
    	if (orderBy != null) {
    		params.put("orderBy", orderBy.toString());
    	}
    	if (pageSize > 0 && pageNumber > 0) {
    		int startRow = pageSize * (pageNumber - 1) + 1;
    		int endRow = pageSize * pageNumber;
    		int offset = pageSize * (pageNumber - 1);
    		
    		params.put("startRow", Integer.valueOf(startRow));
    		params.put("endRow", Integer.valueOf(endRow));
    		params.put("offset", Integer.valueOf(offset));
    		params.put("limit", pageSize);
    	}
    	
    	return getSqlSessionTemplate().<T> selectList(getNamespace() + ".find", params);
    }
    @Override
    public List<T> find(VO condition, OrderBy orderBy, int pageSize, int pageNumber) {
      
        return find(null,condition,orderBy,pageSize,pageNumber);
    }

   @Override
    public List<T> findByIdList(List<PK> idList, VO condition, OrderBy orderBy) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("idList", idList);
        params.put("condition", condition);
        if (orderBy != null) {
            params.put("orderBy", orderBy.toString());
        }

        return getSqlSessionTemplate().<T> selectList(getNamespace() + ".findByIdList", params);
    }
    @Override
    public List<T> findByIdList(List<PK> idList, VO condition) {
        return findByIdList(idList, condition, null);
    }
    @Override
    public List<T> findByIdList(List<PK> idList, OrderBy orderBy) {
        return findByIdList(idList, null, orderBy);
    }

    @Override
    public List<T> findByIdList(List<PK> idList) {
        return findByIdList(idList, null, null);
    }

    @Override
    public int count() {
        return count("*");
    }

    @Override
    public int count(String column) {
        return count(null, column);
    }

    @Override
    public int count(VO condition) {
        return count(condition, "*");
    }

    @Override
    public int count(VO condition, String column) {
        DAOUtils.checkColumn(column);

        Map<String, Object> params = Maps.newHashMap();
        params.put("condition", condition);
        params.put("count_column", column);

        return getSqlSessionTemplate().<Integer> selectOne(getNamespace() + ".count", params);
    }

    @Override
    public Map<String, Object> aggregate(String[] functions, String[] columns) {
        return aggregate(null, functions, columns);
    }

    @Override
    public Map<String, Object> aggregate(VO condition, String[] functions, String[] columns) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("condition", condition);
        params.put("aggregate_sql", DAOUtils.buildAggregateSql(functions, columns));

        Map<String, Object> result = getSqlSessionTemplate().<Map<String, Object>> selectOne(getNamespace() + ".aggregate", params);
        if (result != null) {
            Map<String, Object> processedResult = Maps.newHashMapWithExpectedSize(result.size());
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                processedResult.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            return processedResult;
        } else {
            return result;
        }
    }

    @Override
    public int update(T entity, VO condition) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("entity", entity);
        params.put("condition", condition);

        return getSqlSessionTemplate().update(getNamespace() + ".update", params);
    }

    @Override
    public int update(T entity, PK id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("entity", entity);
        params.put("id", id);

        return getSqlSessionTemplate().update(getNamespace() + ".updateById", params);
    }

    @Override
    public int updateByIdList(T entity, List<PK> idList, VO condition) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("entity", entity);
        params.put("idList", idList);
        params.put("condition", condition);

        return getSqlSessionTemplate().update(getNamespace() + ".updateByIdList", params);
    }

    @Override
    public int updateByIdList(T entity, List<PK> idList) {
        return updateByIdList(entity, idList, null);
    }

    @Override
    public int update(Map<String, Object> entity, VO condition) {
        Map<String, Object> params = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : entity.entrySet()) {
            params.put("entity_" +  entry.getKey(), entry.getValue());
        }
        params.put("condition", condition);

        return getSqlSessionTemplate().update(getNamespace() + ".forceUpdate", params);
    }

    @Override
    public int update(Map<String, Object> entity, PK id) {
        Map<String, Object> params = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : entity.entrySet()) {
            params.put("entity_" +  entry.getKey(), entry.getValue());
        }
        params.put("id", id);

        return getSqlSessionTemplate().update(getNamespace() + ".forceUpdateById", params);
    }

    @Override
    public int updateByIdList(Map<String, Object> entity, List<PK> idList, VO condition) {
        Map<String, Object> params = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : entity.entrySet()) {
            params.put("entity_" +  entry.getKey(), entry.getValue());
        }
        params.put("idList", idList);
        params.put("condition", condition);

        return getSqlSessionTemplate().update(getNamespace() + ".forceUpdateByIdList", params);
    }

    @Override
    public int updateByIdList(Map<String, Object> entity, List<PK> idList) {
        return updateByIdList(entity, idList, null);
    }

    @Override
    public int remove(PK id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", id);
        return getSqlSessionTemplate().delete(getNamespace() + ".removeById", params);
    }
    @Override
    public int getTimeForInt(){
    	return getSqlSessionTemplate().selectOne("com.tenzhao.common.dao.AbstractDAO.getTimeForInt");
    }
    @Override
    public long getTimeForLong(){
    	return getSqlSessionTemplate().selectOne("com.tenzhao.common.dao.AbstractDAO.getTimeForLong");
    }
    @Override
    public  int updateAddEquals(Map<String, Object> entity, PK id){
    	 Map<String, Object> params = Maps.newHashMap();
         for (Map.Entry<String, Object> entry : entity.entrySet()) {
             params.put("entity_" +  entry.getKey(), entry.getValue());
         }
         params.put("id", id);
    	return getSqlSessionTemplate().update(getNamespace()+".updateAddEqualsById",params);
    }
    @Override
    public int updateAddEquals(Map<String, Object> entity, VO condition){
    	 Map<String, Object> params = Maps.newHashMap();
         for (Map.Entry<String, Object> entry : entity.entrySet()) {
             params.put("entity_" +  entry.getKey(), entry.getValue());
         }
         params.put("condition", condition);
    	return getSqlSessionTemplate().update(getNamespace()+".updateAddEquals",params);
    }
    @Override
    public int updateAddEquals(Map<String, Object> entity, List<PK> idList, VO condition){
     	 Map<String, Object> params = Maps.newHashMap();
         for (Map.Entry<String, Object> entry : entity.entrySet()) {
             params.put("entity_" +  entry.getKey(), entry.getValue());
         }
         params.put("idList", idList);
         params.put("condition", condition);
    	return getSqlSessionTemplate().update(getNamespace()+".updateAddEqualsByIdList",params);
    }
    @Override
    public List<T> findByCondition(Condition condition){
    	if(Objects.isNull(condition)) {
    		throw new IllegalArgumentException("Condition不能为空");
    	}
    	Map<String, Object> params = Maps.newHashMap();
    	params.put("condition", condition);
    	return getSqlSessionTemplate().selectList(getNamespace()+".findByCondition",params);
    }
    @Override
    public int findByConditionCount(Condition condition){
    	if(Objects.isNull(condition)) {
    		throw new IllegalArgumentException("Condition不能为空");
    	}
    	Map<String, Object> params = Maps.newHashMap();
    	params.put("condition", condition);
    	params.put("count_column", StringUtils.isNotBlank(condition.getSelectClause())?condition.getSelectClause():"*");
    	return getSqlSessionTemplate().selectOne(getNamespace()+".findByConditionCount",params);
    }
    
    /**
     * 条件删除
     * @param condition
     * @return
     */
    @Override
    public int remove(Condition condition) {
    	if(Objects.isNull(condition)) {
    		throw new IllegalArgumentException("Condition不能为空");
    	}
    	Map<String,Object> params = new HashMap<String,Object>(1);
    	params.put("condition", condition);
    	return getSqlSessionTemplate().delete(getNamespace() + ".removeByCondition",params);
    }
    
    /**
     * 更新非空字段
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public int update(T entity,Condition condition) {
    	if(Objects.isNull(condition)) {
    		throw new IllegalArgumentException("Condition不能为空");
    	}
    	Map<String,Object> params = new HashMap<String,Object>(2);
    	params.put("entity", entity);
    	params.put("condition", condition);
    	return getSqlSessionTemplate().update(getNamespace() + ".updateByCondition",params);
    }
    
    /**
     * 强制更新
     * @param updateEntries
     * @param condition
     * @return
     */
    @Override
    public int update(List<UpdateEntry> updateEntries,Condition condition) {
    	if(Objects.isNull(condition)) {
    		throw new IllegalArgumentException("Condition不能为空");
    	}
    	Map<String,Object> params = new HashMap<String,Object>(2);
    	params.put("entries", updateEntries);
    	params.put("condition", condition);
    	return getSqlSessionTemplate().update(getNamespace() + ".forceUpdateByCondition",params);
    }
    
    @Override
    public int update(List<UpdateEntry> updateEntries,PK id) {
    	Map<String,Object> params = new HashMap<String,Object>(2);
    	params.put("entries", updateEntries);
    	params.put("id", id);
    	return getSqlSessionTemplate().update(getNamespace() + ".updateEntriesById",params);
    }
}

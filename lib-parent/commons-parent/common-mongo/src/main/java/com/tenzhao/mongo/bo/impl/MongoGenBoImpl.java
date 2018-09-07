package com.tenzhao.mongo.bo.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import com.mongodb.WriteResult;
import com.tenzhao.mongo.bo.MongoGenBo;
import com.tenzhao.mongo.dao.MongoGenDao;

public abstract class MongoGenBoImpl<T> implements MongoGenBo<T> {
    
	protected Class<T> entityClass ;
	
	protected abstract MongoGenDao<T> getDao();
	
	@Override
	public void save(T t) {
		getDao().save(t);
	}
	
	@Override
	public List<T> find(Query query){		
		return getDao().find(query, entityClass);
	}
	
	@Override
	public T findOne(Query query){
		return getDao().findOne(query,entityClass);
	}
	@Override
	public List<T> findAll() {
		return getDao().findAll(entityClass);
	}
	
	@Override
	public Long count() {
		
		return this.count(null);
	}
	@Override
	public Long count(Query query) {
		
		return getDao().count(query,entityClass);
	}
	
	@Override
	public T findById(Object id ){
		return getDao().findById(id, entityClass);
	}
	
	@Override
	public WriteResult remove(Query query ){
		return getDao().remove(query,entityClass);
	}
	
	@Override
	public void save(List<T> list){
	    if(!CollectionUtils.isEmpty(list)){
	        for(T t : list){
	            getDao().save(t);
	        }
	    }	    
	}
	
	@Override
	public void save(T t, String collectionname){
	    getDao().save(t, collectionname);
	}
	
	@Override
	public void save(List<T> list,  String collectionname){
	    if(!CollectionUtils.isEmpty(list)){
            for(T t : list){                
                getDao().save(t, collectionname);
            }
        }   
	}
	
	@Override
	public List<T> findAll(String collectionname){
	    return getDao().findAll(entityClass, collectionname);	    
	}
	
	@Override
	public List<T> find(Query query, String collectionname){
	    return getDao().find(query, entityClass, collectionname);
	}
	
	@Override
	public List<T> find(String collectionname, Sort sort, int skip, int limit){
	    return find(new Query(), sort ,collectionname, skip, limit);
	}
	
	@Override
	public List<T> find(Query query, Sort sort, String collectionname, int skip, int limit){ 
	    if(skip > 1){
	        query.skip(skip);
	    }
	    if(limit > 0){
	        query.limit(limit);	
	    }	
        if(null != sort){
            query.with(sort);
        }
	    return find(query, collectionname);
	}
	
	@Override
	public void upsert(Query query, Update update, String collectionname){
	    getDao().upsert(query, update, collectionname);
	}
	
	@Override
	public GroupByResults<T> group(String collectionname, GroupBy groupBy){
          return getDao().group(collectionname, groupBy, entityClass);
	}
	
	@Override
	public GroupByResults<T> group(Criteria criteria, String collectionname, GroupBy groupBy){
	    return getDao().group(criteria, collectionname, groupBy, entityClass);
	}
	
	@Override
	public MapReduceResults<T> mapReduce(String collectionname, String mapFunction, String reduceFunction){
	     return getDao().mapReduce(collectionname, mapFunction, reduceFunction, entityClass);
	 }
	
	@Override
	public MapReduceResults<T> mapReduce(Query query, String collectionname, String mapFunction, String reduceFunction){
	    return getDao().mapReduce(query, collectionname, mapFunction, reduceFunction, entityClass);
	}
	
	@Override
	public AggregationResults<T> aggregate(TypedAggregation<?> aggregation, String collectionname){
	    return getDao().aggregate(aggregation, collectionname, entityClass);
	}
	
	@Override
	public <O> AggregationResults<O> aggregate(Aggregation aggregation, Class<O> outputType){
	    return getDao().aggregate(aggregation, entityClass, outputType);
	}
	
	@Override
	public <O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionname, Class<O> outputType){
	    return getDao().aggregate(aggregation, collectionname, outputType);
	}
}

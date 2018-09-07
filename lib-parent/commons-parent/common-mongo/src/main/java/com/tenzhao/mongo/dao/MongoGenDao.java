package com.tenzhao.mongo.dao;

import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public interface MongoGenDao<T> {
	void save(T t);
	
	void save(T t, String collectionname);

	List<T> find(Query query, Class<T> clazz);

	T findById(Object id, Class<T> clazz);

	WriteResult remove(Query query, Class<T> clazz);

	T findOne(Query query, Class<T> entityClass);

	List<T> findAll(Class<T> entityClass);

	Long count(Class<T> entityClass);

	Long count(Query query, Class<T> entityClass);
	
	Long count(Query query, String collectionname);
	
	List<T> findAll(Class<T> entityClass, String collectionname);
	
	List<T> find(Query query, Class<T> entityClass, String collectionname);
	
	WriteResult upsert(Query query, Update update, String collectionname);	
	
	GroupByResults<T> group(String inputCollectionName, GroupBy groupBy, Class<T> entityClass);
	
	GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy, Class<T> entityClass);
	
	MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass);
	
	MapReduceResults<T> mapReduce(Query query, String inputCollectionsName, String mapFunction, String reduceFunction, Class<T> entityClass);
	
	AggregationResults<T> aggregate(TypedAggregation<?> aggregation, String collectionName, Class<T> outputType);
	
	<O> AggregationResults<O> aggregate(Aggregation aggregation, Class<T> inputType, Class<O> outputType);
	
	<O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionname, Class<O> outputType);
}

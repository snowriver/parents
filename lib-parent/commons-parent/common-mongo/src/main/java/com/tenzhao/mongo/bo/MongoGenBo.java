package com.tenzhao.mongo.bo;

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

import com.mongodb.WriteResult;

public interface MongoGenBo<T> {
	void save(T t);
	
	void save(T t, String collectioname);
	
	List<T> find(Query query);

	T findOne(Query query);
	
	T findById(Object id);
	
	List<T> findAll();

	Long count();

	Long count(Query query);
	
	WriteResult remove(Query query);

	void save(List<T> list);
	
	void save(List<T> list, String collectioname);
	
	List<T> findAll(String collectionname);
	
	List<T> find(Query query, String collectionname);
	
	List<T> find(String collectionname, Sort sort,int skip, int limit);
	
	List<T> find(Query query, Sort sort, String collectionname, int skip, int limit);
	
	void upsert(Query query, Update update, String collectionname);
	
	GroupByResults<T> group(String collectionname, GroupBy groupBy);
	
	GroupByResults<T> group(Criteria criteria, String collectionname, GroupBy groupBy);	
	
	MapReduceResults<T> mapReduce(String collectionname, String mapFunction, String reduceFunction);
	
	MapReduceResults<T> mapReduce(Query query, String collectionname, String mapFunction, String reduceFunction);
	
	AggregationResults<T> aggregate(TypedAggregation<?> aggregation, String collectionname);
	
	<O> AggregationResults<O> aggregate(Aggregation aggregation, Class<O> outputType);
	
	<O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionname, Class<O> outputType);
}

package com.tenzhao.mongo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import com.tenzhao.mongo.dao.MongoGenDao;

public abstract class MongoGenDaoImpl<T> implements MongoGenDao<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public void save(T t) {
        mongoTemplate.save(t);
    }

    @Override
    public List<T> find(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    @Override
    public T findOne(Query query, Class<T> entityClass) {
        return mongoTemplate.findOne(query, entityClass);
    }

    @Override
    public List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    @Override
    public Long count(Class<T> entityClass) {
        return count(null, entityClass);
    }

    @Override
    public Long count(Query query, Class<T> entityClass) {
        return mongoTemplate.count(query, entityClass);
    }

    @Override
    public Long count(Query query, String collectionname) {
        return mongoTemplate.count(query, collectionname);
    }

    @Override
    public T findById(Object id, Class<T> clazz) {
        return mongoTemplate.findById(id, clazz);
    }

    @Override
    public WriteResult remove(Query query, Class<T> clazz) {
        return mongoTemplate.remove(query, clazz);
    }

    @Override
    public void save(T t, String collectionname) {
        mongoTemplate.save(t, collectionname);
    }

    @Override
    public List<T> findAll(Class<T> clazz, String collectionname) {
        return mongoTemplate.findAll(clazz, collectionname);
    }

    @Override
    public List<T> find(Query query, Class<T> clazz, String collectionname) {
        return mongoTemplate.find(query, clazz, collectionname);
    }

    @Override
    public WriteResult upsert(Query query, Update update, String collectionname) {
        return mongoTemplate.upsert(query, update, collectionname);
    }

    @Override
    public GroupByResults<T> group(String inputCollectionName, GroupBy groupBy, Class<T> entityClass){
       return mongoTemplate.group(inputCollectionName, groupBy, entityClass);
    }

    @Override
    public GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy, Class<T> entityClass){
        return mongoTemplate.group(criteria, inputCollectionName, groupBy, entityClass);
    }
    
    @Override
    public MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction,String reduceFunction,Class<T> entityClass){
        return mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, entityClass);
    }
    
    @Override
    public MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass){
        return mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, entityClass);
    }
    
    @Override
    public AggregationResults<T> aggregate(TypedAggregation<?> aggregation, String collectionName, Class<T> outputType){        
        return mongoTemplate.aggregate(aggregation, collectionName, outputType);
    }
    
    @Override
    public <O> AggregationResults<O> aggregate(Aggregation aggregation, Class<T> inputType, Class<O> outputType){
        return mongoTemplate.aggregate(aggregation, inputType, outputType);
    }
    
    public <O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionname, Class<O> outputType){
        return mongoTemplate.aggregate(aggregation, collectionname, outputType);
    }
}

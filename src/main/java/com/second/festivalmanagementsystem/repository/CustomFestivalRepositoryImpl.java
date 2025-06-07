package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Festival;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public class CustomFestivalRepositoryImpl implements CustomFestivalRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Festival> findFestivalsByQuery(Query query) {
        return mongoTemplate.find(query, Festival.class);
    }
}

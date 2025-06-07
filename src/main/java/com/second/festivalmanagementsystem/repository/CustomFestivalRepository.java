package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Festival;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface CustomFestivalRepository {
    public List<Festival> findFestivalsByQuery(Query query);

}

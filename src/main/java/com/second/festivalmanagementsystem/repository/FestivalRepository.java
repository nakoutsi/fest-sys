package com.second.festivalmanagementsystem.repository;


import com.second.festivalmanagementsystem.model.Festival;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends MongoRepository<Festival, String>,CustomFestivalRepository {
    // Find festivals by name containing a keyword (case-insensitive)
    List<Festival> findByNameContainingIgnoreCase(String name);

    List<Festival> findByState(String state);
    Optional<Festival> findByName(String name);


}

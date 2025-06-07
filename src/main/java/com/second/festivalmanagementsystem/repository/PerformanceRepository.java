package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Performance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PerformanceRepository extends MongoRepository<Performance, String> {
    // Find performances by festival ID
    List<Performance> findByFestivalId(String festivalId);

    // Find performances by state
    List<Performance> findByState(String state);

    // Find performances by name containing a keyword
    List<Performance> findByNameContainingIgnoreCase(String name);

    // Find performances by genre
    List<Performance> findByGenre(String genre);

    // Find performances by festival ID and state
    List<Performance> findByFestivalIdAndState(String festivalId, String state);

    @Query("{ $and: [ { 'genre': ?0 }, { 'festivalId': ?1 } ] }")
    List<Performance> findByCriteria(String genre, String festivalId);


}

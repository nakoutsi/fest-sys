package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, String> {
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

    @Query("SELECT p FROM Performance p WHERE p.genre = ?1 AND p.festivalId = ?2")
    List<Performance> findByCriteria(String genre, String festivalId);


}

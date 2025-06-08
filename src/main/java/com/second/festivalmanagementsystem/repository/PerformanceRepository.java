package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Performance;
import com.second.festivalmanagementsystem.enums.PerformanceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, String> {
    // Find performances by festival ID
    List<Performance> findByFestival_Id(String festivalId);

    // Find performances by state
    List<Performance> findByState(PerformanceState state);

    // Find performances by name containing a keyword
    List<Performance> findByNameContainingIgnoreCase(String name);

    // Find performances by genre
    List<Performance> findByGenre(String genre);

    // Find performances by festival ID and state
    List<Performance> findByFestival_IdAndState(String festivalId, PerformanceState state);

    Optional<Performance> findByNameAndFestival_Id(String name, String festivalId);

    @Query("SELECT p FROM Performance p WHERE p.genre = ?1 AND p.festival.id = ?2")
    List<Performance> findByCriteria(String genre, String festivalId);


}

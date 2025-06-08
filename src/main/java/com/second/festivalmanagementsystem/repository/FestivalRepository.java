package com.second.festivalmanagementsystem.repository;


import com.second.festivalmanagementsystem.model.Festival;
import com.second.festivalmanagementsystem.enums.FestivalState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival, String>, CustomFestivalRepository {
    // Find festivals by name containing a keyword (case-insensitive)
    List<Festival> findByNameContainingIgnoreCase(String name);

    List<Festival> findByState(FestivalState state);
    Optional<Festival> findByName(String name);


}

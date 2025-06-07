package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Festival;

import java.util.Date;

import java.util.List;

public interface CustomFestivalRepository {
    List<Festival> findFestivalsByFilters(String name, String description, Date startDate, Date endDate, String venue);

}

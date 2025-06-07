package com.second.festivalmanagementsystem.service;


import com.second.festivalmanagementsystem.enums.PerformanceState;
import com.second.festivalmanagementsystem.exceptions.FestivalException;
import com.second.festivalmanagementsystem.exceptions.PerformanceException;
import com.second.festivalmanagementsystem.exceptions.UserException;
import com.second.festivalmanagementsystem.model.Festival;
import com.second.festivalmanagementsystem.model.Performance;
import com.second.festivalmanagementsystem.model.User;
import com.second.festivalmanagementsystem.repository.FestivalRepository;
import com.second.festivalmanagementsystem.repository.PerformanceRepository;
import com.second.festivalmanagementsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FestivalRepository festivalRepository;

    public Performance createPerformance(Performance performance, User loggedUser, String festivalId) throws FestivalException {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new FestivalException("Festival not found."));

        if (festival.getStaff().contains(loggedUser) || festival.getOrganizers().contains(loggedUser)) {
            throw new FestivalException("User cannot perform this performance as he is organizer or staff.");
        }

        festival.getPerformances().add(performance);
        performance.setFestival(festival);

        loggedUser.getMain_artist_in().add(festivalId);
        performance.setMain_artist(loggedUser);

        festivalRepository.save(festival);
        userRepository.save(loggedUser);
        return performanceRepository.save(performance);
    }

    public Performance updatePerformance(String id, Performance updatedPerformance) {
        Performance existingPerformance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found."));
        existingPerformance.setDescription(updatedPerformance.getDescription());
        existingPerformance.setGenre(updatedPerformance.getGenre());
        existingPerformance.setDuration(updatedPerformance.getDuration());
        return performanceRepository.save(existingPerformance);
    }

    public List<Performance> getPerformancesByFestival(String festivalId) {
        return performanceRepository.findByFestival_Id(festivalId);
    }

    public void deletePerformance(String id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found."));
        if (!PerformanceState.CREATED.equals(performance.getState())) {
            throw new IllegalStateException("Only performances in CREATED state can be deleted.");
        }
        performanceRepository.deleteById(id);
    }
    public List<Performance> searchPerformances(String genre, String festivalId) {
        return performanceRepository.findByCriteria(genre, festivalId);
    }

    public Performance submitPerformance(String id, User loggedUser) throws FestivalException {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found"));

        if (performance.getState() != PerformanceState.CREATED) {
            throw new FestivalException("Performance is not created.");
        }

        if (!performance.getMain_artist().equals(loggedUser) && !performance.getArtists().contains(loggedUser)) {
            throw new FestivalException("User is not artist in this performance");
        }

        performance.setState(PerformanceState.SUBMITTED);
        return performanceRepository.save(performance);
    }

    public Performance addArtist(String id, User loggedUser, String artist) throws PerformanceException {
        Optional<User> artistUser = Optional.empty();

        artistUser = userRepository.findByUsername(artist);
        if (!artistUser.isPresent()) {
            throw new PerformanceException("Artist username does not exist");
        }


        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found"));
        if (performance.getState() != PerformanceState.CREATED) {
            throw new PerformanceException("Performance is not created.");
        }
        if (!performance.getMain_artist().equals(loggedUser) || !performance.getArtists().contains(loggedUser)) {
            throw new PerformanceException("User is not artist in this performance");
        }

        artistUser.get().getArtist_in().add(performance.getId());
        performance.getArtists().add(artistUser.get());
        userRepository.save(artistUser.get());
        return performanceRepository.save(performance);
    }
}

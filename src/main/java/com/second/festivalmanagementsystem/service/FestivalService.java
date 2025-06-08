package com.second.festivalmanagementsystem.service;

import com.second.festivalmanagementsystem.enums.FestivalState;
import com.second.festivalmanagementsystem.exceptions.FestivalException;
import com.second.festivalmanagementsystem.model.Festival;
import com.second.festivalmanagementsystem.model.User;
import com.second.festivalmanagementsystem.repository.CustomFestivalRepository;

import com.second.festivalmanagementsystem.repository.FestivalRepository;
import com.second.festivalmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private UserRepository userRepository;


    public Festival createFestival(Festival festival, User loggeduser) throws FestivalException {

        if (festival.getName() == null || festival.getName().isEmpty()) {
            throw new FestivalException("Festival name is required.");
        }
        Optional<Festival> foundfestival = festivalRepository.findByName(festival.getName());
        if (foundfestival.isPresent()) {
            throw new FestivalException("Festival name already exists.");
        }

        festival.getOrganizers().add(loggeduser);
        Festival saved = festivalRepository.save(festival);
        loggeduser.getOrganizer_in().add(saved.getId());
        userRepository.save(loggeduser);
        return saved;

    }

    public Festival updateFestival(String id, Festival updatedFestival, User loggedUser) throws FestivalException {
        Festival existingFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (!existingFestival.getOrganizers().contains(loggedUser)){
            throw new FestivalException("User is not organizer in this Festival");
        }

        if (existingFestival.getState()==FestivalState.ANNOUNCED)
            throw new FestivalException("Festival is not in announcement state");

        if (updatedFestival.getName() != null)
           existingFestival.setName(updatedFestival.getName());
        if (updatedFestival.getDescription() != null)
            existingFestival.setDescription(updatedFestival.getDescription());
        if (updatedFestival.getStartDate() != null)
            existingFestival.setStartDate(updatedFestival.getStartDate());
        if (updatedFestival.getEndDate() != null)
            existingFestival.setEndDate(updatedFestival.getEndDate());
        return festivalRepository.save(existingFestival);
    }

    public List<Festival> getAllFestivals() {
        return festivalRepository.findAll();
    }

    public void deleteFestival(String id, User loggedUser) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (!festival.getOrganizers().contains(loggedUser)){
            throw new FestivalException("User is not organizer in this Festival");
        }
        if ((FestivalState.CREATED!=festival.getState())) {
            throw new FestivalException("Only festivals in CREATED state can be deleted.");
        }
        festivalRepository.deleteById(id);
    }
    public Optional<Festival> getFestivalById(String id) {
        return festivalRepository.findById(id);
    }

    public void submitFestival(String id) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.CREATED) {
            throw new FestivalException("Only festivals in CREATED state can be submitted.");
        }else{
            festival.setState(FestivalState.SUBMISSION);
            festivalRepository.save(festival);
        }
    }

    public void assignFestival(String id) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.SUBMISSION) {
            throw new FestivalException("Only festivals in SUBMITTED state can be assigned.");
        }else{
            festival.setState(FestivalState.ASSIGNMENT);
            festivalRepository.save(festival);
        }
    }

    public void reviewFestival(String id) throws FestivalException  {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.ASSIGNMENT) {
            throw new FestivalException("Only festivals in ASSIGNMENT state can be reviewed.");
        }else{
            festival.setState(FestivalState.REVIEW);
            festivalRepository.save(festival);
        }
    }

    public void scheduleFestival(String id)throws FestivalException  {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.REVIEW) {
            throw new FestivalException("Only festivals in REVIEW state can be scheduled.");
        }else{
            festival.setState(FestivalState.SCHEDULING);
            festivalRepository.save(festival);
        }
    }

    public void finalFestival(String id) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.SCHEDULING) {
            throw new FestivalException("Only festivals in SCHEDULING state can move to FINAL_SUBMISSION.");
        } else {
            festival.setState(FestivalState.FINAL_SUBMISSION);
            festivalRepository.save(festival);
        }
    }

    public void decisionFestival(String id) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.FINAL_SUBMISSION) {
            throw new FestivalException("Only festivals in FINAL_SUBMISSION state can move to DECISION.");
        } else {
            festival.setState(FestivalState.DECISION);
            festivalRepository.save(festival);
        }
    }

    public void announcedFestival(String id) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));
        if (festival.getState() != FestivalState.DECISION) {
            throw new FestivalException("Only festivals in DECISION state can be announced.");
        } else {
            festival.setState(FestivalState.ANNOUNCED);
            festivalRepository.save(festival);
        }
    }

    @Transactional
    public String addOrganizers(String id, User loggedUser, ArrayList<String> organizers) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));

        List<User> usersToSave = new ArrayList<>();

        for (String organizer : organizers) {
            Optional<User> optionalUser = userRepository.findByUsername(organizer);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                festival.getOrganizers().add(user);
                user.getOrganizer_in().add(festival.getId());
                usersToSave.add(user);
            }
            else{
                throw new FestivalException("User " + organizer + " does not exist.");
            }
        }

        festivalRepository.save(festival);
        userRepository.saveAll(usersToSave);

        return "All users were added as organizers!";
    }

    @Transactional
    public String addStaff(String id, User loggedUser, ArrayList<String> staff) throws FestivalException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalException("Festival not found."));

        List<User> usersToSave = new ArrayList<>();

        for (String staffboy : staff) {
            Optional<User> optionalUser = userRepository.findByUsername(staffboy);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                festival.getStaff().add(user);
                user.getStaff_in().add(festival.getId());
                usersToSave.add(user);
            }
            else{
                throw new FestivalException("User " + staffboy + " does not exist.");
            }
        }

        festivalRepository.save(festival);
        userRepository.saveAll(usersToSave);

        return "All users were added as staff!";
    }

    // Filter festivals using optional criteria
    public List<Festival> findFestivalsByFilters(String name, String description, Date startDate, Date endDate, String venue) {
        return festivalRepository.findFestivalsByFilters(name, description, startDate, endDate, venue);
    }
}

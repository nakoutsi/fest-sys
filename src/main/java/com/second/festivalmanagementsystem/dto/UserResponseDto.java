package com.second.festivalmanagementsystem.dto;

import com.second.festivalmanagementsystem.model.User;
import java.util.Set;

public record UserResponseDto(
        String id,
        String username,
        String fullName,
        Set<String> main_artist_in,
        Set<String> artist_in,
        Set<String> stage_manager,
        Set<String> organizer_in,
        Set<String> staff_in) {
    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getMain_artist_in(),
                user.getArtist_in(),
                user.getStage_manager(),
                user.getOrganizer_in(),
                user.getStaff_in());
    }
}

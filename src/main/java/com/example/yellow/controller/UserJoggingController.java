package com.example.yellow.controller;

import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.UserModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.service.UserJoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserJoggingController {

    @Autowired
    private UserJoggingService userJoggingService;

    @PostMapping
    public void createUserJogging(Authentication authentication, @RequestBody JoggingModel jogging) {

        userJoggingService.createJogging(authentication.getName(), jogging);
    }

    @GetMapping
    public Iterable<JoggingModel> getUserJoggings(Authentication authentication) {

        return userJoggingService.getUserJoggings(authentication.getName());
    }

    @GetMapping("/statistics")
    public Iterable<WeekStatistics> getUserWeekStatistics(Authentication authentication) {

        return userJoggingService.getWeekStatistics(authentication.getName());
    }

    @PutMapping("/jogging")
    public void updateUserJogging(Authentication authentication, @RequestBody JoggingModel jogging) {

        userJoggingService.updateUserJogging(authentication.getName(), jogging);
    }

    @DeleteMapping("/jogging")
    public void deleteUserJogging(Authentication authentication, @RequestBody JoggingModel jogging) {
        userJoggingService.deleteJogging(authentication.getName(), jogging.getId());
    }

    /*@GetMapping("/j")
    public Iterable<JoggingModel> getEnt(Authentication authentication){
        return userJoggingService.getAll();
    }

    @GetMapping("/u")
    public Iterable<UserModel> getU(Authentication authentication){
        return userJoggingService.getU();
    }*/
}
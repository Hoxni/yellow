package com.example.yellow.controller;

import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.UserModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.service.UserJoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

@RestController
@RequestMapping("/api/v1")
public class UserJoggingController {

    @Autowired
    private UserJoggingService userJoggingService;

    @PostMapping("/joggings")
    public void createJogging(@RequestParam Long userId, @RequestBody JoggingModel jogging) {

        userJoggingService.createJogging(userId, jogging);
    }

    @GetMapping("/joggings")
    public Iterable<JoggingModel> getUserJoggings(
            @RequestParam(required = false) Long userId,
            @RequestParam int page,
            @RequestParam(defaultValue = "10") @Max(100) int size) {
        return userJoggingService.getUserJoggings(userId, page, size);
    }

    @GetMapping("/joggings/{joggingId}")
    public JoggingModel getJogging(@PathVariable Long joggingId){
        return userJoggingService.getJogging(joggingId);
    }

    /*@GetMapping("/statistics")
    public Iterable<WeekStatistics> getUserWeekStatistics(Authentication authentication) {

        return userJoggingService.getWeekStatistics(authentication.getName());
    }*/

    @PutMapping("/joggings")
    public void updateJogging(@RequestBody JoggingModel jogging) {

        userJoggingService.updateJogging(jogging);
    }

    @DeleteMapping("/joggings/{joggingId}")
    public void deleteJogging(@PathVariable Long joggingId) {
        userJoggingService.deleteJogging(joggingId);
    }

    @GetMapping("/users/{userId}")
    public void getUser(@PathVariable Long userId){
        userJoggingService.getUser(userId);
    }

    /*@GetMapping("/joggings")
    public Iterable<JoggingModel> getEnt(Authentication authentication){
        return userJoggingService.getAll();
    }*/

    /*
    @GetMapping("/u")
    public Iterable<UserModel> getU(Authentication authentication){
        return userJoggingService.getU();
    }*/
}
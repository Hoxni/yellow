package com.example.yellow.controller;

import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.service.JoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
public class JoggingController {

    @Autowired
    private JoggingService joggingService;

    @PostMapping("/joggings")
    public void createJogging(@RequestParam Long userId, @RequestBody JoggingModel jogging) {
        joggingService.createJogging(userId, jogging);
    }

    @GetMapping("/joggings")
    public Page<JoggingModel> getUserJoggings(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size) {
        return joggingService.getUserJoggings(userId, page, size);
    }

    @GetMapping("/joggings/{joggingId}")
    public JoggingModel getJogging(@PathVariable Long joggingId){
        return joggingService.getJogging(joggingId);
    }

    @PutMapping("/joggings")
    public void updateJogging(@RequestBody JoggingModel jogging) {
        joggingService.updateJogging(jogging);
    }

    @DeleteMapping("/joggings/{joggingId}")
    public void deleteJogging(@PathVariable Long joggingId) {
        joggingService.deleteJogging(joggingId);
    }

    @GetMapping("/statistics")
    public Page<WeekStatistics> getWeekStatistics(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size){
        return joggingService.getWeekStatistics(userId, page, size);
    }
}
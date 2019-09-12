package com.example.yellow.controller;

import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.service.JoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JoggingController {

    @Autowired
    private JoggingService joggingService;

    @PostMapping("/joggings")
    @PreAuthorize("hasPermission(#userId, 'create')")
    public void createJogging(@RequestParam Long userId, @RequestBody JoggingModel jogging) {
        joggingService.createJogging(userId, jogging);
    }

    @GetMapping("/joggings")
    @PreAuthorize("hasPermission(#userId, 'read')")
    public List<JoggingModel> getUserJoggings(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size) {
        return joggingService.getUserJoggings(userId, page, size).getContent();
    }

    @GetMapping("/joggings/{joggingId}")
    @PreAuthorize("hasPermission(#joggingId, 'get_jogging')")
    public JoggingModel getJogging(@PathVariable Long joggingId){
        return joggingService.getJogging(joggingId);
    }

    @PutMapping("/joggings")
    @PreAuthorize("hasPermission(#jogging.id, 'read')")
    public void updateJogging(@RequestBody JoggingModel jogging) {
        joggingService.updateJogging(jogging);
    }

    @DeleteMapping("/joggings/{joggingId}")
    @PreAuthorize("hasPermission(#joggingId, 'delete')")
    public void deleteJogging(@PathVariable Long joggingId) {
        joggingService.deleteJogging(joggingId);
    }

    @GetMapping("/joggings/report")
    @PreAuthorize("hasPermission(#userId, 'report')")
    public List<WeekStatistics> getWeekStatistics(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size){
        return joggingService.getWeekStatistics(userId, page, size).getContent();
    }

}
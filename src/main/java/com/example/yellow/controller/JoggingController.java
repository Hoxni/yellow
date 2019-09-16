package com.example.yellow.controller;

import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.service.JoggingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JoggingController {

    private final JoggingService joggingService;

    public JoggingController(JoggingService joggingService) {
        this.joggingService = joggingService;
    }

    @PostMapping("/joggings")
    @PreAuthorize("@securityService.isOwner(#userId)")
    public void createJogging(@RequestParam Long userId, @RequestBody JoggingModel jogging) {
        joggingService.createJogging(userId, jogging);
    }

    @GetMapping("/joggings")
    @PreAuthorize("@securityService.isOwner(#userId) || @securityService.isAdmin()")
    public List<JoggingModel> getUserJoggings(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size) {
        return joggingService.getUserJoggings(userId, page, size).getContent();
    }

    @GetMapping("/joggings/{joggingId}")
    @PreAuthorize("@securityService.isAdmin()")
    public JoggingModel getJogging(@PathVariable Long joggingId){
        return joggingService.getJogging(joggingId);
    }

    @PutMapping("/joggings/{joggingId}")
    @PreAuthorize("@joggingService.isOwnerOfJogging(authentication.principal['id'], #joggingId)")
    public void updateJogging(@PathVariable Long joggingId, @RequestBody JoggingModel jogging) {
        joggingService.updateJogging(joggingId, jogging);
    }

    @DeleteMapping("/joggings/{joggingId}")
    @PreAuthorize("@joggingService.isOwnerOfJogging(authentication.principal['id'], #joggingId)")
    public void deleteJogging(@PathVariable Long joggingId) {
        joggingService.deleteJogging(joggingId);
    }

    @GetMapping("/joggings/report")
    @PreAuthorize("@securityService.isOwner(#userId) || @securityService.isAdmin()")
    public List<WeekStatistics> getWeekStatistics(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size){
        return joggingService.getWeekStatistics(userId, page, size).getContent();
    }

}
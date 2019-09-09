package com.example.yellow.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WeekStatistics {

    private LocalDate startDate;
    private LocalDate endDate;

    private Double averageSpeed;
    private Double averageTime;
    private Long totalDistance;

    public WeekStatistics(LocalDateTime startDate, LocalDateTime endDate, Double averageSpeed, Double averageTime, Long totalDistance) {
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.averageSpeed = averageSpeed;
        this.averageTime = averageTime;
        this.totalDistance = totalDistance;
    }
}

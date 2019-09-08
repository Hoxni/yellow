package com.example.yellow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WeekStatistics {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long averageSpeed;
    private Long averageTime;
    private Long totalDistance;

}

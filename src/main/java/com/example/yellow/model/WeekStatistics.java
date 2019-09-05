package com.example.yellow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeekStatistics {

    private LocalDate startDate;
    private LocalDate endDate;

    private double averageSpeed;
    private double averageTime;
    private int totalDistance;
}

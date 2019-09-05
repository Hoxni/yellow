package com.example.yellow.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JoggingModel {

    private Long id;
    private int distance;
    private int time;
    private LocalDateTime dateTime;
    private Long userId;

}

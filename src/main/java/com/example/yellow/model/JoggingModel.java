package com.example.yellow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoggingModel {

    private Long id;
    private int distance;
    private int time;
    private LocalDateTime dateTime;

}

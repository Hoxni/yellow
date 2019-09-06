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
    private Long distance;
    private Long time;
    private LocalDateTime dateTime;

}

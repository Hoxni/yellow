package com.example.yellow.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorInfo {

    private String message;

    private Integer code;

    private String url;

}

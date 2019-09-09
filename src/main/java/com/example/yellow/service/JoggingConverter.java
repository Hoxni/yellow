package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.model.JoggingModel;

public class JoggingConverter {

    public static JoggingEntity updateEntity(JoggingEntity updatable, JoggingModel newData){

        updatable.setDistance(newData.getDistance());
        updatable.setTime(newData.getTime());
        updatable.setDateTime(newData.getDateTime());

        return updatable;
    }

    public static JoggingEntity convertModelToEntity(JoggingModel model, Long userId){
        return JoggingEntity.builder()
                .id(model.getId())
                .distance(model.getDistance())
                .time(model.getTime())
                .dateTime(model.getDateTime())
                .userId(userId)
                .build();
    }

    public static JoggingModel convertEntityToModel(JoggingEntity entity){
        return JoggingModel.builder()
                .id(entity.getId())
                .distance(entity.getDistance())
                .time(entity.getTime())
                .dateTime(entity.getDateTime())
                .build();
    }
}

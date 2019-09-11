package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.model.JoggingModel;

public class JoggingConverter {

    public static JoggingEntity updateEntity(JoggingEntity updatable, JoggingModel newData){

        updatable.setDistance(newData.getDistance());
        updatable.setDuration(newData.getDuration());
        updatable.setCreatedAt(newData.getCreatedAt());

        return updatable;
    }

    public static JoggingEntity convertModelToEntity(JoggingModel model, Long userId){
        return JoggingEntity.builder()
                .id(model.getId())
                .distance(model.getDistance())
                .duration(model.getDuration())
                .createdAt(model.getCreatedAt())
                .userId(userId)
                .build();
    }

    public static JoggingModel convertEntityToModel(JoggingEntity entity){
        return JoggingModel.builder()
                .id(entity.getId())
                .distance(entity.getDistance())
                .duration(entity.getDuration())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

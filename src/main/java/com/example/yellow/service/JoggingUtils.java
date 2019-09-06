package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.entity.UserEntity;
import com.example.yellow.model.JoggingModel;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JoggingUtils {

    public JoggingEntity updateEntity(JoggingEntity updatable, JoggingModel newData){

        updatable.setDistance(newData.getDistance());
        updatable.setTime(newData.getTime());
        updatable.setDateTime(newData.getDateTime());

        return updatable;
    }

    public JoggingEntity convertModelToEntity(JoggingModel model, UserEntity user){
        return JoggingEntity.builder()
                .id(model.getId())
                .distance(model.getDistance())
                .time(model.getTime())
                .dateTime(model.getDateTime())
                .user(user)
                .build();
    }

    public JoggingModel convertEntityToModel(JoggingEntity entity){
        return JoggingModel.builder()
                .id(entity.getId())
                .distance(entity.getDistance())
                .time(entity.getTime())
                .dateTime(entity.getDateTime())
                .userId(entity.getUser().getId())
                .build();
    }

    public Iterable<JoggingModel> createListOfModels(Collection<JoggingEntity> joggings) {
        return joggings.stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }
}

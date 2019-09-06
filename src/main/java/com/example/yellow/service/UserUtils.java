package com.example.yellow.service;

import com.example.yellow.entity.UserEntity;
import com.example.yellow.model.UserModel;

public class UserUtils {

    public static UserModel convertEntityToModel(UserEntity entity){
        return UserModel.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}

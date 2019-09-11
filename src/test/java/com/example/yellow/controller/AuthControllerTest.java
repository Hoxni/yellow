package com.example.yellow.controller;

import com.example.yellow.abstraction.AbstractTest;
import com.example.yellow.entity.UserEntity;
import com.example.yellow.model.UserModel;
import org.junit.Assert;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractTest {

    @Test
    public void signUp() throws Exception{
        UserModel userModel = UserModel.builder().username("user1").password("user1").build();

        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userModel)))
                .andExpect(status().isOk());

        Assert.assertEquals(1L, userRepository.count());
    }

    @Test
    public void singIn() throws Exception{
        UserEntity userEntity = UserEntity.builder().username("user1").password("pass1").build();
        userRepository.save(userEntity);

        UserModel userModel = UserModel.builder().username("user1").password("pass1").build();

        mockMvc.perform(post("/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userModel)))
                .andExpect(status().isOk());
    }
}

package com.example.yellow.controller;

import com.example.yellow.abstraction.AbstractTest;
import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.entity.UserEntity;
import com.example.yellow.enumeration.Role;
import com.example.yellow.model.JoggingModel;
import com.example.yellow.security.jwt.JwtTokenUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JoggingControllerTest extends AbstractTest {

    @Test
    public void addJogging() throws Exception {

        UserEntity userEntity = UserEntity.builder()
                .username("user1").password("pass1").build();
        Long id = userRepository.save(userEntity).getId();

        String token = JwtTokenUtils.generateToken(userEntity);

        JoggingModel joggingModel = JoggingModel.builder()
                .distance(10L).duration(10L)
                .createdAt(LocalDateTime.of(2020, 10, 10, 10, 10))
                .build();

        mockMvc.perform(post("/api/v1/joggings")
                .header("Authorization", token)
                .contentType("application/json")
                .param("userId", id.toString())
                .content(objectMapper.writeValueAsString(joggingModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserJoggings() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .username("user1").password("pass1").build();
        Long id = userRepository.save(userEntity).getId();

        String token = JwtTokenUtils.generateToken(userEntity);

        JoggingEntity joggingEntity = JoggingEntity.builder().userId(id).build();
        joggingRepository.save(joggingEntity);

        mockMvc.perform(get("/api/v1/joggings")
                .header("Authorization", token)
                .contentType("application/json")
                .param("userId", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    public void getJogging() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .username("user1").password("pass1").userRole(Role.ADMIN).build();
        Long id = userRepository.save(userEntity).getId();

        String token = JwtTokenUtils.generateToken(userEntity);

        JoggingEntity joggingEntity = JoggingEntity.builder().userId(id).build();
        Long jogId = joggingRepository.save(joggingEntity).getId();

        mockMvc.perform(get("/api/v1/joggings/{joggingId}", jogId)
                .header("Authorization", token)
                .contentType("application/json")
                .param("userId", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(jogId.intValue())));
    }

    @Test
    public void updateJogging() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .username("user1").password("pass1").build();
        Long id = userRepository.save(userEntity).getId();

        String token = JwtTokenUtils.generateToken(userEntity);

        JoggingEntity joggingEntity = JoggingEntity.builder().userId(id).build();
        Long jogId = joggingRepository.save(joggingEntity).getId();

        JoggingModel joggingModel = JoggingModel.builder().id(jogId).build();

        mockMvc.perform(put("/api/v1/joggings")
                .header("Authorization", token)
                .contentType("application/json")
                .param("userId", id.toString())
                .content(objectMapper.writeValueAsBytes(joggingModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteJogging() throws Exception{
        UserEntity userEntity = UserEntity.builder()
                .username("user1").password("pass1").build();
        Long id = userRepository.save(userEntity).getId();

        String token = JwtTokenUtils.generateToken(userEntity);

        JoggingEntity joggingEntity = JoggingEntity.builder().userId(id).build();
        Long jogId = joggingRepository.save(joggingEntity).getId();

        mockMvc.perform(delete("/api/v1/joggings/{joggingId}", jogId)
                .header("Authorization", token)
                .contentType("application/json")
                .param("userId", id.toString()))
                .andExpect(status().isOk());

        Assert.assertEquals(0L, joggingRepository.count());
    }

    @Test
    public void getWeekStatistics() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .username("user1").password("pass1").build();
        Long id = userRepository.save(userEntity).getId();

        String token = JwtTokenUtils.generateToken(userEntity);

        JoggingEntity joggingEntity = JoggingEntity.builder()
                .userId(id).distance(10L).duration(10L)
                .createdAt(LocalDateTime.now())
                .build();
        joggingRepository.save(joggingEntity);

        mockMvc.perform(get("/api/v1/joggings/report")
                .header("Authorization", token)
                .contentType("application/json")
                .param("userId", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
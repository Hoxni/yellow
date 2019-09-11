package com.example.yellow.controller;

import com.example.yellow.abstraction.AbstractTest;
import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.entity.UserEntity;
import com.example.yellow.model.JoggingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JoggingControllerTest extends AbstractTest {

    @Before
    public void addUser(){
        UserEntity userEntity = UserEntity.builder()
                .id(1L).username("user1").password("pass1").build();
        userRepository.save(userEntity);
    }

    @Test
    public void addJogging() throws Exception {

        JoggingModel joggingModel = JoggingModel.builder()
                .id(1L).distance(10L).duration(10L)
                .createdAt(LocalDateTime.of(2020, 10, 10, 10, 10))
                .build();

        mockMvc.perform(post("/api/v1/joggings")
                .header("Authorization", "user1")
                .contentType("application/json")
                .param("userId", "1")
                .content(objectMapper.writeValueAsString(joggingModel)))
                .andExpect(status().isOk());

        Assert.assertEquals(1L, joggingRepository.count());
    }

    @Test
    public void getUserJoggings() throws Exception {

        mockMvc.perform(get("/api/v1/joggings")
                .header("Authorization", "user1")
                .contentType("application/json")
                .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getJogging() throws Exception {

        JoggingEntity joggingEntity = JoggingEntity.builder().id(1L).userId(1L).build();
        joggingRepository.save(joggingEntity);

        mockMvc.perform(get("/api/v1/joggings/{joggingId}", 1L)
                .header("Authorization", "user1")
                .contentType("application/json")
                .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateJogging() throws Exception {

        JoggingEntity joggingEntity = JoggingEntity.builder().id(1L).userId(1L).build();
        joggingRepository.save(joggingEntity);

        JoggingModel joggingModel = JoggingModel.builder().id(1L).build();

        mockMvc.perform(put("/api/v1/joggings")
                .header("Authorization", "user1")
                .contentType("application/json")
                .param("userId", "1")
                .content(objectMapper.writeValueAsBytes(joggingModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteJogging() throws Exception{

        JoggingEntity joggingEntity = JoggingEntity.builder().id(1L).userId(1L).build();
        joggingRepository.save(joggingEntity);

        mockMvc.perform(delete("/api/v1/joggings/{joggingId}", 1L)
                .header("Authorization", "user1")
                .contentType("application/json")
                .param("userId", "1"))
                .andExpect(status().isOk());

        Assert.assertEquals(0L, joggingRepository.count());
    }

    @Test
    public void getWeekStatistics() throws Exception{

        JoggingEntity joggingEntity = JoggingEntity.builder()
                .id(1L).userId(1L).distance(10L).duration(10L)
                .createdAt(LocalDateTime.now())
                .build();
        joggingRepository.save(joggingEntity);

        mockMvc.perform(get("/api/v1/joggings/report")
                .header("Authorization", "user1")
                .contentType("application/json")
                .param("userId", "1"))
                .andExpect(status().isOk());
    }
}
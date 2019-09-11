package com.example.yellow.repository;

import com.example.yellow.initializer.PostgresInitializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresInitializer.class})
public class JoggingRepositoryTest {

    @Autowired
    private JoggingRepository joggingRepository;

    @Test
    public void initDb_addJoggings_NumberOfJoggingsIs3(){
        Assert.assertEquals(3L, joggingRepository.count());
    }
}

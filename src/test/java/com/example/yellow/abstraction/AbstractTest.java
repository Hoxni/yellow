package com.example.yellow.abstraction;

import com.example.yellow.controller.AuthController;
import com.example.yellow.controller.JoggingController;
import com.example.yellow.initializer.PostgresInitializer;
import com.example.yellow.repository.JoggingRepository;
import com.example.yellow.repository.UserRepository;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {PostgresInitializer.class})
public abstract class AbstractTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JoggingRepository joggingRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JoggingController joggingController;

    @Autowired
    protected AuthController authController;

    @After
    public void clearDatabase(){
        joggingRepository.deleteAll();
        userRepository.deleteAll();
    }
}

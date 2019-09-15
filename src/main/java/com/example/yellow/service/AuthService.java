package com.example.yellow.service;

import com.example.yellow.entity.UserEntity;
import com.example.yellow.repository.UserRepository;
import com.example.yellow.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> addNewUser(String username, String password) {
        if (!userRepository.findByUsername(username).isPresent()) {
            UserEntity userEntity = UserEntity.builder()
                    .username(username).password(password).build();

            userRepository.save(userEntity);

            return login(username, password);
        } else throw new BadCredentialsException("Existing username");
    }

    public Map<String, String> login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        if (user.getPassword().equals(password)) {
            String token = JwtTokenUtils.generateToken(user);

            return Map.of("id", user.getId().toString(), "token", token);
        } else throw new BadCredentialsException("Wrong username/pass");
    }

}

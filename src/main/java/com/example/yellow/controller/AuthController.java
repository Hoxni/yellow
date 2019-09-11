package com.example.yellow.controller;

import com.example.yellow.model.UserModel;
import com.example.yellow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody UserModel user) throws Exception{

        String token = authService.addNewUser(user.getUsername(), user.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> singIn(@RequestBody UserModel user) throws Exception{

        String token = authService.login(user.getUsername(), user.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

}

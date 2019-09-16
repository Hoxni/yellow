package com.example.yellow.controller;

import com.example.yellow.model.UserModel;
import com.example.yellow.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody UserModel user) {

        Map<String, String> response = authService.addNewUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> singIn(@RequestBody UserModel user) {

        Map<String, String> response = authService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(response);
    }

}

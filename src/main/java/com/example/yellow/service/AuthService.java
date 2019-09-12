package com.example.yellow.service;

import com.example.yellow.entity.UserEntity;
import com.example.yellow.enumeration.Role;
import com.example.yellow.repository.UserRepository;
import com.example.yellow.security.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> addNewUser(String username, String password) {
        if (!userRepository.findByUsername(username).isPresent()) {
            UserEntity userEntity = UserEntity.builder()
                    .username(username).password(password).userRole(Role.USER).build();

            userRepository.save(userEntity);

            return login(username, password);
        } else throw new BadCredentialsException("Existing username");
    }

    public Map<String, String> login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        if (user.getPassword().equals(password)) {
            String token = jwtTokenUtils.generateToken(user);
            auth(token);
            return Map.of("id", user.getId().toString(), "token", token);
        } else throw new BadCredentialsException("Wrong username/pass");
    }

    private void auth(String token) {
        try {
            Claims claims = jwtTokenUtils.getClaimsFromToken(token);
            List<Role> authority = List.of(Role.valueOf(claims.get("role").toString()));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    claims, null,
                    authority.stream().map(v-> new SimpleGrantedAuthority(v.name())).collect(Collectors.toList())));
        } catch (BadCredentialsException ignored) {
        }
    }

}

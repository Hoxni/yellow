package com.example.yellow.security.evaluator;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.enumeration.Role;
import com.example.yellow.exception.JoggingNotFoundException;
import com.example.yellow.repository.JoggingRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Primary
@Service
public class JoggingPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private JoggingRepository joggingRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String action = permission.toString();

        switch (action) {
            case "create": {
                return ((Claims) authentication.getPrincipal()).get("id").toString().equals(targetDomainObject.toString());
            }
            case "report":
            case "read": {
                return ((Claims) authentication.getPrincipal()).get("id").toString().equals(targetDomainObject.toString()) ||
                        authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()));
            }
            case "get": {
                return authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name())) ||
                        ((Claims) authentication.getPrincipal()).get("id").equals(
                                joggingRepository.findById(
                                        Long.parseLong(targetDomainObject.toString()))
                                        .orElseThrow(()->new JoggingNotFoundException(Long.parseLong(targetDomainObject.toString())))
                                        .getUserId());
            }
            case "delete": {
                Optional<JoggingEntity> jogging = joggingRepository.findById((Long)targetDomainObject);
                return jogging.map(entity -> entity.getUserId().toString()
                        .equals(((Claims) authentication.getPrincipal()).get("id").toString()))
                        .orElse(false);
            }
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
package com.example.yellow.security.permission;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.enumeration.Role;
import com.example.yellow.repository.JoggingRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JoggingPermissionEvaluator implements PermissionEvaluator {

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
            case "get_jogging": {
                return authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()));
            }
            /*case "delete": {
                return ((Claims) authentication.getPrincipal()).get("id").equals(
                joggingRepository.findById(
                        Long.parseLong(targetDomainObject.toString()))
                        .orElse(JoggingEntity.builder().build())
                        .getUserId());
            }*/
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
package com.example.yellow.security.jwt;

import com.example.yellow.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    private String secret = "secret";

    public String getUsernameFromToken(String token){
        return (String) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("username");
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserEntity user){
        return Jwts.builder()
                .setClaims(getClaims(user))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Map<String, Object> getClaims(UserEntity userEntity){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userEntity.getId());
        claims.put("username", userEntity.getUsername());
        claims.put("role", userEntity.getUserRole().name());
        return claims;
    }

    public boolean validateToken(String token){
        return true;
    }
}

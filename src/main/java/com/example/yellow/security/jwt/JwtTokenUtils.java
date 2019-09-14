package com.example.yellow.security.jwt;

import com.example.yellow.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtils {

    private static String secret = "sdrFf367JKUsre02476f";

    public static String getUsernameFromToken(String token){
        return (String) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("username");
    }

    public static Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static String generateToken(UserEntity user){
        return Jwts.builder()
                .setClaims(getClaims(user))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private static Map<String, Object> getClaims(UserEntity userEntity){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userEntity.getId());
        claims.put("username", userEntity.getUsername());
        claims.put("role", userEntity.getUserRole().name());
        return claims;
    }

    public static boolean validateToken(String token){
        return getClaimsFromToken(token).getExpiration().after(new Date());
    }
}

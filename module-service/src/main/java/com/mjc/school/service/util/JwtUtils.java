package com.mjc.school.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateJwtToken(UserDetails userDetails){
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

        claims.put("permissions", userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public List<String> getAuthoritiesFromToken(String token){
        return getClaimsFromToken(token).get("permissions", List.class);
    }

    private Claims getClaimsFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}

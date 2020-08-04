package com.enosh.jwtauth.services;

import com.enosh.jwtauth.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.*;
import static javax.xml.bind.DatatypeConverter.*;

@Service
public class JwtService {

    @Value("${secret}")
    private String SECRET_KEY;

    public Function<Claims, String> extractSubject = Claims::getSubject;

    public <T extends UserEntity> String encodeJwt(T subject) {
        byte[] secretBytes = parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(secretBytes, HS256.getJcaName());

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setId(String.valueOf(subject.getId()))
                .setSubject(subject.getEmail())
                .signWith(HS256, key)
                .compact();
    }

    public Claims decodeJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean validateToken(Claims claims, UserDetails userDetails) {
        return extractSubject.apply(claims).equals(userDetails.getUsername());
    }
}

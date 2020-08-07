package com.enosh.jwtauth.services;

import com.enosh.jwtauth.model.Scope;
import com.enosh.jwtauth.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Map;
import java.util.function.Function;

import static com.enosh.jwtauth.model.Scope.*;
import static io.jsonwebtoken.SignatureAlgorithm.*;
import static javax.xml.bind.DatatypeConverter.*;

@Service
public class JwtService {

    @Value("${secret}")
    private String SECRET_KEY;

    public static final String SCOPE = "scope";
    public static final String ID = "id";
    public static final String NAME = "name";

    public Function<Claims, String> extractName = claims ->
            String.valueOf(claims.get(NAME));

    public Function<Claims, Scope> extractScope = claims ->
            Scope.valueOf(String.valueOf(claims.get(SCOPE)));

    public <T extends UserEntity> String encodeCompany(T t) {
        return encodeJwt(t, COMPANY);
    }

    public <T extends UserEntity> String encodeAdmin(T t) {
        t.setId(0L);
        return encodeJwt(t, ADMIN);
    }

    public <T extends UserEntity> String encodeJwt(T subject, Scope scope) {
        byte[] secretBytes = parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(secretBytes, HS256.getJcaName());
        Map<String, Object> claims = Map.of(
                SCOPE, scope,
                ID, subject.getId(),
                NAME, subject.getEmail()
        );
        return Jwts.builder()
                .setClaims(claims)
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
        return extractName.apply(claims).equals(userDetails.getUsername());
    }
}

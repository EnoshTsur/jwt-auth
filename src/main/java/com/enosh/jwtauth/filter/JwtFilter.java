package com.enosh.jwtauth.filter;

import com.enosh.jwtauth.services.CompanyDetailsService;
import com.enosh.jwtauth.services.JwtService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final CompanyDetailsService companyDetailsService;
    private final JwtService jwtService;

    private UserDetails mapToUserDetails(Claims claims) {
        return jwtService
                .extractSubject
                .andThen(companyDetailsService::loadUserByUsername)
                .apply(claims);
    }

    private boolean validateToken(Claims claims) {
        return jwtService.validateToken(claims, mapToUserDetails(claims));
    }

    private UsernamePasswordAuthenticationToken mapToToken(UserDetails userDetails){
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private Consumer<UsernamePasswordAuthenticationToken> setContextAuth(HttpServletRequest request) {
        return authToken -> {
            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                    .buildDetails(request)
            );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        };
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader("Authorization"))
                .map(header -> header.substring(7))
                .map(jwtService::decodeJwt)
                .filter(this::validateToken)
                .map(this::mapToUserDetails)
                .map(this::mapToToken)
                .ifPresent(setContextAuth(request));

        chain.doFilter(request, response);
    }
}

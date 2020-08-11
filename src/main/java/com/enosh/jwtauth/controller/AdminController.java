package com.enosh.jwtauth.controller;

import com.enosh.jwtauth.model.LoginDto;
import com.enosh.jwtauth.model.ResponseDto;
import com.enosh.jwtauth.model.UserEntity;
import com.enosh.jwtauth.services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin-username}")
    private String username;

    @Value("${admin-password}")
    private String password;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdminController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto<String>> authenticate(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(
                            false,
                            e.getMessage()
                    ));
        }

        return ResponseEntity.ok(
                new ResponseDto<>(true,
                        jwtService.encodeAdmin(
                                new UserEntity(
                                        username,
                                        password
                                )
                        )
                )
        );
    }

    @GetMapping("/me")
    public String me(Principal principal) {
        return "<h1><marquee>" + principal.getName() + "</marquee></h1>";
    }

}

package com.healthcare.system.controllers;

import com.healthcare.system.dto.AuthRequest;
import com.healthcare.system.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "authentication", description = "Method for Authentication of HealthCare Members")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Logins a User", description = "Takes AuthRequest containing credentials to login a user")
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = String.class)) })
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        }
        else {
            throw new UsernameNotFoundException("User with email " + authRequest.getEmail() + " does not exist");
        }
    }

    @GetMapping("/user")
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Object.class)) })
    @Operation(summary = "Gets a User", description = "Helps in getting the logged in user")
    public Object getUser() {
        return  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

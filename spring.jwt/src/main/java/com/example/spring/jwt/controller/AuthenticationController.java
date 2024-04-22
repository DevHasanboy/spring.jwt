package com.example.spring.jwt.controller;

import com.example.spring.jwt.model.AuthenticationResponse;
import com.example.spring.jwt.model.User;
import com.example.spring.jwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")

    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/admin")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("hello admin, here is it");
    }

}

package com.example.spring.jwt.service;

import com.example.spring.jwt.model.AuthenticationResponse;
import com.example.spring.jwt.model.User;
import com.example.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User request){
        User user= User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(request.getRole())
                .build();
        var used=userRepository.save(user);
        String token= jwtService.generateToken(used);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPassword(),
                        request.getUsername()
                )
        );
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token= jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

}

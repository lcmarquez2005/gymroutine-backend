package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.UserEntity;
import org.example.gymroutine.repository.UserRepository;
import org.example.gymroutine.security.JwtService;
import org.example.gymroutine.service.AuthService;
import org.example.gymroutine.model.request.AuthLoginRequest;
import org.example.gymroutine.model.request.AuthRegisterRequest;
import org.example.gymroutine.model.response.AuthResponse;
import org.example.gymroutine.model.response.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(AuthRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        var user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();
        
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return AuthResponse.builder()
                .token(jwtToken)
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        
        var jwtToken = jwtService.generateToken(user);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return AuthResponse.builder()
                .token(jwtToken)
                .user(userResponse)
                .build();
    }
}

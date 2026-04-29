package org.example.gymroutine.service;

import org.example.gymroutine.model.request.AuthLoginRequest;
import org.example.gymroutine.model.request.AuthRegisterRequest;
import org.example.gymroutine.model.response.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRegisterRequest request);
    AuthResponse login(AuthLoginRequest request);
}

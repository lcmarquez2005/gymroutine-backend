package org.example.gymroutine.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.service.UserService;
import org.example.gymroutine.model.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }
}

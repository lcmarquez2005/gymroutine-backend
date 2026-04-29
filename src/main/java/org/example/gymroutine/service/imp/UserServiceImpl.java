package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.UserEntity;
import org.example.gymroutine.repository.UserRepository;
import org.example.gymroutine.service.UserService;
import org.example.gymroutine.model.response.UserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        UserEntity currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponse.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .build();
    }
}

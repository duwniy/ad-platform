package com.adplatform.auth.service;

import com.adplatform.auth.dto.AuthResponse;
import com.adplatform.auth.dto.LoginRequest;
import com.adplatform.auth.dto.RegisterRequest;
import com.adplatform.config.JwtUtil;
import com.adplatform.shared.exception.BusinessException;
import com.adplatform.user.entity.Role;
import com.adplatform.user.entity.User;
import com.adplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }
        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .fullName(request.getFullName())
            .role(Role.ADVERTISER)
            .active(true)
            .build();
        userRepository.save(user);
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
            .token(token)
            .email(user.getEmail())
            .fullName(user.getFullName())
            .role(user.getRole().name())
            .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("User not found"));
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
            .token(token)
            .email(user.getEmail())
            .fullName(user.getFullName())
            .role(user.getRole().name())
            .build();
    }
}

package com.example.backendrestobarx.services;

import com.example.backendrestobarx.dto.LoginRequest;
import com.example.backendrestobarx.dto.LoginResponse;
import com.example.backendrestobarx.dto.UserDto;
import com.example.backendrestobarx.entity.User;
import com.example.backendrestobarx.repository.UserRepository;
import com.example.backendrestobarx.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmailAndIsActiveTrue(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado o inactivo");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        UserDto userDto = UserDto.fromEntity(user);

        return new LoginResponse(token, userDto);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return UserDto.fromEntity(user);
    }
}

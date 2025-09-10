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

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

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

    public UserDto updateUser(Long id, Map<String, String> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (updates.containsKey("name")) {
            user.setName(updates.get("name"));
        }
        if (updates.containsKey("phone")) {
            user.setPhone(updates.get("phone"));
        }

        User saved = userRepository.save(user);
        return UserDto.fromEntity(saved);
    }

}

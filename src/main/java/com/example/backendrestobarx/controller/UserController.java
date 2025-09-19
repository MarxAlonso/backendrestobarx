package com.example.backendrestobarx.controller;

import com.example.backendrestobarx.dto.UserDto;
import com.example.backendrestobarx.entity.User;
import com.example.backendrestobarx.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates
    ) {
        UserDto updated = userService.updateUser(id, updates);
        return ResponseEntity.ok(updated);
    }

    // Solo admin puede ver los clientes
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getAllClients() {
        return ResponseEntity.ok(userService.getClients());
    }
}

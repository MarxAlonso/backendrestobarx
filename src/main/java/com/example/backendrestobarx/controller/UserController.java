package com.example.backendrestobarx.controller;

import com.example.backendrestobarx.dto.RegisterRequest;
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
    public UserController(UserService userService) { this.userService = userService; }

    // Crear cliente (solo admin)
    @PostMapping
    public ResponseEntity<UserDto> createClient(@RequestBody RegisterRequest request) {
        UserDto created = userService.registerClientAndReturn(request);
        return ResponseEntity.ok(created);
    }

    // 👉 Editar usuario (admin o el propio cliente)
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates) {
        UserDto updated = userService.updateUser(id, updates);
        return ResponseEntity.ok(updated);
    }

    // Eliminar usuario (solo admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Listar clientes
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getAllClients() {
        return ResponseEntity.ok(userService.getClients());
    }
}

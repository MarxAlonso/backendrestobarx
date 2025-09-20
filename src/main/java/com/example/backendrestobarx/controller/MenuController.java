package com.example.backendrestobarx.controller;

import com.example.backendrestobarx.entity.MenuItem;
import com.example.backendrestobarx.repository.MenuItemRepository;
import com.example.backendrestobarx.services.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "http://localhost:5173")
public class MenuController {

    private final MenuItemService menuItemService;
    private final MenuItemRepository menuItemRepository;

    public MenuController(MenuItemService menuItemService, MenuItemRepository menuItemRepository) {
        this.menuItemService = menuItemService;
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<MenuItem>> getByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getByCategory(id));
    }

    /*@PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem item) {
        return ResponseEntity.ok(menuItemService.save(item));
    }*/

    //Esta funcion es para mostrar los 3 primeros menus en mi front react
    @GetMapping("/featured")
    public List<MenuItem> getFeaturedMenu() {
        return menuItemRepository.findTop3ByOrderByCreatedAtDesc();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem item) {
        return ResponseEntity.ok(menuItemService.save(item));
    }

    // Actualizar un menú existente (solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id,
                                                   @RequestBody MenuItem item) {
        return menuItemService.findById(id)
                .map(existing -> {
                    // Actualiza solo los campos que necesitas
                    existing.setTitle(item.getTitle());
                    existing.setDescription(item.getDescription());
                    existing.setPrice(item.getPrice());
                    existing.setImageUrl(item.getImageUrl());
                    existing.setCategory(item.getCategory());
                    return ResponseEntity.ok(menuItemService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un menú (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (menuItemService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
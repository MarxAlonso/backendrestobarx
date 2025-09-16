package com.example.backendrestobarx.services;

import com.example.backendrestobarx.entity.MenuItem;
import com.example.backendrestobarx.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryId(categoryId);
    }

    public MenuItem save(MenuItem item) {
        return menuItemRepository.save(item);
    }
}
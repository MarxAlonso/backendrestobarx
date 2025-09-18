package com.example.backendrestobarx.repository;

import com.example.backendrestobarx.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);
    List<MenuItem> findTop3ByOrderByCreatedAtDesc();
}
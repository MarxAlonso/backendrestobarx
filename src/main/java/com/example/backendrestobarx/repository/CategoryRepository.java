package com.example.backendrestobarx.repository;

import com.example.backendrestobarx.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> { }
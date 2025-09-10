package com.dreamshop.dreamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamshop.dreamshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Category findByName(String name);
}

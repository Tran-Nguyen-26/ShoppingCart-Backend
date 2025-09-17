package com.dreamshop.dreamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamshop.dreamshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  User findByEmail(String email);

}

package com.dreamshop.dreamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamshop.dreamshop.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}

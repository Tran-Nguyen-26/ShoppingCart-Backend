package com.dreamshop.dreamshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.exceptions.AlreadyExistsException;
import com.dreamshop.dreamshop.exceptions.ResourceNotFoundException;
import com.dreamshop.dreamshop.model.Category;
import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

  private final ICategoryService categoryService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllCategories() {
    try {
      List<Category> categories = categoryService.getAllCategories();
      return ResponseEntity.ok(new ApiResponse("success", categories));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new ApiResponse("Error:", 500));
    }
  }

  @GetMapping("/{id}/category")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
    try {
      Category category = categoryService.getCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("found", category));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("{name}/category")
  public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
    try {
      Category category = categoryService.getCategoryByName(name);
      return ResponseEntity.ok(new ApiResponse("found", category));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
    try {
      Category theCategory = categoryService.addCategory(category);
      return ResponseEntity.ok(new ApiResponse("add success", theCategory));
    } catch (AlreadyExistsException e) {
      return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
    try {
      Category theCategory = categoryService.updateCategory(category, id);
      return ResponseEntity.ok(new ApiResponse("Update success", theCategory));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
    try {
      categoryService.deleteCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("delete success", null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
    }
  }
}

package com.dreamshop.dreamshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.dto.UserDto;
import com.dreamshop.dreamshop.model.User;
import com.dreamshop.dreamshop.request.CreateUserRequest;
import com.dreamshop.dreamshop.request.UpdateUserRequest;
import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

  private final IUserService userService;

  @GetMapping("/{id}/user")
  public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    UserDto userDto = userService.convertToDto(user);
    return ResponseEntity.ok(new ApiResponse("success", userDto));
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
    User newUser = userService.createUser(request);
    UserDto userDto = userService.convertToDto(newUser);
    return ResponseEntity.ok(new ApiResponse("create user success", userDto));
  }

  @PutMapping("/{userId}/update")
  public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
    User user = userService.updateUser(request, userId);
    UserDto userDto = userService.convertToDto(user);
    return ResponseEntity.ok(new ApiResponse("update success", userDto));
  }

  @DeleteMapping("/{userId}/delete")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok(new ApiResponse("delete success", null));
  }
}

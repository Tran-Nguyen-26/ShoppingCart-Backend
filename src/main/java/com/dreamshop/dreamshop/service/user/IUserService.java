package com.dreamshop.dreamshop.service.user;

import com.dreamshop.dreamshop.dto.UserDto;
import com.dreamshop.dreamshop.model.User;
import com.dreamshop.dreamshop.request.CreateUserRequest;
import com.dreamshop.dreamshop.request.UpdateUserRequest;

public interface IUserService {
  User getUserById(Long userId);

  User createUser(CreateUserRequest request);

  User updateUser(UpdateUserRequest request, Long userId);

  void deleteUser(Long userId);

  UserDto convertToDto(User user);

  User getAuthenticatedUser();
}

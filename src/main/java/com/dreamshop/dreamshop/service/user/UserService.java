package com.dreamshop.dreamshop.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dreamshop.dreamshop.exceptions.AlreadyExistsException;
import com.dreamshop.dreamshop.exceptions.ResourceNotFoundException;
import com.dreamshop.dreamshop.model.User;
import com.dreamshop.dreamshop.repository.UserRepository;
import com.dreamshop.dreamshop.request.CreateUserRequest;
import com.dreamshop.dreamshop.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

  private final UserRepository userRepository;

  @Override
  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("user not found"));
  }

  @Override
  public User createUser(CreateUserRequest request) {
    return Optional.of(request)
        .filter(req -> !userRepository.existsByEmail(request.getEmail()))
        .map(req -> {
          User user = new User();
          user.setFirstName(req.getFirstName());
          user.setLastName(req.getLastName());
          user.setEmail(req.getEmail());
          user.setPassword(req.getPassword());
          return userRepository.save(user);
        }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + "already exists"));
  }

  @Override
  public User updateUser(UpdateUserRequest request, Long userId) {
    return userRepository.findById(userId).map(user -> {
      user.setFirstName(request.getFirstName());
      user.setLastName(request.getLastName());
      return userRepository.save(user);
    }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
      throw new ResourceNotFoundException("user not found");
    });
  }

}

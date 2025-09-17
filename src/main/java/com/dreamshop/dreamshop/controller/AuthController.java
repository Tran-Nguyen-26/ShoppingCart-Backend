package com.dreamshop.dreamshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.request.LoginRequest;
import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.response.JwtResponse;
import com.dreamshop.dreamshop.security.jwt.JwtUtils;
import com.dreamshop.dreamshop.security.user.ShopUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateTokenForUser(authentication);
    ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
    return ResponseEntity.ok(new ApiResponse("login success", new JwtResponse(userDetails.getId(), jwt)));
  }
}

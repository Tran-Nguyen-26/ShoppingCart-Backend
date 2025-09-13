package com.dreamshop.dreamshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.model.Cart;
import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
  private final ICartService cartService;

  @GetMapping("/{cartId}/my-cart")
  public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
    Cart cart = cartService.getCart(cartId);
    return ResponseEntity.ok(new ApiResponse("success", cart));
  }

  @DeleteMapping("/{cartId}/clear")
  public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
    cartService.clearCart(cartId);
    return ResponseEntity.ok(new ApiResponse("clear cart success", null));
  }

  @GetMapping("/{cartId}/cart/total-price")
  public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
    return ResponseEntity.ok(new ApiResponse("Total price", cartService.getTotalPrice(cartId)));
  }
}

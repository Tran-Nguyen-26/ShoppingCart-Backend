package com.dreamshop.dreamshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.service.cart.ICartItemService;
import com.dreamshop.dreamshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

  private final ICartItemService cartItemService;
  private final ICartService cartService;

  @PostMapping("/item/add")
  public ResponseEntity<ApiResponse> addItemToCart(
      @RequestParam Long cartId,
      @RequestParam Long productId,
      @RequestParam int quantity) {
    if (cartId == null) {
      cartId = cartService.initializeNewCart();
    }
    cartItemService.addItemToCart(cartId, productId, quantity);
    return ResponseEntity.ok(new ApiResponse("add item success", null));
  }

  @DeleteMapping("/{cartId}/item/{productId}/remove")
  public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
    cartItemService.removeItemFromCart(cartId, productId);
    return ResponseEntity.ok(new ApiResponse("remove success", null));
  }

  @PutMapping("/cart/{cartId}/item/{productId}/update")
  public ResponseEntity<ApiResponse> updateItemQuantity(
      @PathVariable Long cartId,
      @PathVariable Long productId,
      @RequestParam int quantity) {
    cartItemService.updateItemToCart(cartId, productId, quantity);
    return ResponseEntity.ok(new ApiResponse("update success", null));
  }
}

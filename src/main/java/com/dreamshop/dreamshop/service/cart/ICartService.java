package com.dreamshop.dreamshop.service.cart;

import java.math.BigDecimal;

import com.dreamshop.dreamshop.model.Cart;
import com.dreamshop.dreamshop.model.User;

public interface ICartService {
  Cart getCart(Long id);

  void clearCart(Long id);

  BigDecimal getTotalPrice(Long id);

  Cart initializeNewCart(User user);

  Cart getCartByUserId(Long userId);
}

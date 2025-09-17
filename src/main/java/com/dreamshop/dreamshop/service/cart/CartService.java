package com.dreamshop.dreamshop.service.cart;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamshop.dreamshop.model.Cart;
import com.dreamshop.dreamshop.model.User;
import com.dreamshop.dreamshop.repository.CartItemRepository;
import com.dreamshop.dreamshop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  @Override
  public Cart getCart(Long id) {
    return cartRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("cart not found"));
  }

  @Transactional
  @Override
  public void clearCart(Long id) {
    Cart cart = this.getCart(id);
    cartItemRepository.deleteAllByCartId(id);
    cart.getItems().clear();
    cartRepository.deleteById(id);
  }

  @Override
  public BigDecimal getTotalPrice(Long id) {
    Cart cart = this.getCart(id);
    return cart.getTotalAmount();
  }

  @Override
  public Cart initializeNewCart(User user) {
    return Optional
        .ofNullable(this.getCartByUserId(user.getId()))
        .orElseGet(() -> {
          Cart cart = new Cart();
          cart.setUser(user);
          return cartRepository.save(cart);
        });
  }

  @Override
  public Cart getCartByUserId(Long userId) {
    return cartRepository.findByUserId(userId);
  }
}

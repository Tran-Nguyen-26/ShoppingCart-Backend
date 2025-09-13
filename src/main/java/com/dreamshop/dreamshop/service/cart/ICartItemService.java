package com.dreamshop.dreamshop.service.cart;

import com.dreamshop.dreamshop.model.CartItem;

public interface ICartItemService {
  void addItemToCart(Long cartId, Long productId, int quantity);

  void removeItemFromCart(Long cartId, Long productId);

  void updateItemToCart(Long cartId, Long productId, int quantity);

  CartItem getCartItem(Long cartId, Long productId);
}

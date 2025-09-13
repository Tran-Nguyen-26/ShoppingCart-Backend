package com.dreamshop.dreamshop.service.cart;

import org.springframework.stereotype.Service;

import com.dreamshop.dreamshop.exceptions.ResourceNotFoundException;
import com.dreamshop.dreamshop.model.Cart;
import com.dreamshop.dreamshop.model.CartItem;
import com.dreamshop.dreamshop.model.Product;
import com.dreamshop.dreamshop.repository.CartItemRepository;
import com.dreamshop.dreamshop.repository.CartRepository;
import com.dreamshop.dreamshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final IProductService productService;
  private final ICartService cartService;

  @Override
  public void addItemToCart(Long cartId, Long productId, int quantity) {
    Cart cart = cartService.getCart(cartId);
    Product product = productService.getProductById(productId);
    CartItem cartItem = cart.getItems()
        .stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst().orElse(new CartItem());
    if (cartItem.getId() == null) {
      cartItem.setQuantity(quantity);
      cartItem.setUnitPrice(product.getPrice());
      cartItem.setProduct(product);
      cartItem.setCart(cart);
    } else {
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }
    cartItem.setTotalPrice();
    cart.addItem(cartItem);
    cartItemRepository.save(cartItem);
    cartRepository.save(cart);
  }

  @Override
  public void removeItemFromCart(Long cartId, Long productId) {
    Cart cart = cartService.getCart(cartId);
    CartItem cartItem = this.getCartItem(cartId, productId);
    cart.removeItem(cartItem);
    cartRepository.save(cart);
  }

  @Override
  public void updateItemToCart(Long cartId, Long productId, int quantity) {
    Cart cart = cartService.getCart(cartId);
    cart.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .ifPresent(item -> {
          item.setQuantity(item.getQuantity() + quantity);
          item.setUnitPrice(item.getProduct().getPrice());
          item.setTotalPrice();
        });
    cart.updateTotalAmount();
    cartRepository.save(cart);
  }

  @Override
  public CartItem getCartItem(Long cartId, Long productId) {
    Cart cart = cartService.getCart(cartId);
    return cart.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("product not found"));
  }
}

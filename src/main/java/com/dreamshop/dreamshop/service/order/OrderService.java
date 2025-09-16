package com.dreamshop.dreamshop.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dreamshop.dreamshop.dto.OrderDto;
import com.dreamshop.dreamshop.enums.OrderStatus;
import com.dreamshop.dreamshop.exceptions.ResourceNotFoundException;
import com.dreamshop.dreamshop.model.Cart;
import com.dreamshop.dreamshop.model.Order;
import com.dreamshop.dreamshop.model.OrderItem;
import com.dreamshop.dreamshop.model.Product;
import com.dreamshop.dreamshop.repository.OrderRepository;
import com.dreamshop.dreamshop.repository.ProductRepository;
import com.dreamshop.dreamshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final ICartService cartService;
  private final ModelMapper modelMapper;

  @Override
  public Order placeOrder(Long userId) {
    Cart cart = cartService.getCartByUserId(userId);
    Order order = this.createOrder(cart);
    List<OrderItem> orderItems = createOrderItems(order, cart);
    order.setTotalAmount(this.calculateTotalAmount(orderItems));
    order.setOrderItems(new HashSet<>(orderItems));
    Order savedOrder = orderRepository.save(order);
    cartService.clearCart(cart.getId());
    return savedOrder;
  }

  private Order createOrder(Cart cart) {
    Order order = new Order();
    order.setUser(cart.getUser());
    order.setOrderDate(LocalDate.now());
    order.setOrderStatus(OrderStatus.PENDING);
    return order;
  }

  private List<OrderItem> createOrderItems(Order order, Cart cart) {
    return cart.getItems()
        .stream()
        .map(cartItem -> {
          Product product = cartItem.getProduct();
          product.setInventory(product.getInventory() - cartItem.getQuantity());
          productRepository.save(product);
          return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
        })
        .toList();
  }

  private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
    return orderItems
        .stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Override
  public OrderDto getOrder(Long orderId) {
    return orderRepository
        .findById(orderId)
        .map(this::convertToDto)
        .orElseThrow(() -> new ResourceNotFoundException("order not found"));
  }

  @Override
  public List<OrderDto> getUserOrders(Long userId) {
    return orderRepository
        .findByUserId(userId)
        .stream()
        .map(this::convertToDto).toList();
  }

  @Override
  public OrderDto convertToDto(Order order) {
    OrderDto orderDto = modelMapper.map(order, OrderDto.class);
    return orderDto;
  }

}
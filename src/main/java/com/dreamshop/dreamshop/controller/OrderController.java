package com.dreamshop.dreamshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamshop.dreamshop.dto.OrderDto;
import com.dreamshop.dreamshop.model.Order;
import com.dreamshop.dreamshop.response.ApiResponse;
import com.dreamshop.dreamshop.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

  private final IOrderService orderService;

  @PostMapping("/order")
  public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
    Order order = orderService.placeOrder(userId);
    return ResponseEntity.ok(new ApiResponse("create success", order));
  }

  @GetMapping("/{orderId}/order")
  public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
    OrderDto orderDto = orderService.getOrder(orderId);
    return ResponseEntity.ok(new ApiResponse("success", orderDto));
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<ApiResponse> getUserOrders(Long userId) {
    List<OrderDto> ordersDto = orderService.getUserOrders(userId);
    return ResponseEntity.ok(new ApiResponse("success", ordersDto));
  }
}

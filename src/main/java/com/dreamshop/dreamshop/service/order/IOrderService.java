package com.dreamshop.dreamshop.service.order;

import java.util.List;

import com.dreamshop.dreamshop.model.Order;

public interface IOrderService {
  Order placeOrder(Long userId);

  Order getOrder(Long orderId);

  List<Order> getUserOrders(Long userId);
}

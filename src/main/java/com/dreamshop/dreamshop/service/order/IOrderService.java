package com.dreamshop.dreamshop.service.order;

import java.util.List;

import com.dreamshop.dreamshop.dto.OrderDto;
import com.dreamshop.dreamshop.model.Order;

public interface IOrderService {
  Order placeOrder(Long userId);

  OrderDto getOrder(Long orderId);

  List<OrderDto> getUserOrders(Long userId);

  OrderDto convertToDto(Order order);
}

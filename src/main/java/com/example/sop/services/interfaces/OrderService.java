package com.example.sop.services.interfaces;

import com.example.sop.services.dtos.OrderDTO;

import java.util.List;
import java.util.UUID;


public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    List<OrderDTO> getAllOrders();

    OrderDTO updateOrderStatus(UUID orderId, String newStatus);

    OrderDTO getOrderById(UUID orderId);

    void deleteOrderById(UUID orderId);

}
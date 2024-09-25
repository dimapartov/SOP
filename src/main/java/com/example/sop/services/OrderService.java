package com.example.sop.services;

import com.example.sop.services.dtos.OrderDTO;

import java.util.List;
import java.util.UUID;


public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(UUID id, OrderDTO orderDTO);
    OrderDTO getOrderById(UUID id);
    List<OrderDTO> getAllOrders();
    void deleteOrder(UUID id);

}
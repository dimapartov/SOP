package com.example.sop.services;

import com.example.sop.services.dtos.OrderItemDTO;

import java.util.List;
import java.util.UUID;


public interface OrderItemService {

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
//    List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId);
    void deleteOrderItemById(UUID id);

}
package com.example.sop.services.interfaces;

import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;

import java.util.List;
import java.util.UUID;


public interface OrderItemService {

    OrderItemCreationDTO createOrderItem(OrderItemCreationDTO orderItemCreationDTO);

    List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId);

    OrderItemDTO getOrderItemById(UUID orderItemId);

    void deleteOrderItemById(UUID id);

}
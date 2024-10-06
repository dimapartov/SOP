package com.example.sop.services.impl;

import com.example.sop.models.OrderItem;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrderItemServiceImpl implements OrderItemService {

    private ModelMapper modelMapper;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;


    @Autowired
    public OrderItemServiceImpl(ModelMapper modelMapper, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.modelMapper = modelMapper;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public OrderItemCreationDTO createOrderItem(OrderItemCreationDTO orderItemCreationDTO) {
        OrderItem orderItem = modelMapper.map(orderItemCreationDTO, OrderItem.class);
        orderItemRepository.saveAndFlush(orderItem);
        return modelMapper.map(orderItem, OrderItemCreationDTO.class);
    }

    @Override
    public List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId) {
        List<OrderItem> allOrderItemsByOrderId = orderItemRepository.findOrderItemsByOrderId(orderId);
        return allOrderItemsByOrderId.stream().map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class)).toList();
    }

    @Override
    public OrderItemDTO getOrderItemById(UUID orderItemId) {
        Optional<OrderItem> requestedOrderItem = orderItemRepository.findById(orderItemId);
        if (requestedOrderItem.isEmpty()) {
            throw new RuntimeException("Order item not found");
        }
        return modelMapper.map(requestedOrderItem, OrderItemDTO.class);
    }

    @Override
    public void deleteOrderItemById(UUID id) {
        orderItemRepository.deleteById(id);
    }

}
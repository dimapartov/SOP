package com.example.sop.services.impl;

import com.example.sop.models.OrderItem;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        orderItemRepository.saveAndFlush(orderItem);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    @Override
    public List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId) {
        List<OrderItem> allOrderItemsByOrderId = orderItemRepository.findOrderItemsByOrderId(orderId);
        return allOrderItemsByOrderId.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItemById(UUID id) {
        orderItemRepository.deleteById(id);
    }

}
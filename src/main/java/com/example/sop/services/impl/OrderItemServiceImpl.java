package com.example.sop.services.impl;

import com.example.sop.models.Order;
import com.example.sop.models.OrderItem;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.OrderItemService;
import com.example.sop.services.dtos.OrderItemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        Order order = orderRepository.findById(orderItemDTO.getOrderId()).get();
        orderItem.setOrder(order);
        orderItemRepository.saveAndFlush(orderItem);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    @Override
    public void deleteOrderItemById(UUID id) {
        orderItemRepository.deleteById(id);
    }

}
package com.example.sop.services.impl;

import com.example.sop.models.Order;
import com.example.sop.models.OrderItem;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.interfaces.OrderItemService;
import com.example.sop.services.dtos.OrderItemDTO;
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
        Order order = orderRepository.findById(orderItemDTO.getOrderId()).get();
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        orderItem.setOrder(order);
        orderItemRepository.saveAndFlush(orderItem);
        OrderItemDTO newOrderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
        newOrderItemDTO.setOrderId(orderItem.getOrder().getId());
        return newOrderItemDTO;
    }

    @Override
    public List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId) {
        List<OrderItem> orderItemsByOrderId = orderItemRepository.findOrderItemsByOrderId(orderId);
        return orderItemsByOrderId.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItemById(UUID id) {
        orderItemRepository.deleteById(id);
    }

}
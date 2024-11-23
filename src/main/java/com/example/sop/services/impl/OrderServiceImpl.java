package com.example.sop.services.impl;

import com.example.sopcontracts.enums.OrderStatusEnum;
import com.example.sop.models.Order;
import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.exceptions.OrderNotFoundException;
import com.example.sop.services.interfaces.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrderServiceImpl implements OrderService {

    private ModelMapper modelMapper;
    private OrderRepository orderRepository;
    private EmployeeRepository employeeRepository;


    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setOrderStatus(OrderStatusEnum.CREATED);
        orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        return allOrders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).toList();
    }

    @Override
    public OrderDTO updateOrderStatus(UUID orderId, String newStatus) {
        Optional<Order> targetOrder = orderRepository.findById(orderId);
        if (targetOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }
        targetOrder.get().setOrderStatus(OrderStatusEnum.valueOf(newStatus.toUpperCase()));
        orderRepository.saveAndFlush(targetOrder.get());
        return modelMapper.map(targetOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(UUID orderId) {
        Optional<Order> requestedOrder = orderRepository.findById(orderId);
        if (requestedOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }
        return modelMapper.map(requestedOrder, OrderDTO.class);
    }

    @Override
    public void deleteOrderById(UUID orderId) {
        Optional<Order> requestedOrder = orderRepository.findById(orderId);
        if (requestedOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }
        orderRepository.deleteById(orderId);
    }

}
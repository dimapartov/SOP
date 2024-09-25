/*
package com.example.sop.services.impl;

import com.example.sop.enums.OrderStatusEnum;
import com.example.sop.models.Employee;
import com.example.sop.models.Order;
import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.interfaces.OrderService;
import com.example.sop.services.dtos.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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
        Employee employee = employeeRepository.findById(orderDTO.getEmployeeId()).get();
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setEmployee(employee);
        Order savedOrder = orderRepository.saveAndFlush(order);
        OrderDTO newOrderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        newOrderDTO.setEmployeeId(savedOrder.getEmployee().getId());
        return newOrderDTO;
    }

    @Override
    public OrderDTO updateOrderStatus(UUID id, String newStatus) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderStatusEnum newOrderStatusEnum = OrderStatusEnum.valueOf(newStatus.toUpperCase());
        existingOrder.setOrderStatus(newOrderStatusEnum);
        Order savedOrder = orderRepository.saveAndFlush(existingOrder);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setEmployeeId(order.getEmployee().getId());
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}*/
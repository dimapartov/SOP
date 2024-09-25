package com.example.sop.services.impl;

import com.example.sop.models.Employee;
import com.example.sop.models.Order;
import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.services.OrderService;
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
        Order order = modelMapper.map(orderDTO, Order.class);
        Employee employee = employeeRepository.findById(orderDTO.getEmployeeId()).get();
        order.setEmployee(employee);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrder(UUID id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        modelMapper.map(orderDTO, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
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
}
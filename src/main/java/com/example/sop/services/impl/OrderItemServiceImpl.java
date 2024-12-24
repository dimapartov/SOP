package com.example.sop.services.impl;

import com.example.sop.grpc.OrderPartValidationClient;
import com.example.sop.models.Order;
import com.example.sop.models.OrderItem;
import com.example.sop.models.Part;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import com.example.sopcontracts.exceptions.OrderItemNotFoundException;
import com.example.sopcontracts.exceptions.OrderNotFoundException;
import com.example.sopcontracts.exceptions.PartNotFoundException;
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
    private PartRepository partRepository;
    private OrderPartValidationClient orderPartValidationClient;


    @Autowired
    public OrderItemServiceImpl(ModelMapper modelMapper,
                                OrderItemRepository orderItemRepository,
                                OrderRepository orderRepository,
                                PartRepository partRepository,
                                OrderPartValidationClient orderPartValidationClient) {
        this.modelMapper = modelMapper;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.partRepository = partRepository;
        this.orderPartValidationClient = orderPartValidationClient;
    }


    @Override
    public OrderItemDTO createOrderItem(OrderItemCreationDTO orderItemCreationDTO) {
        Part part = partRepository.findById(orderItemCreationDTO.getPartId())
                .orElseThrow(() -> new PartNotFoundException(orderItemCreationDTO.getPartId()));

        // Validate Part using gRPC
        orderPartValidationClient.validatePart(orderItemCreationDTO.getPartId().toString(), orderItemCreationDTO.getQuantity());

        // Proceed with normal creation logic
        OrderItem orderItem = modelMapper.map(orderItemCreationDTO, OrderItem.class);

        Order order = orderRepository.findById(orderItemCreationDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(orderItemCreationDTO.getOrderId()));

        orderItem.setOrder(order);
        orderItem.setPart(part);

        orderItemRepository.saveAndFlush(orderItem);

        part.setQuantityOnStorage(part.getQuantityOnStorage() - orderItem.getQuantity());
        partRepository.saveAndFlush(part);

        return modelMapper.map(orderItem, OrderItemDTO.class);
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
            throw new OrderItemNotFoundException(orderItemId);
        }
        return modelMapper.map(requestedOrderItem, OrderItemDTO.class);
    }

    @Override
    public void deleteOrderItemById(UUID orderItemId) {
        Optional<OrderItem> requestedOrderItem = orderItemRepository.findById(orderItemId);
        if (requestedOrderItem.isEmpty()) {
            throw new OrderItemNotFoundException(orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }

}
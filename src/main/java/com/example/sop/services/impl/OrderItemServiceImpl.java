package com.example.sop.services.impl;

import com.example.sop.models.Order;
import com.example.sop.models.OrderItem;
import com.example.sop.models.Part;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sopcontracts.exceptions.OrderItemNotFoundException;
import com.example.sop.services.interfaces.OrderItemService;
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


    @Autowired
    public OrderItemServiceImpl(ModelMapper modelMapper, OrderItemRepository orderItemRepository, OrderRepository orderRepository, PartRepository partRepository) {
        this.modelMapper = modelMapper;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.partRepository = partRepository;
    }


    @Override
    public OrderItemDTO createOrderItem(OrderItemCreationDTO orderItemCreationDTO) {
        // Преобразуем DTO в сущность OrderItem
        OrderItem orderItem = modelMapper.map(orderItemCreationDTO, OrderItem.class);

        // Проверяем, существует ли связанный заказ
        Order order = orderRepository.findById(orderItemCreationDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(orderItemCreationDTO.getOrderId()));

        // Присваиваем заказ сущности
        orderItem.setOrder(order);

        // Пример: если нужна связь с другой сущностью (Part)
        // Извлекаем Part и добавляем в OrderItem
        Part part = partRepository.findById(orderItemCreationDTO.getPartId())
                .orElseThrow(() -> new PartNotFoundException(orderItemCreationDTO.getPartId()));
        orderItem.setPart(part);

        // Сохраняем OrderItem
        orderItemRepository.saveAndFlush(orderItem);

        // Преобразуем сущность OrderItem обратно в DTO. При этом
        // вложенные объекты (Order и Part) тоже преобразуются в DTO
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
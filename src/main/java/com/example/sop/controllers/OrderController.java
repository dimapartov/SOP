package com.example.sop.controllers;

import com.example.sop.config.RabbitMQConfiguration;
import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.interfaces.OrderService;
import com.example.sopcontracts.controllers.OrdersApi;
import com.example.sopcontracts.dtos.OrderRequest;
import com.example.sopcontracts.dtos.OrderResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class OrderController implements OrdersApi {

    private OrderService orderService;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    private OrderDTO mapToOrderDTO(OrderRequest orderRequest) {
        return new OrderDTO(orderRequest.employeeId(), orderRequest.customerName(), orderRequest.customerEmail());
    }

    private OrderResponse mapToOrderResponse(OrderDTO orderDTO) {
        return new OrderResponse(orderDTO.getId(), orderDTO.getEmployeeId(), orderDTO.getCustomerName(),
                orderDTO.getCustomerEmail(), orderDTO.getOrderStatus());
    }


    @Override
    public ResponseEntity<EntityModel<OrderResponse>> createOrder(OrderRequest orderRequest) {
        OrderDTO mappedOrderDTO = mapToOrderDTO(orderRequest);

        OrderDTO createdOrder = orderService.createOrder(mappedOrderDTO);

        OrderResponse orderResponse = mapToOrderResponse(createdOrder);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.create", orderResponse);

        EntityModel<OrderResponse> createdOrderEntityModel = EntityModel.of(orderResponse);

        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(orderResponse.id())).withSelfRel());
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrderById(orderResponse.id())).withRel(
                "deleteOrder"));
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).updateOrderStatus(orderResponse.id(),
                "newStatus")).withRel("updateStatus"));

        return ResponseEntity.created(createdOrderEntityModel.getRequiredLink("self").toUri()).body(createdOrderEntityModel);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<OrderResponse>>> getAllOrders() {
        // Получаем список заказов из сервиса
        List<OrderDTO> orderDTOs = orderService.getAllOrders();

        // Преобразуем OrderDTO в OrderResponse и оборачиваем в EntityModel
        List<EntityModel<OrderResponse>> orderResponses = orderDTOs.stream()
                .map(orderDTO -> {
                    OrderResponse orderResponse = mapToOrderResponse(orderDTO);
                    return EntityModel.of(orderResponse,
                            linkTo(methodOn(OrderController.class).getOrderById(orderResponse.id())).withSelfRel(),
                            linkTo(methodOn(OrderController.class).deleteOrderById(orderResponse.id())).withRel("deleteOrder"),
                            linkTo(methodOn(OrderController.class).updateOrderStatus(orderResponse.id(), "newStatus")).withRel("updateStatus"),
                            linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
                })
                .toList();

        // Оборачиваем список в CollectionModel для HATEOAS
        CollectionModel<EntityModel<OrderResponse>> allOrdersCollectionModel = CollectionModel.of(orderResponses);

        return ResponseEntity.ok(allOrdersCollectionModel);
    }

    @Override
    public ResponseEntity<EntityModel<OrderResponse>> getOrderById(UUID orderId) {
        OrderDTO orderById = orderService.getOrderById(orderId);

        OrderResponse orderResponse = mapToOrderResponse(orderById);

        EntityModel<OrderResponse> orderByIdEntityModel = EntityModel.of(orderResponse);

        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(orderId)).withSelfRel());
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrderById(orderResponse.id())).withRel(
                "deleteOrder"));
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).updateOrderStatus(orderResponse.id(),
                "newStatus")).withRel("updateStatus"));

        return ResponseEntity.ok(orderByIdEntityModel);
    }

    @Override
    public ResponseEntity<EntityModel<OrderResponse>> updateOrderStatus(UUID orderId, String newStatus) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, newStatus);

        OrderResponse orderResponse = mapToOrderResponse(updatedOrder);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.update.status", orderResponse);

        EntityModel<OrderResponse> updatedOrderEntityModel = EntityModel.of(orderResponse);

        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(orderId)).withSelfRel());
        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrderById(orderResponse.id())).withRel(
                "deleteOrder"));


        return ResponseEntity.ok(updatedOrderEntityModel);
    }

    @Override
    public ResponseEntity<String> deleteOrderById(UUID orderId) {
        orderService.deleteOrderById(orderId);

        String deletionSucceededMessage = "Successfully deleted order with order ID: " + orderId;

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.delete", deletionSucceededMessage);

        return ResponseEntity.ok(deletionSucceededMessage);
    }

}
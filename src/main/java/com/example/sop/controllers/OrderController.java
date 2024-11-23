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

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class OrderController implements OrdersApi {

    private OrderService orderService;
    private RabbitTemplate rabbitTemplate;

    private OrderDTO mapToOrderDTO(OrderRequest orderRequest) {
        return new OrderDTO(
                orderRequest.employeeId(),
                orderRequest.customerName(),
                orderRequest.customerEmail()
        );
    }

    private OrderResponse mapToOrderResponse(OrderDTO orderDTO) {
        return new OrderResponse(
                orderDTO.getId(),
                orderDTO.getEmployeeId(),
                orderDTO.getCustomerName(),
                orderDTO.getCustomerEmail(),
                orderDTO.getOrderStatus()
        );
    }


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ResponseEntity<EntityModel<OrderResponse>> createOrder(OrderRequest orderRequest) {
        OrderDTO mappedOrderDTO = mapToOrderDTO(orderRequest);

        OrderDTO createdOrder = orderService.createOrder(mappedOrderDTO);

        OrderResponse orderResponse = mapToOrderResponse(createdOrder);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.create", orderResponse);

        EntityModel<OrderResponse> createdOrderEntityModel = EntityModel.of(orderResponse);

        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(orderResponse.id())).withSelfRel());
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrder(orderResponse.id())).withRel("deleteOrder"));
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).updateOrderStatus(orderResponse.id(), "newStatus")).withRel("updateStatus"));

        return ResponseEntity.created(createdOrderEntityModel.getRequiredLink("self").toUri()).body(createdOrderEntityModel);
    }

    //TODO
    @Override
    public ResponseEntity<CollectionModel<EntityModel<OrderResponse>>> getAllOrders() {
        return null;
    }

    //TODO
    @Override
    public ResponseEntity<EntityModel<OrderResponse>> getOrderById(UUID id) {
        return null;
    }

    //TODO
    @Override
    public ResponseEntity<EntityModel<OrderResponse>> updateOrderStatus(UUID id, String newStatus) {
        return null;
    }

    //TODO
    @Override
    public ResponseEntity<Boolean> deleteOrder(UUID id) {
        return null;
    }

}
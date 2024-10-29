package com.example.sop.controllers;

import com.example.sop.config.RabbitMQConfiguration;
import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.interfaces.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private RabbitTemplate rabbitTemplate;

/*
    private RabbitMQPublisherService rabbitMQPublisherService;
*/


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

/*    @Autowired
    public void setRabbitMQPublisherService(RabbitMQPublisherService rabbitMQPublisherService) {
        this.rabbitMQPublisherService = rabbitMQPublisherService;
    }*/


    @PostMapping("/create")
    public ResponseEntity<EntityModel<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.create", createdOrder);

        EntityModel<OrderDTO> createdOrderEntityModel = EntityModel.of(createdOrder);

        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(createdOrder.getId())).withSelfRel());
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrder(createdOrder.getId())).withRel("deleteOrder"));
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).updateOrderStatus(createdOrder.getId(), "newStatus")).withRel("updateStatus"));

        return ResponseEntity.created(createdOrderEntityModel.getRequiredLink("self").toUri()).body(createdOrderEntityModel);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders() {
        List<EntityModel<OrderDTO>> allOrdersEntityModels = orderService.getAllOrders()
                .stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).deleteOrder(order.getId())).withRel("deleteOrder"),
                        linkTo(methodOn(OrderController.class).updateOrderStatus(order.getId(), "newStatus")).withRel("updateStatus"),
                        linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders")))
                .toList();

        CollectionModel<EntityModel<OrderDTO>> allOrdersCollectionModel = CollectionModel.of(allOrdersEntityModels);

        return ResponseEntity.ok(allOrdersCollectionModel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> updateOrderStatus(@PathVariable UUID id, @RequestParam String newStatus) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, newStatus);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.update.status", updatedOrder);

        EntityModel<OrderDTO> updatedOrderEntityModel = EntityModel.of(updatedOrder);

        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrder(updatedOrder.getId())).withRel("deleteOrder"));


        return ResponseEntity.ok(updatedOrderEntityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderById(@PathVariable UUID id) {
        OrderDTO orderById = orderService.getOrderById(id);

        EntityModel<OrderDTO> orderByIdEntityModel = EntityModel.of(orderById);

        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).deleteOrder(orderById.getId())).withRel("deleteOrder"));
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).updateOrderStatus(orderById.getId(), "newStatus")).withRel("updateStatus"));

        return ResponseEntity.ok(orderByIdEntityModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrderById(id);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "orders.delete", "Successfully deleted order with order ID: " + id);

        return ResponseEntity.noContent().build();
    }

}
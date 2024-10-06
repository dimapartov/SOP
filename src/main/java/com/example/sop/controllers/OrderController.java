package com.example.sop.controllers;

import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.interfaces.OrderService;
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

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<EntityModel<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        EntityModel<OrderDTO> createdOrderEntityModel = EntityModel.of(createdOrder);

        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(createdOrder.getId())).withSelfRel());
        createdOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));

        return ResponseEntity.created(createdOrderEntityModel.getRequiredLink("self").toUri()).body(createdOrderEntityModel);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders() {
        List<EntityModel<OrderDTO>> allOrdersEntityModels = orderService.getAllOrders()
                .stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders")))
                .toList();

        CollectionModel<EntityModel<OrderDTO>> allOrdersCollectionModel = CollectionModel.of(allOrdersEntityModels);

        return ResponseEntity.ok(allOrdersCollectionModel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> updateOrderStatus(@PathVariable UUID id, @RequestParam String newStatus) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, newStatus);

        EntityModel<OrderDTO> updatedOrderEntityModel = EntityModel.of(updatedOrder);

        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        updatedOrderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));

        return ResponseEntity.ok(updatedOrderEntityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderById(@PathVariable UUID id) {
        OrderDTO orderById = orderService.getOrderById(id);

        EntityModel<OrderDTO> orderByIdEntityModel = EntityModel.of(orderById);

        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        orderByIdEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("allOrders"));

        return ResponseEntity.ok(orderByIdEntityModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.noContent().build();
    }

}
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
import java.util.stream.Collectors;

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

        EntityModel<OrderDTO> orderResource = EntityModel.of(createdOrder);
        orderResource.add(linkTo(methodOn(OrderController.class).getOrderById(createdOrder.getId())).withSelfRel());
        orderResource.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("all-orders"));

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).getOrderById(createdOrder.getId())).toUri())
                .body(orderResource);
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<OrderDTO>> getAllOrders() {
        List<EntityModel<OrderDTO>> orders = orderService.getAllOrders().stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).getAllOrders()).withRel("all-orders")))
                .collect(Collectors.toList());

        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> updateOrderStatus(@PathVariable UUID id, @RequestParam String newStatus) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, newStatus);

        EntityModel<OrderDTO> orderResource = EntityModel.of(updatedOrder);
        orderResource.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        orderResource.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("all-orders"));

        return ResponseEntity.ok(orderResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderById(@PathVariable UUID id) {
        OrderDTO orderDTO = orderService.getOrderById(id);

        EntityModel<OrderDTO> orderResource = EntityModel.of(orderDTO);
        orderResource.add(linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        orderResource.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("all-orders"));

        return ResponseEntity.ok(orderResource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.noContent().build();
    }
}
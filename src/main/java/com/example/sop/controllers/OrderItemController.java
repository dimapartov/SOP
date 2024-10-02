package com.example.sop.controllers;

import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/*@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private OrderItemService orderItemService;

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @PostMapping("/create")
    public ResponseEntity<EntityModel<OrderItemCreationDTO>> createOrderItem(@RequestBody OrderItemCreationDTO orderItemCreationDTO) {
        OrderItemCreationDTO createdOrderItem = orderItemService.createOrderItem(orderItemCreationDTO);
        EntityModel<OrderItemCreationDTO> resource = EntityModel.of(createdOrderItem);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderItemController.class)
                .getOrderItemById(createdOrderItem.getId())).withSelfRel();
        resource.add(selfLink);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<EntityModel<OrderItemDTO>>> getAllOrderItemsByOrderId(@PathVariable UUID orderId) {
        List<OrderItemDTO> orderItems = orderItemService.getAllOrderItemsByOrderId(orderId);
        List<EntityModel<OrderItemDTO>> resources = orderItems.stream()
                .map(orderItem -> {
                    EntityModel<OrderItemDTO> resource = EntityModel.of(orderItem);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderItemController.class)
                            .getOrderItemById(orderItem.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderItemById(@PathVariable UUID id) {
        orderItemService.deleteOrderItemById(id);
        return ResponseEntity.noContent().build();
    }

}*/

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private OrderItemService orderItemService;

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<EntityModel<OrderItemCreationDTO>> createOrderItem(@RequestBody OrderItemCreationDTO orderItemCreationDTO) {
        OrderItemCreationDTO createdOrderItem = orderItemService.createOrderItem(orderItemCreationDTO);
        EntityModel<OrderItemCreationDTO> resource = EntityModel.of(createdOrderItem);

        // Self link to the created order item - not needed if no getOrderItemById method
        // Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderItemController.class)
        //        .getOrderItemById(createdOrderItem.getId())).withSelfRel();
        // resource.add(selfLink);

        // Link to the corresponding order using orderId
        Link orderLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderItemController.class)
                .getAllOrderItemsByOrderId(createdOrderItem.getOrderId())).withRel("order-items-by-order");
        resource.add(orderLink);

        // Link to delete the created order item
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderItemController.class)
                .deleteOrderItemById(createdOrderItem.getId())).withRel("delete");
        resource.add(deleteLink);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<EntityModel<OrderItemDTO>>> getAllOrderItemsByOrderId(@PathVariable UUID orderId) {
        List<OrderItemDTO> orderItems = orderItemService.getAllOrderItemsByOrderId(orderId);
        List<EntityModel<OrderItemDTO>> resources = orderItems.stream()
                .map(orderItem -> {
                    EntityModel<OrderItemDTO> resource = EntityModel.of(orderItem);
                    // Links for each order item if needed
                    // Example: Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderItemController.class)
                    //        .getOrderItemById(orderItem.getId())).withSelfRel();
                    // resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderItemById(@PathVariable UUID id) {
        orderItemService.deleteOrderItemById(id);
        return ResponseEntity.noContent().build();
    }

    // Optional: You could add additional methods as necessary for future functionality.
}
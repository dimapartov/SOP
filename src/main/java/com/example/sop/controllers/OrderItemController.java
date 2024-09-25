/*
package com.example.sop.controllers;

import com.example.sop.services.OrderItemService;
import com.example.sop.services.dtos.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    private OrderItemService orderItemService;


    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @PostMapping("/create")
    public ResponseEntity<EntityModel<OrderItemDTO>> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        OrderItemDTO createdOrderItem = orderItemService.createOrderItem(orderItemDTO);

        EntityModel<OrderItemDTO> resource = EntityModel.of(createdOrderItem);

        resource.add(linkTo(methodOn(OrderItemController.class).deleteOrderItemById(createdOrderItem.getId())).withRel("deleteOrderItem"));

        */
/*resource.add(linkTo(methodOn(PartController.class).getPartById(createdOrderItem.getId())).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        resource.add(linkTo(methodOn(PartController.class).deletePartById(createdOrderItem.getId())).withRel("deletePart"));
        resource.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(createdOrderItem.getId(), 0)).withRel("changeQuantity"));*//*


        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderItemById(@PathVariable UUID id) {
        orderItemService.deleteOrderItemById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}*/
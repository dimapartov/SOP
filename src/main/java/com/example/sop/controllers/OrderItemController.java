package com.example.sop.controllers;

import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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

        EntityModel<OrderItemCreationDTO> createdOrderItemEntityModel = EntityModel.of(createdOrderItem);

        createdOrderItemEntityModel.add(linkTo(methodOn(OrderItemController.class).getOrderItemById(createdOrderItem.getId())).withSelfRel());
        createdOrderItemEntityModel.add(linkTo(methodOn(OrderItemController.class).getAllOrderItemsByOrderId(orderItemCreationDTO.getOrderId())).withRel("allOrderItemsByOrderId"));
        createdOrderItemEntityModel.add(linkTo(methodOn(OrderItemController.class).deleteOrderItemById(createdOrderItem.getId())).withRel("deleteOrderItem"));

        return ResponseEntity.created(createdOrderItemEntityModel.getRequiredLink("self").toUri()).body(createdOrderItemEntityModel);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<EntityModel<OrderItemDTO>>> getAllOrderItemsByOrderId(@PathVariable UUID orderId) {
        List<OrderItemDTO> orderItemsByOrderId = orderItemService.getAllOrderItemsByOrderId(orderId);

        List<EntityModel<OrderItemDTO>> orderItemsByOrderIdEntityModels = orderItemsByOrderId.stream()
                .map(orderItem -> {
                    EntityModel<OrderItemDTO> orderItemEntityModel = EntityModel.of(orderItem);
                    Link employeeLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class)
                            .getEmployeeById(orderItem.getOrder().getEmployeeId())).withRel("employeeInCharge");
                    orderItemEntityModel.add(employeeLink);
                    return orderItemEntityModel;
                })
                .toList();

        return ResponseEntity.ok(orderItemsByOrderIdEntityModels);
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<EntityModel<OrderItemDTO>> getOrderItemById(@PathVariable UUID orderItemId) {
        OrderItemDTO orderItemById = orderItemService.getOrderItemById(orderItemId);

        EntityModel<OrderItemDTO> orderItemByIdEntityModel = EntityModel.of(orderItemById);

        orderItemByIdEntityModel.add(linkTo(methodOn(OrderItemController.class).getAllOrderItemsByOrderId(orderItemById.getOrder().getId())).withRel("allOrderItemsByOrderId"));
        orderItemByIdEntityModel.add(linkTo(methodOn(OrderItemController.class).deleteOrderItemById(orderItemById.getId())).withRel("deleteOrderItem"));

        return ResponseEntity.ok(orderItemByIdEntityModel);
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItemById(@PathVariable UUID orderItemId) {
        orderItemService.deleteOrderItemById(orderItemId);

        return ResponseEntity.noContent().build();
    }

}
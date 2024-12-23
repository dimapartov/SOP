package com.example.sop.controllers;

import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import com.example.sopcontracts.controllers.OrderItemApi;
import com.example.sopcontracts.dtos.OrderItemRequest;
import com.example.sopcontracts.dtos.OrderItemResponse;
import com.example.sopcontracts.dtos.OrderResponse;
import com.example.sopcontracts.dtos.PartResponse;
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
public class OrderItemController implements OrderItemApi {

    private OrderItemService orderItemService;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // --- Utility methods for DTO mapping ---
    private OrderItemCreationDTO mapToOrderItemCreationDTO(OrderItemRequest orderItemRequest) {
        return new OrderItemCreationDTO(orderItemRequest.orderId(), orderItemRequest.partId(), orderItemRequest.quantity());
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItemDTO orderItemDTO) {
        return new OrderItemResponse(
                orderItemDTO.getId(),
                new OrderResponse(
                        orderItemDTO.getOrder().getId(),
                        orderItemDTO.getOrder().getEmployeeId(),
                        orderItemDTO.getOrder().getCustomerName(),
                        orderItemDTO.getOrder().getCustomerEmail(),
                        orderItemDTO.getOrder().getOrderStatus()
                ),
                new PartResponse(
                        orderItemDTO.getPart().getId(),
                        orderItemDTO.getPart().getName(),
                        orderItemDTO.getPart().getQuantityOnStorage(),
                        orderItemDTO.getPart().getPrice()
                ),
                orderItemDTO.getQuantity()
        );
    }

    // --- Implemented API Methods ---

    // Create an Order Item
    @Override
    public ResponseEntity<EntityModel<OrderItemResponse>> createOrderItem(OrderItemRequest orderItemRequest) {
        // Преобразуем запрос в DTO для передачи в сервис
        OrderItemCreationDTO orderItemCreationDTO = mapToOrderItemCreationDTO(orderItemRequest);

        // Создаем OrderItem через сервис и получаем полностью заполненный OrderItemDTO
        OrderItemDTO createdOrderItem = orderItemService.createOrderItem(orderItemCreationDTO);

        // Преобразуем OrderItemDTO в OrderItemResponse
        OrderItemResponse orderItemResponse = mapToOrderItemResponse(createdOrderItem);

        // Оборачиваем ответ в формат HATEOAS
        EntityModel<OrderItemResponse> createdOrderItemEntity = EntityModel.of(orderItemResponse);
        createdOrderItemEntity.add(linkTo(methodOn(OrderItemController.class).getOrderItemById(orderItemResponse.id())).withSelfRel());
        createdOrderItemEntity.add(linkTo(methodOn(OrderItemController.class).getAllOrderItemsByOrderId(orderItemRequest.orderId())).withRel("allOrderItems"));
        createdOrderItemEntity.add(linkTo(methodOn(OrderItemController.class).deleteOrderItemById(orderItemResponse.id())).withRel("deleteOrderItem"));

        // Возвращаем ответ с HATEOAS ссылками и статусом CREATED
        return ResponseEntity.created(createdOrderItemEntity.getRequiredLink("self").toUri()).body(createdOrderItemEntity);
    }


    // Retrieve all Order Items by Order ID
    @Override
    public ResponseEntity<CollectionModel<EntityModel<OrderItemResponse>>> getAllOrderItemsByOrderId(UUID orderId) {
        List<OrderItemDTO> orderItemDTOs = orderItemService.getAllOrderItemsByOrderId(orderId);

        // Map results to a HATEOAS-enabled collection
        List<EntityModel<OrderItemResponse>> orderItems = orderItemDTOs.stream()
                .map(orderItem -> {
                    OrderItemResponse orderItemResponse = mapToOrderItemResponse(orderItem);
                    return EntityModel.of(orderItemResponse,
                            linkTo(methodOn(OrderItemController.class).getOrderItemById(orderItem.getId())).withSelfRel(),
                            linkTo(methodOn(OrderItemController.class).deleteOrderItemById(orderItem.getId())).withRel("deleteOrderItem"),
                            linkTo(methodOn(OrderItemController.class).getAllOrderItemsByOrderId(orderId)).withRel("allOrderItems"));
                }).toList();

        return ResponseEntity.ok(CollectionModel.of(orderItems));
    }

    // Retrieve a specific Order Item by ID
    @Override
    public ResponseEntity<EntityModel<OrderItemResponse>> getOrderItemById(UUID orderItemId) {
        OrderItemDTO orderItemDTO = orderItemService.getOrderItemById(orderItemId);
        OrderItemResponse orderItemResponse = mapToOrderItemResponse(orderItemDTO);

        EntityModel<OrderItemResponse> responseEntity = EntityModel.of(orderItemResponse);
        responseEntity.add(linkTo(methodOn(OrderItemController.class).getOrderItemById(orderItemId)).withSelfRel());
        responseEntity.add(linkTo(methodOn(OrderItemController.class).getAllOrderItemsByOrderId(orderItemResponse.order().id())).withRel("allOrderItems"));
        responseEntity.add(linkTo(methodOn(OrderItemController.class).deleteOrderItemById(orderItemId)).withRel("deleteOrderItem"));

        return ResponseEntity.ok(responseEntity);
    }

    // Delete an Order Item by ID
    @Override
    public ResponseEntity<String> deleteOrderItemById(UUID orderItemId) {
        orderItemService.deleteOrderItemById(orderItemId);
        String deletionSucceededMessage = "Successfully deleted order item with ID: " + orderItemId;

        return ResponseEntity.ok(deletionSucceededMessage);
    }

}
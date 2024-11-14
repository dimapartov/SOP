package com.example.sop.graphql;

import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.interfaces.OrderItemService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;


@DgsComponent
public class OrderItemDataFetcher {

    private OrderItemService orderItemService;


    @Autowired
    public OrderItemDataFetcher(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @DgsQuery
    public OrderItemDTO getOrderItemById(String orderItemId) {
        return orderItemService.getOrderItemById(UUID.fromString(orderItemId));
    }

    @DgsQuery
    public List<OrderItemDTO> getAllOrderItemsByOrderId(String orderId) {
        return orderItemService.getAllOrderItemsByOrderId(UUID.fromString(orderId));
    }

    @DgsMutation
    public OrderItemCreationDTO createOrderItem(String orderId, String partId, int quantity) {
        OrderItemCreationDTO newOrderItem = new OrderItemCreationDTO();

        newOrderItem.setOrderId(UUID.fromString(orderId));
        newOrderItem.setPartId(UUID.fromString(partId));
        newOrderItem.setQuantity(quantity);

        return orderItemService.createOrderItem(newOrderItem);
    }

    @DgsMutation
    public Boolean deleteOrderItemById(String orderItemId) {
        orderItemService.deleteOrderItemById(UUID.fromString(orderItemId));
        return true;
    }

}
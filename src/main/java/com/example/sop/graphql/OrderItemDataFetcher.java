package com.example.sop.graphql;

import com.example.sop.services.dtos.OrderDTO;
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
    public OrderItemDTO getOrderItemById(String id) {
        return orderItemService.getOrderItemById(UUID.fromString(id));
    }

    @DgsQuery
    public List<OrderItemDTO> getAllOrderItemsByOrderId(String id) {
        return orderItemService.getAllOrderItemsByOrderId(UUID.fromString(id));
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
    public Boolean deleteOrderItemById(String orderId) {
        orderItemService.deleteOrderItemById(UUID.fromString(orderId));
        return true;
    }

}
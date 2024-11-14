package com.example.sop.graphql;

import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.interfaces.OrderService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;


@DgsComponent
public class OrderDataFetcher {

    private OrderService orderService;


    @Autowired
    public OrderDataFetcher(OrderService orderService) {
        this.orderService = orderService;
    }


    @DgsQuery
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DgsQuery
    public OrderDTO getOrderById(String orderId) {
        return orderService.getOrderById(UUID.fromString(orderId));
    }

    @DgsMutation
    public OrderDTO createOrder(String employeeId, String customerName, String customerEmail) {
        OrderDTO newOrder = new OrderDTO();
        newOrder.setEmployeeId(UUID.fromString(employeeId));
        newOrder.setCustomerName(customerName);
        newOrder.setCustomerEmail(customerEmail);

        return orderService.createOrder(newOrder);
    }

    @DgsMutation
    public OrderDTO updateOrderStatus(String orderId, String newStatus) {
        return orderService.updateOrderStatus(UUID.fromString(orderId), newStatus);
    }

    @DgsMutation
    public Boolean deleteOrderById(String orderId) {
        orderService.deleteOrderById(UUID.fromString(orderId));
        return true;
    }

}
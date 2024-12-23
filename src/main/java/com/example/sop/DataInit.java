package com.example.sop;

import com.example.sop.services.dtos.*;
import com.example.sop.services.interfaces.EmployeeService;
import com.example.sop.services.interfaces.OrderItemService;
import com.example.sop.services.interfaces.OrderService;
import com.example.sop.services.interfaces.PartService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class DataInit implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final PartService partService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @Autowired
    public DataInit(EmployeeService employeeService,
                    PartService partService,
                    OrderItemService orderItemService,
                    OrderService orderService) {
        this.employeeService = employeeService;
        this.partService = partService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
//        seedData();
    }

    private void seedData() {
        Faker faker = new Faker();

        // Existing Employees
        EmployeeDTO employee1 = new EmployeeDTO("Dima", "Kubarev", "dimnakubarev@mail.ru", "11111111111");
        EmployeeDTO employee2 = new EmployeeDTO("Denis", "Denisov", "denisdenisov@mail.ru", "22222222222");
        employeeService.createEmployee(employee1);
        employeeService.createEmployee(employee2);

        // Generate additional Employees
        for (int i = 0; i < 8; i++) {
            EmployeeDTO employee = new EmployeeDTO(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber()
            );
            employeeService.createEmployee(employee);
        }
        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();

        // Existing Parts
        PartDTO part1 = new PartDTO("Exhaust", 25, BigDecimal.valueOf(122));
        PartDTO part2 = new PartDTO("Wheel", 123, BigDecimal.valueOf(545345));
        partService.createPart(part1);
        partService.createPart(part2);

        // Generate additional Parts
        for (int i = 0; i < 8; i++) {
            PartDTO part = new PartDTO(
                    faker.commerce().productName(),
                    ThreadLocalRandom.current().nextInt(1, 100),
                    BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(50, 1000))
            );
            partService.createPart(part);
        }
        List<PartDTO> allParts = partService.getAllParts();

        // Existing Orders
        OrderDTO order1 = new OrderDTO(allEmployees.get(0).getId(), "Dmitrii", "uvp112.kubarev.d@mail.ru");
        orderService.createOrder(order1);

        // Generate additional Orders
        for (int i = 0; i < 9; i++) {
            UUID randomEmployeeId = allEmployees.get(ThreadLocalRandom.current().nextInt(allEmployees.size())).getId();
            OrderDTO order = new OrderDTO(
                    randomEmployeeId,
                    faker.name().fullName(),
                    faker.internet().emailAddress()
            );
            orderService.createOrder(order);
        }
        List<OrderDTO> allOrders = orderService.getAllOrders();

        // Generate additional Order Items
        for (int i = 0; i < 10; i++) {
            UUID randomOrderId = allOrders.get(ThreadLocalRandom.current().nextInt(allOrders.size())).getId();
            UUID randomPartId = allParts.get(ThreadLocalRandom.current().nextInt(allParts.size())).getId();
            OrderItemCreationDTO orderItem = new OrderItemCreationDTO(
                    randomOrderId,
                    randomPartId,
                    ThreadLocalRandom.current().nextInt(1, 50)
            );

            // Используем возвращаемое значение OrderItemDTO
            orderItemService.createOrderItem(orderItem);
        }
    }

}
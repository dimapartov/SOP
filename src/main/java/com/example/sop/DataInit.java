package com.example.sop;

import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.dtos.OrderItemCreationDTO;
import com.example.sop.services.dtos.PartDTO;
import com.example.sop.services.interfaces.EmployeeService;
import com.example.sop.services.interfaces.OrderItemService;
import com.example.sop.services.interfaces.OrderService;
import com.example.sop.services.interfaces.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class DataInit implements CommandLineRunner {

    private ModelMapper modelMapper;

    private EmployeeRepository employeeRepository;
    private PartRepository partRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    private EmployeeService employeeService;
    private PartService partService;
    private OrderItemService orderItemService;
    private OrderService orderService;


    @Autowired
    public DataInit(ModelMapper modelMapper,
                    EmployeeRepository employeeRepository,
                    PartRepository partRepository,
                    OrderItemRepository orderItemRepository,
                    OrderRepository orderRepository,
                    EmployeeService employeeService,
                    PartService partService,
                    OrderItemService orderItemService,
                    OrderService orderService) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.partRepository = partRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
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
        EmployeeDTO employee1 = new EmployeeDTO("Dima", "Kubarev", "test1@mail.ru", "11111111111");
        EmployeeDTO employee2 = new EmployeeDTO("Denis", "Denisov", "test2@mail.ru", "22222222222");
        employeeService.createEmployee(employee1);
        employeeService.createEmployee(employee2);
        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();

        PartDTO part1 = new PartDTO("Exhaust", 25, BigDecimal.valueOf(122));
        PartDTO part2 = new PartDTO("Wheel", 123, BigDecimal.valueOf(545345));
        partService.createPart(part1);
        partService.createPart(part2);
        List<PartDTO> allParts = partService.getAllParts();

        OrderDTO order1 = new OrderDTO(allEmployees.get(0).getId(), "CustomerName", "customer@mail.ru");
        orderService.createOrder(order1);
        List<OrderDTO> allOrders = orderService.getAllOrders();

        OrderItemCreationDTO orderItem1 = new OrderItemCreationDTO(allOrders.get(0).getId(), allParts.get(0).getId(), 11);
        orderItemService.createOrderItem(orderItem1);
    }

}
package com.example.sop;

import com.example.sop.enums.OrderStatusEnum;
import com.example.sop.models.Employee;
import com.example.sop.models.Order;
import com.example.sop.models.OrderItem;
import com.example.sop.models.Part;
import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.repositories.OrderItemRepository;
import com.example.sop.repositories.OrderRepository;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.EmployeeService;
import com.example.sop.services.OrderItemService;
import com.example.sop.services.OrderService;
import com.example.sop.services.PartService;
import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.dtos.OrderDTO;
import com.example.sop.services.dtos.OrderItemDTO;
import com.example.sop.services.dtos.PartDTO;
import com.example.sop.services.impl.OrderServiceImpl;
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
    public DataInit(ModelMapper modelMapper, EmployeeRepository employeeRepository, PartRepository partRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository, EmployeeService employeeService, PartService partService, OrderItemService orderItemService, OrderService orderService) {
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
    public void run(String... args) throws Exception {


        EmployeeDTO employee1 = new EmployeeDTO("Dima", "Kubarev", "test1@mail.ru", "11111111111");
        Employee employee1ToBeSaved = modelMapper.map(employee1, Employee.class);
        employeeRepository.saveAndFlush(employee1ToBeSaved);

        EmployeeDTO employee2 = new EmployeeDTO("Aboba", "Aboba", "test2@mail.ru", "22222222222");
        Employee employee2ToBeSaved = modelMapper.map(employee2, Employee.class);
        employeeRepository.saveAndFlush(employee2ToBeSaved);

        PartDTO part1 = new PartDTO("Exhaust", 25, BigDecimal.valueOf(122));
        Part part1ToBeSaved = modelMapper.map(part1, Part.class);
        partRepository.saveAndFlush(part1ToBeSaved);

        PartDTO part2 = new PartDTO("Wheel", 123, BigDecimal.valueOf(545345));
        Part part2ToBeSaved = modelMapper.map(part2, Part.class);
        partRepository.saveAndFlush(part2ToBeSaved);

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        EmployeeDTO employeeForOrder = employees.get(0);

        OrderDTO order1 = new OrderDTO(employeeForOrder.getId(), "Dima", "ssdasd@mail.ru", OrderStatusEnum.PROCESSING);
        orderService.createOrder(order1);

        List<OrderDTO> orders = orderService.getAllOrders();
        OrderDTO orderForOrderItem = orders.get(0);

        List<PartDTO> parts = partService.getAllParts();
        PartDTO partForOrderItem = parts.get(0);

        OrderItemDTO orderItem1 = new OrderItemDTO(orderForOrderItem.getId(), partForOrderItem, 52);
        orderItemService.createOrderItem(orderItem1);


    }

}
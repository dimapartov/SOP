package com.example.sop.controllers;

import com.example.sop.services.EmployeeService;
import com.example.sop.services.dtos.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;


    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/createEmployee")
    public EntityModel<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO createEmployeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(createEmployeeDTO);
        return EntityModel.of(createdEmployee,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(createdEmployee.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }

    @GetMapping("/getAllEmployees")
    public CollectionModel<EntityModel<EmployeeDTO>> getAllEmployees() {
        List<EntityModel<EmployeeDTO>> allEmployees = employeeService.getAllEmployees()
                .stream()
                .map(employee -> EntityModel.of(employee,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(allEmployees,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<EmployeeDTO> getEmployeeById(@PathVariable UUID id) {
        EmployeeDTO employeeById = employeeService.getEmployeeById(id);
        return EntityModel.of(employeeById,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }


}
package com.example.sop.controllers;

import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/create")
    public ResponseEntity<EntityModel<EmployeeDTO>> createEmployee(@RequestBody EmployeeDTO createEmployeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(createEmployeeDTO);

        EntityModel<EmployeeDTO> createdEmployeeEntityModel = EntityModel.of(createdEmployee,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(createdEmployee.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(createdEmployee.getId())).withRel("deleteEmployee"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("allEmployees"));

        return ResponseEntity.created(createdEmployeeEntityModel.getRequiredLink("self").toUri()).body(createdEmployeeEntityModel);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<EmployeeDTO>>> getAllEmployees() {
        List<EntityModel<EmployeeDTO>> allEmployees = employeeService.getAllEmployees()
                .stream()
                .map(employee -> EntityModel.of(employee,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(employee.getId())).withRel("deleteEmployee")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<EmployeeDTO>> employeeCollectionModel = CollectionModel.of(allEmployees);

        return ResponseEntity.ok(employeeCollectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDTO>> getEmployeeById(@PathVariable UUID id) {
        EmployeeDTO employeeById = employeeService.getEmployeeById(id);

        EntityModel<EmployeeDTO> employeeByIdEntityModel = EntityModel.of(employeeById,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(id)).withRel("deleteEmployee"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("allEmployees"));

        return ResponseEntity.ok(employeeByIdEntityModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable UUID id) {
        employeeService.deleteEmployeeById(id);

        return ResponseEntity.noContent().build();
    }

}
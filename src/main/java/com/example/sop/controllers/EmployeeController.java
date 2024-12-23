package com.example.sop.controllers;

import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.interfaces.EmployeeService;
import com.example.sopcontracts.controllers.EmployeeApi;
import com.example.sopcontracts.dtos.EmployeeRequest;
import com.example.sopcontracts.dtos.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class EmployeeController implements EmployeeApi {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    private EmployeeDTO mapToEmployeeDTO(EmployeeRequest employeeRequest) {
        return new EmployeeDTO(employeeRequest.firstName(),
                employeeRequest.lastName(),
                employeeRequest.email(),
                employeeRequest.phoneNumber());
    }

    private EmployeeResponse mapToEmployeeResponse(EmployeeDTO employeeDTO) {
        return new EmployeeResponse(employeeDTO.getId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNumber());
    }


    @Override
    public ResponseEntity<EntityModel<EmployeeResponse>> createEmployee(EmployeeRequest employeeRequest) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(mapToEmployeeDTO(employeeRequest));
        EmployeeResponse employeeResponse = mapToEmployeeResponse(createdEmployee);

        EntityModel<EmployeeResponse> employeeEntityModel = EntityModel.of(employeeResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employeeResponse.id())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(employeeResponse.id())).withRel("deleteEmployee"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("allEmployees"));

        return ResponseEntity.created(employeeEntityModel.getRequiredLink("self").toUri()).body(employeeEntityModel);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<EmployeeResponse>>> getAllEmployees() {
        List<EntityModel<EmployeeResponse>> allEmployeesEntityModels = employeeService.getAllEmployees()
                .stream()
                .map(employee -> {
                    EmployeeResponse employeeResponse = mapToEmployeeResponse(employee);
                    return EntityModel.of(employeeResponse,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employeeResponse.id())).withSelfRel(),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(employeeResponse.id())).withRel("deleteEmployee"));
                })
                .toList();

        CollectionModel<EntityModel<EmployeeResponse>> allEmployeesCollectionModel = CollectionModel.of(allEmployeesEntityModels);

        return ResponseEntity.ok(allEmployeesCollectionModel);
    }

    @Override
    public ResponseEntity<EntityModel<EmployeeResponse>> getEmployeeById(UUID employeeId) {
        EmployeeDTO employeeById = employeeService.getEmployeeById(employeeId);
        EmployeeResponse employeeResponse = mapToEmployeeResponse(employeeById);

        EntityModel<EmployeeResponse> employeeEntityModel = EntityModel.of(employeeResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employeeId)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(employeeId)).withRel("deleteEmployee"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("allEmployees"));

        return ResponseEntity.ok(employeeEntityModel);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(UUID employeeId) {
        employeeService.deleteEmployeeById(employeeId);

        String deletionSucceededMessage = "Successfully deleted employee with ID: " + employeeId;

        return ResponseEntity.ok(deletionSucceededMessage);
    }

}
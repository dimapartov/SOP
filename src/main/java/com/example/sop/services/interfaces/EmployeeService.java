package com.example.sop.services.interfaces;

import com.example.sop.services.dtos.EmployeeDTO;

import java.util.List;
import java.util.UUID;


public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(UUID employeeId);

    List<EmployeeDTO> getAllEmployees();

    void deleteEmployeeById(UUID employeeId);

}
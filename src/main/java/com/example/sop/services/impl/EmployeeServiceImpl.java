package com.example.sop.services.impl;

import com.example.sop.models.Employee;
import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sopcontracts.exceptions.EmployeeNotFoundException;
import com.example.sop.services.interfaces.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employeeRepository.saveAndFlush(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(UUID employeeId) {
        Optional<Employee> requestedEmployee = employeeRepository.findById(employeeId);
        if (requestedEmployee.isEmpty()) {
            throw new EmployeeNotFoundException(employeeId);
        }
        return modelMapper.map(requestedEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public void deleteEmployeeById(UUID employeeId) {
        Optional<Employee> requestedEmployee = employeeRepository.findById(employeeId);
        if (requestedEmployee.isEmpty()) {
            throw new EmployeeNotFoundException(employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

}
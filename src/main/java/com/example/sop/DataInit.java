package com.example.sop;

import com.example.sop.models.Employee;
import com.example.sop.models.Part;
import com.example.sop.repositories.EmployeeRepository;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.dtos.PartDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DataInit implements CommandLineRunner {

    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;
    private PartRepository partRepository;

    @Autowired
    public DataInit(ModelMapper modelMapper, EmployeeRepository employeeRepository, PartRepository partRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.partRepository = partRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        EmployeeDTO employee1 = new EmployeeDTO("Dima", "Kubarev", "test@mail.ru","88005554545");
        Employee employee1ToBeSaved = modelMapper.map(employee1, Employee.class);
        employeeRepository.saveAndFlush(employee1ToBeSaved);

        PartDTO part1 = new PartDTO("Exhaust", 25, BigDecimal.valueOf(122));
        Part part1ToBeSaved = modelMapper.map(part1, Part.class);
        partRepository.saveAndFlush(part1ToBeSaved);
    }

}
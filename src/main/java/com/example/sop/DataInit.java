package com.example.sop;

import com.example.sop.models.Employee;
import com.example.sop.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInit implements CommandLineRunner {

    private EmployeeRepository employeeRepository;

    @Autowired
    public DataInit(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee employee1 = new Employee("Dima", "Kubarev", "test@mail.ru","88005554545");
        employeeRepository.saveAndFlush(employee1);
    }

}
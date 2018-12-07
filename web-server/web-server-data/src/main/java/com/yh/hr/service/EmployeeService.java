package com.yh.hr.service;


import com.yh.hr.domain.Employee;
import com.yh.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Employee findByEmpCode(String userCode) {
        return employeeRepository.findByEmpCode(userCode);
    }

}
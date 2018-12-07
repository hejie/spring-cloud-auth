package com.yh.hr.repository;

import com.yh.hr.domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {


    Employee findByEmpCode(String empCode);
}

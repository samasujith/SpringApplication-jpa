package com.sujith.project.service;

import com.sujith.project.dto.*;
import com.sujith.project.entity.*;

import java.util.*;

public interface EmployeeService {
    EmployeeDto findById(int id);

    List<Employee> findAll();

    List<Employee> getByName(String fname);

    Employee save(EmployeeDto theEmployee);

    List<Employee> saveAll(List<Employee> theEmp);

    Employee update(EmployeeDto theEmployee);

    Employee delete(int id);


    int maxSalary();

    List<Employee> findByDepartment(String dept);

    int maxInDept(String dept);

    Employee updateSalaryById(int id, int salary);

    List<Employee> getEmployeesByCourseName(String name);


}

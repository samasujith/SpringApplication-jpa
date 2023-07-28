package com.sujith.project.dao;

import com.sujith.project.entity.*;

import java.util.*;

public interface EmployeeDao {
    Employee findById(int id);

    List<Employee> findAll();

    List<Employee> getByName(String fname);

    Employee save(Employee theEmployee);

    List<Employee> saveAll(List<Employee> theEmp);

    Employee update(Employee theEmployee);

    Employee delete(int id);


    int maxSalary();

    List<Employee> findByDepartment(String dept);

    int maxInDept(String dept);

    Employee updateSalaryById(int id, int salary);

    List<Employee> getEmployeesByCourseName(String name);
}

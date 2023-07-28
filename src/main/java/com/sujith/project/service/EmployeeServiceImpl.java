package com.sujith.project.service;

import com.sujith.project.dao.*;
import com.sujith.project.dto.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.mapper.*;
import jakarta.transaction.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;
import org.springframework.web.method.annotation.*;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeMapper employeeMapper;
    private final CourseDao courseDao;

    @Autowired

    public EmployeeServiceImpl(EmployeeDao employeeDao, EmployeeMapper employeeMapper, CourseDao courseDao) {
        this.employeeDao = employeeDao;

        this.employeeMapper = employeeMapper;
        this.courseDao = courseDao;
    }

    @Override
    public EmployeeDto findById(int id) {
        try {
            if (id <= 0) {
                logger.error("Employee with id : {}  does not exist ! ",id);
                throw new ApiRequestException("Employee not found with given id : " + id + " id is less than or equal to zero");
            } else {
                Employee tempEmp = employeeDao.findById(id);
                if (tempEmp == null) {
                    throw new ApiRequestException("Employee not found with given id : " + id);
                } else {
                    try {
                        return employeeMapper.copyToDao(employeeDao.findById(id));
                    } catch (MethodArgumentTypeMismatchException e) {
                        throw new ApiRequestException("Required Integer but found string");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Null fields not allowed");
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public List<Employee> getByName(String fname) {
        return employeeDao.getByName(fname);
    }

    @Override
    @Transactional
    public Employee save(EmployeeDto theEmployee) {

        try {
            Employee emp = employeeMapper.copyToEmp(theEmployee);
            employeeDao.save(emp);
            return emp;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Null fields not allowed");
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Null values not allowed");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidDataAccessApiUsageException("Fields marked with non null should not be null");
        }
    }

    @Override
    @Transactional
    public List<Employee> saveAll(List<Employee> theEmp) {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee emp : theEmp) {
            if (!StringUtils.isEmpty(emp.getFirstName())) {
                employeeList.add(emp);
            }
            else {
                    logger.error("Employee with lastname {} is not inserted",emp.getLastName());
            }


        }

        return employeeDao.saveAll(employeeList);
    }

    @Override
    @Transactional
    public Employee update(EmployeeDto employee) {
        try {
            Employee theEmployee = employeeMapper.copyToEmp(employee);
            Employee emp = employeeDao.findById(theEmployee.getId());
            emp.setFirstName(theEmployee.getFirstName());
            emp.setLastName(theEmployee.getLastName());
            emp.setSalary(theEmployee.getSalary());
            emp.setExperience(theEmployee.getExperience());
            List<Course> courseList = courseDao.getAll();
            List<Course> tempList = new ArrayList<>();
            List<Course> courses = theEmployee.getCourseList();
            for (Course temp : courses) {
                int count = 0;
                for (Course temp1 : courseList) {
                    if ((temp.getCourseName()).equals(temp1.getCourseName())) {
                        temp.setId(temp1.getId());
                        temp.setCourseName(temp1.getCourseName());
                        tempList.add(temp);
                        count++;
                    }
                }
                if (count > 0) {
                    logger.info("Course already exists !");
                } else {
                    tempList.add(temp);
                }
            }
            emp.setCourseList(tempList);
            return employeeDao.update(theEmployee);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Firstname should not be null");
        } catch (NullPointerException e) {
            throw new ApiRequestException("No Employee was found to Update");
        }
    }

    @Override
    @Transactional
    public Employee delete(int id) {
        try {
            return employeeDao.delete(id);

        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidDataAccessApiUsageException("Employee does not exist");
        }
    }

    @Override
    public int maxSalary() {
        try {
            return employeeDao.maxSalary();
        } catch (NullPointerException e) {
            throw new ApiRequestException("No Employees were present in database");
        }
    }

    @Override
    public List<Employee> findByDepartment(String dept) {
        List<Employee> emp = employeeDao.findByDepartment(dept);
        if (emp.isEmpty()) {
            throw new ApiRequestException("No Employees found in the given department");
        } else {
            return emp;
        }
    }

    @Override
    public int maxInDept(String dept) {
        int max = employeeDao.maxInDept(dept);
        if (max <= 0) {
            throw new ApiRequestException("Employees does not exist in given department");
        } else {
            return max;
        }
    }

    @Override
    @Transactional
    public Employee updateSalaryById(int id, int salary) {
        try {
            return employeeDao.updateSalaryById(id, salary);
        } catch (NullPointerException e) {
            throw new ApiRequestException("Employee not found");
        }
    }

    @Override
    public List<Employee> getEmployeesByCourseName(String name) {
        return employeeDao.getEmployeesByCourseName(name);
    }
}

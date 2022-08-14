package com.lms.model.service;

import com.lms.bean.Employee;

import java.util.List;

public interface EmployeeService {

    Boolean addEmployee(Employee employee);

    Boolean removeEmployee(Integer empID);

    Employee searchEmployee(Integer empID);

    List<Employee> getAllEmployees();

	int checkUser(String email, String pass);

	
}

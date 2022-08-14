package com.lms.service;

import com.lms.bean.Employee;
import com.lms.persistence.EmployeeDao;
import com.lms.persistence.EmployeeDaoImpl;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public Boolean addEmployee(Employee employee) {
        return employeeDao.addEmployee(employee) > 0;
    }

    @Override
    public Boolean removeEmployee(Integer empID) {
        return employeeDao.removeEmployee(empID) > 0;
    }

    @Override
    public Employee searchEmployee(Integer empID) {
        return employeeDao.searchEmployee(empID);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public int checkUser(String email, String pass) {
        return employeeDao.getEId(email, pass);
    }

}

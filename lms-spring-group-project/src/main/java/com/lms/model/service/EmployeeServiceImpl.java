package com.lms.model.service;

import com.lms.bean.Employee;
import com.lms.model.persistence.EmployeeDao;
import com.lms.model.persistence.EmployeeDaoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeDao employeeDao;

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

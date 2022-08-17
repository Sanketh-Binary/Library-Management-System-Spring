package com.lms.model.persistence;

import com.lms.bean.Employee;
import com.lms.model.persistence.helper.EmployeeRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	String pass="wiley";
    @Override
    public Integer addEmployee(Employee employee) {
        int rows = 0;
        String query = "INSERT INTO EMPLOYEE values(?,?,?,?,?)";

		rows = jdbcTemplate.update(query, employee.getEmployeeId(), employee.getEmpFirstName(),employee.getEmpLastName(), employee.getDesignation(),0);

		return rows;
    }

    @Override
    public Integer removeEmployee(int empID) {
        int rows = 0;
        String query = "DELETE FROM EMPLOYEE where employeeId=?";

		 rows = jdbcTemplate.update(query, empID);

		return rows;
    }

    @Override
    public Employee searchEmployee(int empID) {
        Employee employee = null;
        
		try {
		String query="SELECT * FROM EMPLOYEE where EMPLOYEEID=?";
		employee=jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), empID);
		}
		catch(EmptyResultDataAccessException ex) {
			return employee;
		}
		return employee;
          
    
    }

    @Override
    public List<Employee> getAllEmployees() {
    	String query="SELECT * FROM EMPLOYEE";
		List<Employee> empList=jdbcTemplate.query(query, new EmployeeRowMapper());
		
		return empList;
    }

	@Override
	public int getEId(String email,String pass) {
		String qry="Select employeeId,type from user where email=? and pass=?";
		int eid=0;
		boolean type=false;
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lms","root","wiley");
	          PreparedStatement preparedStatement = connection.prepareStatement(qry);) {
			 
			 preparedStatement.setString(1, email);
			 preparedStatement.setString(2, pass);
			 
			 ResultSet resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
	                eid = resultSet.getInt("employeeId");
	                type = resultSet.getBoolean("type");
	               
	            }
			 
		 }catch (SQLException e) {
	            e.printStackTrace();
	        }
		 
		return (eid==0)?-1:((type)?1:0);
	}
}


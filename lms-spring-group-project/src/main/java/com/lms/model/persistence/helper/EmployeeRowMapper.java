package com.lms.model.persistence.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.lms.bean.Employee;
public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int id = resultSet.getInt("employeeId");
		String fn = resultSet.getString("employeeFn");
		String ln = resultSet.getString("employeeLn");
		String desg = resultSet.getString("desg");
		int nob = resultSet.getInt("booksIssued");

		Employee employee = new Employee(id, fn, ln, desg, nob);
		return employee;
	}

}

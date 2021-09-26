package com.bridgelabz.employeepayrollsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;	

public class EmployeePayrollDBService {
private Connection getConnection() throws SQLException {
		
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service_db?useSSL=false";
		String userName = "root";
		String password = "naman1999";
		Connection connection;
		
		System.out.println("Connecting to the database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is Succcessful"+connection);
		
		return connection;
	}
	
	public List<EmployeePayrollData> readData(){
		
		String sql = "SELECT * from employee;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
				
		try (Connection connection = getConnection();){	
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
}

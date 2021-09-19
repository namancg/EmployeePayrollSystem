package com.bridgelabz.employeepayrollsystem;

public class EmployeePayrollData {
	int employeeId;
	String employeeName;
	double employeeSalary;
	
	public EmployeePayrollData(Integer id, String name, Double salary) {
		
		this.employeeId = id;
		this.employeeName = name;
		this.employeeSalary = salary;
	}
	
	@Override
	public String toString() {
		
		return "Employee Details are :Employee Id: "+employeeId+" Employee Name: "+employeeName+" Employee Salary: "+employeeSalary;
	}
}

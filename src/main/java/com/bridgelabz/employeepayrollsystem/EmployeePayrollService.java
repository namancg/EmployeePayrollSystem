package com.bridgelabz.employeepayrollsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	public enum IOService 
	{
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmployeePayrollData> employeePayrollList;
	public EmployeePayrollService() 
	{
	}

	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) 
	{
		this.employeePayrollList = employeePayrollList;
	}
	public static void main(String[] args) {
		
		System.out.println("Welcome To Employee Payroll Application");
		ArrayList<EmployeePayrollData> employeePayrollList  = new ArrayList<EmployeePayrollData>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
		
	}
	private void readEmployeePayrollData(Scanner consoleInputReader) {
		
		System.out.println("Enter Employee Id : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter Employee Salary : ");
		double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	
	void writeEmployeePayrollData(IOService ioservice) {
		if(ioservice.equals(IOService.CONSOLE_IO))
			System.out.println("Writing Employee Payroll to Console\n"+ employeePayrollList);
		else if(ioservice.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}
	
	
}
}

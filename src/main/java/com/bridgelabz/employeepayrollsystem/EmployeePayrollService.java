package com.bridgelabz.employeepayrollsystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;
import java.time.LocalDate;
import com.bridgelabz.employeepayrollsystem.EmployeePayrollService.IOService;

public class EmployeePayrollService {

	private List<EmployeePayrollData> employeePayrollList;
	private EmployeePayrollDBService employeePayrollDBService;
	public enum IOService 
	{
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	public EmployeePayrollService() {
	}	
	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> employeePayrollList  = new ArrayList<EmployeePayrollData>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		
		employeePayrollService.readEmployeePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);		
	}
	
	private void readEmployeePayrollData(Scanner consoleInputReader)
	{
		
		System.out.println("Enter the Id : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter the Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter the Salary : ");
		Double salary = consoleInputReader.nextDouble();
		
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	private EmployeePayrollData getEmployeePayrollData(String name) {
		
		return this.employeePayrollList.stream()
				.filter(EmployeePayrollDataItem -> EmployeePayrollDataItem.employeeName.equals(name))
				.findFirst()
				.orElse(null);
	}

	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) 
	{
		this.employeePayrollList = employeePayrollList;
	}
	public void printData(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) new EmployeePayrollFileIOService().printData();
	}
	public long countEntries(IOService fileIo) 
	{
		if(fileIo.equals(IOService.FILE_IO)) 
			return new EmployeePayrollFileIOService().countEntries();
		
		return 0;
	}
	
	public void writeEmployeePayrollData(IOService ioService) 
	{
		if(ioService.equals(IOService.CONSOLE_IO))
			System.out.println("Writing " + employeePayrollList);
		
		else if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}	
	public long readDataFromFile(IOService fileIo)
	{
		
		List<String> employeePayrollFromFile = new ArrayList<String>();
		if(fileIo.equals(IOService.FILE_IO)) {
			System.out.println("Employee Details from payroll-file.txt");
			employeePayrollFromFile = new EmployeePayrollFileIOService().readDataFromFile();
			
		}
		return employeePayrollFromFile.size();
	}
	
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) 
	{
		
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = new EmployeePayrollDBService().readData();
		return this.employeePayrollList;
		
	}
	public void updateEmployeeSalary(String name, double salary) {
		try {
			int result = new EmployeePayrollDBService().updateEmployeeSalary(name, salary);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void updateEmployeeSalaryUsingStatement(String name, double salary) throws SQLException {
		
		int result = employeePayrollDBService.updateEmployeeDataUsingStatement(name,salary);
		if(result == 0) 
			return;
		
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if(employeePayrollData != null)
			employeePayrollData.employeeSalary = salary;		
	}
	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		
		List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}
	public List<EmployeePayrollData> getEmployeeDetailsBasedOnName(IOService ioService, String name) {
	if(ioService.equals(IOService.DB_IO))
		this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnNameUsingStatement(name);
	return this.employeePayrollList;
}
	
}

package com.bridgelabz.employeepayrollsystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class EmployeePayrollService {

	private List<EmployeePayrollData> employeePayrollList;
	private EmployeePayrollDBService employeePayrollDBService;
	public enum IOService 
	{
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList)
	{
		this.employeePayrollList = employeePayrollList;
	}
	
	public EmployeePayrollService()
	{
		employeePayrollDBService =  EmployeePayrollDBService.getInstance();
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
			if(result == 0) 
				return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void updateEmployeeSalaryUsingStatement(String name, double salary) throws SQLException 
	{
		
		int result = employeePayrollDBService.updateEmployeeDataUsingStatement(name,salary);
		if(result == 0) 
			return;
		
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if(employeePayrollData != null)
			employeePayrollData.employeeSalary = salary;		
	}
	public boolean checkEmployeePayrollInSyncWithDB(String name) 
	{
		
		List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}
	public List<EmployeePayrollData> getEmployeeDetailsBasedOnName(IOService ioService, String name)
	{
		if(ioService.equals(IOService.DB_IO)) {
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnNameUsingStatement(name);
			System.out.println("employee details based on name");
			displayData();
		}
		return this.employeePayrollList;
	}
	public List<EmployeePayrollData> getEmployeeDetailsBasedOnStartDate(IOService ioService, String startDate) 
	{
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnStartDateUsingStatement(startDate);
		System.out.println("employee details based on date range");
		displayData();
		return this.employeePayrollList;
	}
	
	private void displayData() 
	{
		employeePayrollDBService.displayDate();
		
	}
    public List<EmployeePayrollData> getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(IOService ioService, String startDate) 
    {
		
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(startDate);
		return this.employeePayrollList;
	}
    public List<Double> getSumOfSalaryBasedOnGender(IOService ioService) 
    {
		
		List<Double> sumOfSalaryBasedOnGender = new ArrayList<Double>();
		if(ioService.equals(IOService.DB_IO))
			sumOfSalaryBasedOnGender = employeePayrollDBService.getSumOfSalaryBasedOnGenderUsingStatement();
		return sumOfSalaryBasedOnGender;	
	}

	public List<Double> getAverageOfSalaryBasedOnGender(IOService ioService)
	{
		List<Double> averageOfSalaryBasedOnGender = new ArrayList<Double>();
		if(ioService.equals(IOService.DB_IO))
			averageOfSalaryBasedOnGender = employeePayrollDBService.getAverageOfSalaryBasedOnGenderUsingStatement();
		return averageOfSalaryBasedOnGender;
	}

	public List<Double> getMinimumSalaryBasedOnGender(IOService ioService)
	{
		List<Double> minimumSalaryBasedOnGender = new ArrayList<Double>();
		if(ioService.equals(IOService.DB_IO))
			minimumSalaryBasedOnGender = employeePayrollDBService.getMinimumSalaryBasedOnGenderUsingStatement();
		return minimumSalaryBasedOnGender;
	}
	
	public List<Double> getMaximumSalaryBasedOnGender(IOService ioService) 
	{
		List<Double> maximumSalaryBasedOnGender = new ArrayList<Double>();
		if(ioService.equals(IOService.DB_IO))
			maximumSalaryBasedOnGender = employeePayrollDBService.getMaximumSalaryBasedOnGenderUsingStatement();
		return maximumSalaryBasedOnGender;
	}
	public List<Integer> getCountOfEmployeesBasedOnGender(IOService ioService)
	{
		List<Integer> countBasedOnGender = new ArrayList<Integer>();
		if(ioService.equals(IOService.DB_IO))
			countBasedOnGender = employeePayrollDBService.getCountOfEmployeesBasedOnGenderUsingStatement();
		return countBasedOnGender;
	}
	
	
}

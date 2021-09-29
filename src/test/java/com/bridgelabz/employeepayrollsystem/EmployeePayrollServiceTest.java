package com.bridgelabz.employeepayrollsystem;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.employeepayrollsystem.EmployeePayrollService.IOService;
public class EmployeePayrollServiceTest {
	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData( 1, "Jeff Bezos", 100000.0,LocalDate.now()),
				new EmployeePayrollData( 2, "Bill Gates",  200000.0,LocalDate.now()),
				new EmployeePayrollData( 3, "Mark Zuckerberg", 300000.0,LocalDate.now())
		};
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
		employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
		assertEquals( 3, entries);
		
	}
	@Test
	public void givenFileOnReadingFromFileShouldMatchEmployeeCount() {
	EmployeePayrollService employeePayrollService = new EmployeePayrollService();
	long entries = employeePayrollService.readDataFromFile(IOService.FILE_IO);
	Assert.assertEquals(3, entries);
	}
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Assert.assertEquals(8, employeePayrollData.size());
	}
	@Test 
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB()
	{	
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Jeff Bezoz",80000.0);
		
	}
	@Test
	public void givenName_WhenFound_ShouldReturnEmployeeDetails()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		String name = "NAMAN";
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.getEmployeeDetailsBasedOnName(IOService.DB_IO, name);
		String resultName = employeePayrollData.get(0).employeeName;
		Assert.assertEquals(name, resultName);
	}
	
	@Test
	public void givenStartDateRange_WhenMatches_ShouldReturnEmployeeDetails()
	{
		String startDate = "2021-08-28";
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.getEmployeeDetailsBasedOnStartDate(IOService.DB_IO, startDate);
		Assert.assertEquals(2, employeePayrollData.size());
	}
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnSumOfSalaryBasedOnGender() 
	{
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<Double> expectedSalarySum = new ArrayList<Double>();
		expectedSalarySum.add(254000.00);
		expectedSalarySum.add(203000.00);
		List<Double> sumOfSalaryBasedOnGender = employeePayrollService.getSumOfSalaryBasedOnGender(IOService.DB_IO);
		if(sumOfSalaryBasedOnGender.size() == 2) {
			Assert.assertEquals(expectedSalarySum, sumOfSalaryBasedOnGender);
		}
		
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnAverageOfSalaryBasedOnGender() 
	{
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<Double> expectedSalaryAverage = new ArrayList<Double>();
		expectedSalaryAverage.add(50800.00);
		expectedSalaryAverage.add(67666.66666666667);
		List<Double> averageOfSalaryBasedOnGender = employeePayrollService.getAverageOfSalaryBasedOnGender(IOService.DB_IO);
		if(averageOfSalaryBasedOnGender.size() == 2) {
			Assert.assertEquals(expectedSalaryAverage, averageOfSalaryBasedOnGender);
		}
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnMinimumSalaryBasedOnGender() 
	{
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<Double> expectedMinimumSalary = new ArrayList<Double>();
		expectedMinimumSalary.add(10000.00);
		expectedMinimumSalary.add(57000.00);
		List<Double> minimumSalaryBasedOnGender = employeePayrollService.getMinimumSalaryBasedOnGender(IOService.DB_IO);
		if(minimumSalaryBasedOnGender.size() == 2) {
			Assert.assertEquals(expectedMinimumSalary, minimumSalaryBasedOnGender);
		}
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnMaximumSalaryBasedOnGender() 
	{
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<Double> expectedMaximumSalary = new ArrayList<Double>();
		expectedMaximumSalary.add(70000.00);
		expectedMaximumSalary.add(79000.00);
		List<Double> maximumSalaryBasedOnGender = employeePayrollService.getMaximumSalaryBasedOnGender(IOService.DB_IO);
		if(maximumSalaryBasedOnGender.size() == 2) {
			Assert.assertEquals(expectedMaximumSalary, maximumSalaryBasedOnGender);
		}
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnCountOfBasedOnGender() 
	{
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<Integer> expectedCountBasedOnGender = new ArrayList<Integer>();
		expectedCountBasedOnGender.add(5);
		expectedCountBasedOnGender.add(3);
		List<Integer> countBasedOnGender = employeePayrollService.getCountOfEmployeesBasedOnGender(IOService.DB_IO);
		if(countBasedOnGender.size() == 2) {
			Assert.assertEquals(expectedCountBasedOnGender, countBasedOnGender);
		}
	}
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeeToPayroll(8, "Mark", 5000000.00, 1234567890, LocalDate.now(), "M", 1);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertEquals(true,result);
	}
	 @Test
	    public void givenNewEmployeePayrollData_WhenCorrect_InsertToEmployeeAndPayrollTable(){
	        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
	        Employee employee=new Employee(3,"SHUBHA","F","15/76, EAT STREET", 9349399223L,LocalDate.of(2021,8,20),301);
	        Payroll updatedPayroll=employeePayrollService.insertEmployeePayrollValues(employee,20000.00);
	        boolean result = employeePayrollService.compareEmployeePayrollInsertSync(employee.getName(),updatedPayroll);
	        Assert.assertTrue(result);
	    }
	 @Test
	    public void givenNewEmployeePayrollDataWithDepartment_WhenCorrect_InsertToEmployeeAndPayrollTable(){
	        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
	        Employee employee=new Employee(4,"VISMAYA","F","BANASHANKARI", 910026270L,LocalDate.of(2021,8,21),300);
	        Department department = new Department(1,"ENGINEER");
	        employee.setDepartmentList(List.of(department));
	        Payroll updatedPayroll=employeePayrollService.insertEmployeePayrollValues(employee,20000.00);
	        boolean result = employeePayrollService.compareEmployeePayrollInsertSync(employee.getName(),updatedPayroll);
	        Assert.assertTrue(result);
	    }
	 @Test
	 public void givenEmployeePayroll_WhenDeleteRecord_ShouldeSyncWithDB(){
	 	
	 	EmployeePayrollService employeePayrollService = new EmployeePayrollService();
	 	employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
	 	employeePayrollService.deleteEmployeeToPayroll("NAMAN");
	 	int result = employeePayrollService.checkedRecordDeletedFromDatabase("NAMAN");
	 	Assert.assertEquals(0, result);
	 }
}
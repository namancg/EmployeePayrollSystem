package com.bridgelabz.employeepayrollsystem;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
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
		Assert.assertEquals(2, employeePayrollData.size());
	}
}
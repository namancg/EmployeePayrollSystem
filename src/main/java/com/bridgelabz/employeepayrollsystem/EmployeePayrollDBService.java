package com.bridgelabz.employeepayrollsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.bridgelabz.employeepayrollsystem.EmployeePayrollException.ExceptionType;	

public class EmployeePayrollDBService {
	private PreparedStatement employeePayrollDataStatement;
	   private PreparedStatement preparedStatementForEmployeeData;
	private static List<EmployeePayrollData> employeePayrollList;
	private static EmployeePayrollDBService employeePayrollDBService;
	  private PreparedStatement employeeJoinedGivenRangeStatement;
	   List<Employee> employeeList;
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}
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
	 public static EmployeePayrollDBService getDBServiceInstance() {
	        if (employeePayrollDBService == null)
	            employeePayrollDBService = new EmployeePayrollDBService();
	        return employeePayrollDBService;
	    }
	
	public List<EmployeePayrollData> readData(){
		
		String sql="SELECT * from employee_payroll;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}	
	
	public int updateEmployeeSalary(String name, double salary) throws SQLException {
		return this.updateEmployeeDataUsingStatement(name,salary);
		
	}
	
	public int updateEmployeeDataUsingStatement(String name,double salary) throws SQLException {
		String sqlString = String.format("update employee_payroll set salary = %2f where employee_ID IN (SELECT employee_ID FROM employee WHERE employee_name = '%s');",salary,name);
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sqlString);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	private void prepareStatementForEmployeeData() {
			
			try {
				Connection connection = this.getConnection();
				String sqlStatement = "SELECT * FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id WHERE employee_name = ?;";
				employeePayrollDataStatement = connection.prepareStatement(sqlStatement);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

	public List<EmployeePayrollData> getEmployeeDetailsBasedOnNameUsingStatement(String name) {
		String sqlStatement = String.format("SELECT * FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id WHERE employee_name = '%s';",name);
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			employeePayrollList = this.getEmployeePayrollData(resultSet);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basicSalary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, basicSalary, startDate));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	public List<EmployeePayrollData> getEmployeePayrollData(String name) {
		
		List<EmployeePayrollData> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1,name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	public List<EmployeePayrollData> getEmployeeDetailsBasedOnStartDateUsingStatement(String startDate) {
		
		String sql = String.format("SELECT * FROM employee_payroll WHERE startDate BETWEEN CAST('%s' AS DATE) AND DATE(NOW());",startDate);
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			employeePayrollList = this.getEmployeePayrollData(resultSet);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollData> getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(String startDate) 
	{
		List<EmployeePayrollData> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.preparedStatementForEmployeeDataBasedOnStartDate();
		try {
			employeePayrollDataStatement.setString(1,startDate);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	private void preparedStatementForEmployeeDataBasedOnStartDate() {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id WHERE start_date BETWEEN ? AND ?;";;
			employeePayrollDataStatement = connection.prepareStatement(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void displayDate() {
		System.out.println(employeePayrollList);
	}
	public List<Double> getSumOfSalaryBasedOnGenderUsingStatement() {
		
		String sqlStatement = "SELECT gender, SUM(basic_salary) AS TotalSalary FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id GROUP BY gender;";
		List<Double> sumOfSalaryBasedOnGender = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			while(resultSet.next()) {
				double salary = resultSet.getDouble("TotalSalary");
				sumOfSalaryBasedOnGender.add(salary);
			}
		}
		catch(SQLException e){
			throw new EmployeePayrollException(ExceptionType.CANNOT_EXECUTE_QUERY, "Check the Syntax");
		}
		return sumOfSalaryBasedOnGender;
	}
	
	public List<Double> getAverageOfSalaryBasedOnGenderUsingStatement() {
		
		String sqlStatement = "SELECT gender, AVG(salary) AS AverageSalary FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id GROUP BY gender;";
		List<Double> averageOfSalaryBasedOnGender = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			while(resultSet.next()) {
				double salary = resultSet.getDouble("AverageSalary");
				averageOfSalaryBasedOnGender.add(salary);
			}
		}
		catch(SQLException e){
			throw new EmployeePayrollException(ExceptionType.CANNOT_EXECUTE_QUERY, "Check the Syntax");
		}
		return averageOfSalaryBasedOnGender;
	}
	
	public List<Double> getMinimumSalaryBasedOnGenderUsingStatement() {
		
		String sqlStatement = "SELECT gender, MIN(salary) AS MinimumSalary FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id GROUP BY gender;";
		List<Double> MinimumSalaryBasedOnGender = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			while(resultSet.next()) {
				double salary = resultSet.getDouble("MinimumSalary");
				MinimumSalaryBasedOnGender.add(salary);
			}
		}
		catch(SQLException e){
			throw new EmployeePayrollException(ExceptionType.CANNOT_EXECUTE_QUERY, "Check the Syntax");

		}
		return MinimumSalaryBasedOnGender;
	}
	
	public List<Double> getMaximumSalaryBasedOnGenderUsingStatement() {
		
		String sqlStatement = "SELECT gender, MAX(salary) AS MaximumSalary FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id GROUP BY gender;";
		List<Double> MaximumSalaryBasedOnGender = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			while(resultSet.next()) {
				double salary = resultSet.getDouble("MaximumSalary");
				MaximumSalaryBasedOnGender.add(salary);
			}
		}
		catch(SQLException e){
			throw new EmployeePayrollException(ExceptionType.CANNOT_EXECUTE_QUERY, "Check the Syntax");

		}
		return MaximumSalaryBasedOnGender;
	}

	public List<Integer> getCountOfEmployeesBasedOnGenderUsingStatement() {
		
		String sqlStatement = "SELECT gender, COUNT(gender) AS CountBasedOnGender FROM employee JOIN employee_payroll ON employee.employee_ID = employee_payroll.id GROUP BY gender;";
		List<Integer> CountBasedOnGender = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			while(resultSet.next()) {
				int count = resultSet.getInt("CountBasedOnGender");
				CountBasedOnGender.add(count);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return CountBasedOnGender;
	}
public EmployeePayrollData addEmployeeToPayroll(int id, String name, double salary, long phoneNumber, LocalDate startDate, String gender, int companyId) {
		
		int employeeId = -1;
		EmployeePayrollData employeePayrollData = null;
		String sql = String.format("INSERT INTO employee_payroll (name, gender, salary, startDate) VALUES ('%s', '%s', '%s', '%s')", name, gender, salary, Date.valueOf(startDate));
		
		try (Connection connection = this.getConnection()){
			
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					employeeId = resultSet.getInt(1);
			}
			employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employeePayrollData;
		
	}
private List<Employee> getEmployeeDataList(ResultSet resultSet) {
    employeeList = new ArrayList<>();
    try {
        while (resultSet.next()) {
            employeeList.add(new Employee(resultSet.getInt("employee_id"), resultSet.getString("employe_name"), resultSet.getString("gender"), resultSet.getString("address"), resultSet.getLong("phone_number"), resultSet.getDate("start_date").toLocalDate(), resultSet.getInt("company_id")));
        }
    } catch (Exception e) {
        throw new DBException(e.getMessage());
    }
    return employeeList;
}
private void preparedStatementToretriveEmployeeInRange() {
    try {
        Connection connection = this.getConnection();
        String query = "select * from employee where start_date between ? and ?";
        employeeJoinedGivenRangeStatement = connection.prepareStatement(query);
    } catch (Exception e) {
        throw new DBException(e.getMessage());
    }
}
public List<Employee> readEmployedJoinedRange(Date startDate, Date endDate) {
    if (employeeJoinedGivenRangeStatement == null) {
        this.preparedStatementToretriveEmployeeInRange();
    }
    try {
        employeeJoinedGivenRangeStatement.setDate(1, startDate);
        employeeJoinedGivenRangeStatement.setDate(2, endDate);
        ResultSet resultSet = employeeJoinedGivenRangeStatement.executeQuery();
        return this.getEmployeeDataList(resultSet);
    } catch (Exception e) {
        throw new DBException(e.getMessage());
    }
}

public Payroll insertEmployeePayrollValues(Employee employee, Payroll payroll) {
    Payroll updatedPayroll;
    String insertEmployee = String.format("insert into employee values('%s','%s','%s','%s','%s','%s','%s')", employee.getId(), employee.getName(), employee.getGender(), employee.getAddress(), employee.getPhoneNumber(), Date.valueOf(employee.getStartDate()), employee.getCompanyId());
    String insertPayroll = String.format("insert into payroll values('%s','%s','%s','%s','%s','%s')", payroll.getEmployeeId(), payroll.getBasicPay(), payroll.getDeductions(), payroll.getTaxablePay(), payroll.getIncomeTax(), payroll.getNetPay());
    String insertEmployeeDepartment=String.format("insert into employee_department values('%s','%s')",employee.getId(),employee.getDepartmentList().get(0).id);
    Connection connection;
    try {
        connection = this.getConnection();
        connection.setAutoCommit(false);
    } catch (Exception e) {
        throw new DBException(e.getMessage());
    }
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(insertEmployee);
    } catch (Exception e) {
        try {
            connection.rollback();
        } catch (Exception exec) {
            throw new DBException(exec.getMessage());
        }
        throw new DBException(e.getMessage());
    }
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(insertPayroll);
    }
    catch (Exception e) {
        try {
            connection.rollback();
        } catch (Exception exec) {
            throw new DBException(exec.getMessage());
        }
        throw new DBException(e.getMessage());
    }
    try(Statement statement=connection.createStatement()){
        statement.executeUpdate(insertEmployeeDepartment);
        connection.commit();
        updatedPayroll = payroll;
    } catch (Exception e) {
        try {
            connection.rollback();
        } catch (Exception exec) {
            throw new DBException(exec.getMessage());
        }
        throw new DBException(e.getMessage());
    }
    return updatedPayroll;
}
public List<Employee> readEmployeeDataFromDB(String name) {
    if (preparedStatementForEmployeeData == null) {
        this.preparedStatementToReadEmployeeData();
    }
    try {
        preparedStatementForEmployeeData.setString(1, name);
        ResultSet resultSet = preparedStatementForEmployeeData.executeQuery();
        employeeList = this.getCompleteEmployeeDataList(resultSet);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return employeeList;
}
private void preparedStatementToReadEmployeeData() {
    try (Connection connection = this.getConnection()) {
        String query = "select * from employee e, payroll p,company c where e.employee_ID=p.employee_ID and e.company_ID=c.company_ID and employe_name= ?";
        preparedStatementForEmployeeData = connection.prepareStatement(query);
    } catch (Exception e) {
        throw new DBException(e.getMessage());
    }
}
private List<Employee> getCompleteEmployeeDataList(ResultSet resultSet) {
    employeeList = new ArrayList<>();
    try {
        while (resultSet.next()) {
            employeeList.add(new Employee(resultSet.getInt("employee_ID"), resultSet.getString("employe_name"), resultSet.getString("gender"), resultSet.getString("address"), resultSet.getLong("phone_number"), resultSet.getDate("start_date").toLocalDate(), new Payroll(resultSet.getInt("employee_id"), resultSet.getDouble("basic_pay"), resultSet.getDouble("deductions"), resultSet.getDouble("taxable_pay"), resultSet.getDouble("income_tax"), resultSet.getDouble("net_pay")), new Company(resultSet.getInt("company_id"), resultSet.getString("company_name"))));
        }
    } catch (Exception e) {
        throw new DBException(e.getMessage());
    }
    return employeeList;
}
	
	
}

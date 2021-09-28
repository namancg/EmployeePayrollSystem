package com.bridgelabz.employeepayrollsystem;

import java.time.LocalDate;

public class Employee {
	 private Integer companyId;
	    private String gender;
	    private String address;
	    private Long phoneNumber;
	    private LocalDate startDate;
	    public int id;
	    public String name;
	    private Payroll payroll;
	    private Company company;

	    public Employee(int id, String name) {
	        this.id = id;
	        this.name = name;
	    }

	    public Payroll getPayroll() {
	        return payroll;
	    }

	    public void setPayroll(Payroll payroll) {
	        this.payroll = payroll;
	    }

	    public Integer getCompanyId() {
	        return companyId;
	    }

	    public void setCompanyId(Integer companyId) {
	        this.companyId = companyId;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public Long getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(Long phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }

	    public LocalDate getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(LocalDate startDate) {
	        this.startDate = startDate;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Company getCompany() {
	        return company;
	    }

	    public void setCompany(Company company) {
	        this.company = company;
	    }

	    public Employee(int id, String name, String gender, String address, Long phoneNumber, LocalDate startDate, Payroll payroll, Company company) {
	        this.id = id;
	        this.name = name;
	        this.gender = gender;
	        this.address = address;
	        this.phoneNumber = phoneNumber;
	        this.startDate = startDate;
	        this.payroll = payroll;
	        this.company = company;

	    }

	    public Employee(int id, String name, String gender, String address, Long phoneNumber, LocalDate startDate, int companyId) {
	        this.id = id;
	        this.name = name;
	        this.gender = gender;
	        this.address = address;
	        this.phoneNumber = phoneNumber;
	        this.startDate = startDate;
	        this.companyId = companyId;
	    }

	    @Override
	    public String toString() {
	        return "Employee{" +
	                "companyId=" + companyId +
	                ", gender='" + gender + '\'' +
	                ", address='" + address + '\'' +
	                ", phoneNumber=" + phoneNumber +
	                ", startDate=" + startDate +
	                ", id=" + id +
	                ", name='" + name + '\'' +
	                '}';
	    }
}

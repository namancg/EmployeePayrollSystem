package com.bridgelabz.employeepayrollsystem;

public class Payroll {
	private Integer employeeId;
    private  double basicPay;
    private  double deductions;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    private  double taxablePay;
    private  double incomeTax;
    private  double netPay;

    public double getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(double basicPay) {
        this.basicPay = basicPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getTaxablePay() {
        return taxablePay;
    }

    public void setTaxablePay(double taxablePay) {
        this.taxablePay = taxablePay;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    Payroll(Integer employeeId, double basicPay, double deductions, double taxablePay, double incomeTax, double netPay){
        this.employeeId=employeeId;
        this.basicPay=basicPay;
        this.deductions=deductions;
        this.taxablePay=taxablePay;
        this.incomeTax=incomeTax;
        this.netPay=netPay;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "employeeId=" + employeeId +
                ", basicPay=" + basicPay +
                ", deductions=" + deductions +
                ", taxablePay=" + taxablePay +
                ", incomeTax=" + incomeTax +
                ", netPay=" + netPay +
                '}';
    }
}

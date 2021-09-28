package com.bridgelabz.employeepayrollsystem;

public class Department {
	 Integer id;
	    String name;

	    @Override
	    public String toString() {
	        return "Department{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                '}';
	    }

	    Department(Integer id, String name){
	        this.id=id;
	        this.name=name;
	    }
}

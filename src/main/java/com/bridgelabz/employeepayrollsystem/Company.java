package com.bridgelabz.employeepayrollsystem;

public class Company {
	 Integer id;
	    String name;
	    Company(Integer id,String name){
	        this.id=id;
	        this.name=name;
	    }

	    @Override
	    public String toString() {
	        return "Company{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                '}';
	    }
}

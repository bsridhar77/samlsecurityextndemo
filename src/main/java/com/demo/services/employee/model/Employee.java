package com.demo.services.employee.model;

public class Employee {


	Employee(){
		
	}
	private String employeeId;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String memberId) {
		this.employeeId = memberId;
	}
	
	public Employee(String employeeId, String desc) {
		super();
		this.employeeId = employeeId;
		this.desc = desc;
	}

	private String desc;

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
	
	
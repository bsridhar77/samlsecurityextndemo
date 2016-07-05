package com.demo.services.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.services.employee.model.Employee;

@Controller
@SpringBootApplication
public class DemoEmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoEmployeeApplication.class, args);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
 
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllRecords() {
		List<Employee> empList=new ArrayList<Employee>();
		empList.add(new Employee("1","Sri"));
		empList.add(new Employee("2","Raj"));
		empList.add(new Employee("3","Sheldon"));
		empList.add(new Employee("4","Penny"));
		return new ResponseEntity<>(empList, HttpStatus.OK);
	}
    
}

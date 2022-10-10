package org.demo.app;

import java.time.LocalDate;

import org.demo.db.EmployeeRepository;
import org.demo.domain.model.Employee;
import org.demo.services.BonjourService;
import org.demo.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyApplication {

	@Autowired
	private HelloService helloService ;
	
	@Autowired
	private BonjourService bonjourService ;

	@Autowired
	private EmployeeRepository employeeRepository ;
	
    public void run(String[] args) {
    	System.out.println("Hello world!");
    	System.out.println("args.length = " + args.length);
    	helloService.hello("Bob");
    	bonjourService.bonjour("John");
    	
    	System.out.println("SQL create table...");
    	employeeRepository.createTable();
    	
    	Employee emp = new Employee();
    	emp.setId(1L);
    	emp.setFirstName("Bob");
    	emp.setFirstName("Sponge");
    	emp.setBirthDate( LocalDate.of(2020, 7, 14) );    	
    	employeeRepository.insert(emp);
    	
    	System.out.println("SQL count...");
    	int count = employeeRepository.getCount();
    	System.out.println("count = " + count);
    	
    	System.out.println("findById(1)...");
    	emp = employeeRepository.findById(1L);
       	System.out.println("employee = " + emp);

       	System.out.println("findById(123)...");
    	emp = employeeRepository.findByIdWithParamMap(123L);
       	System.out.println("employee = " + emp);
    } 
}

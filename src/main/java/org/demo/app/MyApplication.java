package org.demo.app;

import org.demo.db.EmployeeRepository;
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
    	
    	System.out.println("SQL count...");
    	int count = employeeRepository.getCount();
    	System.out.println("count = " + count);
    	
    } 
}

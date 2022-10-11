package org.demo.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    	
    	employeeRepository.insert(new Employee(1L, "Bob", "Sponge", LocalDate.of(2020, 7, 14)));
    	employeeRepository.insert(new Employee(2L, "John", "Wayne", LocalDate.of(1936, 8, 27)));
    	
    	List<Employee> employees = new ArrayList<>();
    	employees.add(new Employee(10L, "Aaa", "aa", LocalDate.of(2010, 7, 14))) ;
    	employees.add(new Employee(11L, "Bbb", "bb", LocalDate.of(2011, 7, 14))) ;
    	employees.add(new Employee(12L, "Ccc", "bb", LocalDate.of(2012, 7, 14))) ;
    	employees.add(new Employee(13L, "Ddd", "bb", LocalDate.of(2013, 7, 14))) ;
    	employeeRepository.insertBatch(employees);
    	
    	System.out.println("SQL count...");
    	long count = employeeRepository.countAll();
    	System.out.println("count = " + count);
    	
    	Employee emp;
    	System.out.println("findById(1)...");
    	emp = employeeRepository.findById(1L);
       	System.out.println("employee = " + emp);

       	System.out.println("findById(123)...");
    	emp = employeeRepository.findById(5L);
       	System.out.println("employee = " + emp);
       	
       	System.out.println("deleteById(1)...");
       	int r = employeeRepository.deleteById(1L);
       	System.out.println("r = " + r);
       	
       	System.out.println("count = " + employeeRepository.countAll());
    } 
}

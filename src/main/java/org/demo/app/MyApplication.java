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
    	employees.add(new Employee(12L, "Ccc", "cc", LocalDate.of(2012, 7, 14))) ;
    	employees.add(new Employee(13L, "Ddd", "dd", LocalDate.of(2013, 7, 14))) ;
    	System.out.println("insertBatch()");
    	employeeRepository.insertBatch(employees);
    	
    	System.out.println("countAll() : " + employeeRepository.countAll() );
    	System.out.println("exists(2) : " + employeeRepository.exists(2L) );
    	System.out.println("exists(99) : " + employeeRepository.exists(99L) );

    	System.out.println("findAll() : ");
    	for ( Employee e : employeeRepository.findAll() ) {
    		System.out.println(" . " + e);
    	}
    	
    	System.out.println("findById(1) : " + employeeRepository.find(1L) );
    	System.out.println("findById(5) : " + employeeRepository.find(5L) );
       	
       	System.out.println("deleteById(1) : " + employeeRepository.delete(1L));
       	System.out.println("deleteById(5) : " + employeeRepository.delete(5L));
       	System.out.println("deleteById(12) : " + employeeRepository.delete(12L));
       	
       	System.out.println("countAll() : " + employeeRepository.countAll());
       	
       	for ( Employee e : employees ) {
       		e.setFirstName(e.getFirstName() + "-foo");
       	}
    	System.out.println("updateBatch()");
    	employeeRepository.updateBatch(employees);
    	System.out.println("findAll() : ");
    	for ( Employee e : employeeRepository.findAll() ) {
    		System.out.println(" . " + e);
    	}
    } 
}

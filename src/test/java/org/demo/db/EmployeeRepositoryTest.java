package org.demo.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.demo.domain.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Test
	void test() {
		assertEquals(0, employeeRepository.countAll());	
		
		employeeRepository.insert( new Employee(1L, "John", "Wayne", LocalDate.of(1936, 8, 27)) );		
		assertEquals(1, employeeRepository.countAll());
		
		Employee e = employeeRepository.find(1L);
		assertNotNull(e);
		assertEquals("John", e.getFirstName());
		
		assertEquals(1, employeeRepository.delete(e));
		assertEquals(0, employeeRepository.countAll());	
	}

}

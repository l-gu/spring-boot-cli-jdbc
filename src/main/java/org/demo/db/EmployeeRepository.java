package org.demo.db;

import java.util.List;

import org.demo.domain.model.Employee;

public interface EmployeeRepository {
	
	void createTable(); // just for test 
	
	long countAll();
	
	boolean exists(Long id);
	
	boolean exists(Employee record);
	
	List<Employee> findAll();

	Employee find(Long id);
		
	int insert(Employee record) ;
	
	int[] insertBatch(List<Employee> records);

	int update(Employee record);
	
	int[] updateBatch(List<Employee> records);
	
	int delete(Long id);
	
	int delete(Employee record);
}

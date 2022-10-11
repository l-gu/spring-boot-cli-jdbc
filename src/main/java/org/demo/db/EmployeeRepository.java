package org.demo.db;

import java.util.List;

import org.demo.domain.model.Employee;

public interface EmployeeRepository {
	
	void createTable(); // just fot test 
	
	long countAll();
	
	List<Employee> findAll();

	Employee findById(Long id);
		
	int insert(Employee record) ;
	
	int[] insertBatch(List<Employee> records);

	int update(Employee record);
	
	int[] updateBatch(List<Employee> records);
	
	int deleteById(Long id);
	
	int delete(Employee record);
}

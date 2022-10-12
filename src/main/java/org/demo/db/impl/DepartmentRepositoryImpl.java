package org.demo.db.impl;

import org.demo.db.DepartmentRepository;
import org.demo.domain.model.Department;
import org.springframework.stereotype.Repository;

/**
 * Provisional version with no real implementation 
 * Just required by Spring for component injection
 * Replaced by Mock in JUnit tests
 *
 */
@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

	@Override
	public Department find(int id) {
		// TODO 
		return null;
	}
	
}

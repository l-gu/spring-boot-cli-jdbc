package org.demo.services.impl;

import org.demo.db.DepartmentRepository;
import org.demo.services.CheckDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckDepartmentServiceImpl implements CheckDepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	public void checkDepartmentExistence(int id) {
		if ( departmentRepository.find(id) == null ) {
			throw new IllegalStateException("Invalid department id " + id);
		}
	}
}

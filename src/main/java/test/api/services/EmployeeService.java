package test.api.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.api.entities.Department;
import test.api.entities.Employee;
import test.api.exception.ResourceNotFoundException;
import test.api.models.EmployeeRequest;
import test.api.repositories.DepartmentRepo;
import test.api.repositories.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	public Employee createEmployee(EmployeeRequest employeeRequest) {
		Department department = departmentRepo.findById(employeeRequest.department_id())
		.orElseThrow(() -> new ResourceNotFoundException("Department not exist id = " + employeeRequest.department_id()));
		Employee employee = new Employee(
			employeeRequest.firstname(),
			employeeRequest.lastname(),
			employeeRequest.email(),
			department
		);
		return employeeRepo.save(employee);
	}

	public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) {
		Employee employee = employeeRepo.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id = " + id));
		
		Department department = departmentRepo.findById(employeeRequest.department_id())
		.orElseThrow(() -> new ResourceNotFoundException("Department not exist id = " + employeeRequest.department_id()));
		
		employee.setFirstname(employeeRequest.firstname());
		employee.setLastname(employeeRequest.lastname());
		employee.setEmail(employeeRequest.email());
		employee.setDepartment(department);
		
		Employee employeeUpdate = employeeRepo.save(employee);
		return employeeUpdate;
	}

	public Map<String, Boolean> deleteEmployee(Long id) {
		Employee employee = employeeRepo.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id = " + id));
		
		employeeRepo.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

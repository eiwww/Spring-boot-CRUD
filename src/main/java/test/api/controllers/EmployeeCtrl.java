package test.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.api.entities.Employee;
import test.api.exception.ResourceNotFoundException;
import test.api.models.EmployeeRequest;
import test.api.repositories.EmployeeRepo;
import test.api.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeCtrl {
	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public List<Employee> getAllEmployee(Authentication authentication) {
		System.out.println(authentication.getName());
		return employeeRepo.findAll();
	}

	@GetMapping("{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepo.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id = " + id));

		return ResponseEntity.ok(employee);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Employee createEmployee(@RequestBody EmployeeRequest employee) {
		return employeeService.createEmployee(employee);
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeData) {
		Employee employee = employeeRepo.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id = " + id));
		
		employee.setFirstname(employeeData.getFirstname());
		employee.setLastname(employeeData.getLastname());
		employee.setEmail(employeeData.getEmail());
		
		Employee employeeUpdate = employeeRepo.save(employee);
		return ResponseEntity.ok(employeeUpdate);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee = employeeRepo.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id = " + id));
		
		employeeRepo.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}

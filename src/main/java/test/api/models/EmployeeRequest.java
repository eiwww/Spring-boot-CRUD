package test.api.models;

public record EmployeeRequest(
  	// Long id,
	String firstname,
	String lastname,
	String email,
	Long department_id
) {}

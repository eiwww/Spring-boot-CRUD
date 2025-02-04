package test.api.models;

public record EmployeeRequest(
  	Long id,
	String firstname,
	String lastname,
	String email,
	String gender,
	Long department_id
) {}

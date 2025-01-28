package test.api.models;

public record UserRequest(
  	Long id,
  	String username,
  	String password,
	String role,
  	Long employee_id   
) {}

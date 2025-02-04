package test.api.models;

public record RegisterUserRequest(
    String username,
    String password,
    String firstname,
    String lastname,
    String email,
    String gender,
    String role,
    Long department_id
) { }

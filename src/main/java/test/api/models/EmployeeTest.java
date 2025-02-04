package test.api.models;

import test.api.entities.Employee.Gender;
import test.api.entities.User.Role;

public record EmployeeTest(
    String emp_name,
    String dep_name,
    Role role,
    Gender gender
) { }

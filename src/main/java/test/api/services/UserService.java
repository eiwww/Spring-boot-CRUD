package test.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.api.entities.Department;
import test.api.entities.Employee;
import test.api.entities.User;
import test.api.exception.ResourceNotFoundException;
import test.api.models.RegisterUserRequest;
import test.api.models.UserRequest;
import test.api.repositories.DepartmentRepo;
import test.api.repositories.EmployeeRepo;
import test.api.repositories.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserRequest userRequest) {
        Employee employee = employeeRepo.findById(userRequest.employee_id())
        .orElseThrow(() -> new ResourceNotFoundException("Employee not exist id = " + userRequest.employee_id()));
        
        User user = new User(
            userRequest.username(),
            passwordEncoder.encode(userRequest.password()),
            User.Role.valueOf(userRequest.role()),
            employee
        );
        return userRepo.save(user);
    }

    @Transactional
    public Employee registerUser(RegisterUserRequest registerUserRequest) {
        Department department = departmentRepo.findById(registerUserRequest.department_id())
        .orElseThrow(() -> new ResourceNotFoundException("Department not exist id = " + registerUserRequest.department_id()));

        Employee employee = new Employee(
            registerUserRequest.firstname(),
            registerUserRequest.lastname(),
            registerUserRequest.email(),
            Employee.Gender.valueOf(registerUserRequest.gender()),
            department
        );
        employeeRepo.save(employee);

        User user = new User(
            registerUserRequest.username(),
            passwordEncoder.encode(registerUserRequest.password()),
            User.Role.valueOf(registerUserRequest.role()),
            employee
        );
        userRepo.save(user);

        return employee;
    }
}

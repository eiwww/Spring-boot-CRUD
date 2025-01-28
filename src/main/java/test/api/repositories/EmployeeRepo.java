package test.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.api.entities.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
  
}

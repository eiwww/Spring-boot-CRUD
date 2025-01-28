package test.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.api.entities.Department;

@Repository
public interface  DepartmentRepo extends JpaRepository<Department, Long> {
  
}

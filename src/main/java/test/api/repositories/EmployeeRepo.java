package test.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import test.api.entities.Employee;
import test.api.models.EmployeeTest;

@SuppressWarnings("unused")
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {
    //other way to use query
    @Query("SELECT new test.api.models.EmployeeTest(e.firstName, d.name, u.role, e.gender) FROM Employee e INNER JOIN Department d ON e.department.id = d.id LEFT JOIN User u ON e.user.id = u.id WHERE e.firstName LIKE %:txt%")
    Page<EmployeeTest> getEmployeeDep(@Param("txt") String search, Pageable pageable);
}
 
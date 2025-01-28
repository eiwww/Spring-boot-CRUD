package test.api.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.api.entities.Department;
import test.api.exception.ResourceNotFoundException;
import test.api.models.DepartmentRequest;
import test.api.repositories.DepartmentRepo;

@Service
public class DepartmentService {
    
    @Autowired
    DepartmentRepo departmentRepo;

    public Department createDepartment(DepartmentRequest departmentRequest) {
        Department department = new Department(
            departmentRequest.name()  
        );
        return departmentRepo.save(department);
    }

    public Department updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not exist id = " + id));

        department.setName(departmentRequest.name());

        Department departmentUpdate = departmentRepo.save(department);
        return departmentUpdate;
    }

    public Map<String, Boolean> deleteDepartment(Long id) {
        Department department = departmentRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not exist id = " + id));

        departmentRepo.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

package test.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.api.entities.Department;
import test.api.exception.ResourceNotFoundException;
import test.api.models.DepartmentRequest;
import test.api.repositories.DepartmentRepo;
import test.api.services.DepartmentService;




@RestController
@RequestMapping("/departments")
public class DepartmentCtrl {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping("")
    public List<Department> getAllDepartment() {
        return departmentRepo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not exist id = " + id));
        return ResponseEntity.ok(department);
    }
    
    @PostMapping("")
    public Department createDepartment(@RequestBody DepartmentRequest department) {
        return departmentService.createDepartment(department);
    }

    @PutMapping("")
    public Department updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.updateDepartment(id, departmentRequest);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }
    
}

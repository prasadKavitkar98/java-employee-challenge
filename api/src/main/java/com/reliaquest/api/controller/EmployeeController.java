package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("my-api/employee")
public class EmployeeController implements IEmployeeController<Employee, Employee> {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return Optional.ofNullable(employeeService.getAllEmployees())
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("No Employees Found"));
    }

    @Override
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String name) {
        return Optional.ofNullable(employeeService.getEmployeeByName(name))
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("No Employees Found"));
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return employee != null
                ? ResponseEntity.ok(employee)
                : ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalary());
    }

    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(employeeService.getTopTenHighestPaidEmployeeNames());
    }

    @Override
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employee));
    }

    @Override
    @DeleteMapping("/deleteId/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        String response = employeeService.deleteEmployeeById(id);
        return response.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }
}

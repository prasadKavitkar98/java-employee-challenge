package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.model.Employee;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeClient employeeClient;

    @Autowired
    public EmployeeService(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    public List<Employee> getAllEmployees() {
        return employeeClient.fetchAllEmployees();
    }

    public List<Employee> getEmployeeByName(String name) {
        return employeeClient.fetchAllEmployees().stream()
                .filter(emp -> emp.getEmployeeName() != null
                        && emp.getEmployeeName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public Employee getEmployeeById(String id) {
        return employeeClient.fetchEmployeeById(id);
    }

    public Integer getHighestSalary() {
        return employeeClient.fetchAllEmployees().stream()
                .mapToInt(Employee::getEmployeeSalary)
                .max()
                .orElse(0);
    }

    public List<String> getTopTenHighestPaidEmployeeNames() {
        return employeeClient.fetchAllEmployees().stream()
                .sorted(Comparator.comparingInt(Employee::getEmployeeSalary).reversed())
                .limit(10)
                .map(Employee::getEmployeeName)
                .toList();
    }

    public Employee createEmployee(Employee employee) {
        return employeeClient.createEmployee(employee);
    }

    public String deleteEmployeeById(String id) {
        Employee employee = employeeClient.fetchEmployeeById(id);
        return employeeClient.deleteEmployeeByName(employee.getEmployeeName());
    }
}

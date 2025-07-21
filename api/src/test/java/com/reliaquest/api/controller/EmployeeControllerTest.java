package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;

import java.util.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = employeeList();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(2, response.getBody().size());
        assertEquals("Prasad", response.getBody().get(0).getEmployeeName());
    }

    @Test
    void shouldThrowWhenNoEmployeesFound() {
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> employeeController.getAllEmployees());
    }

    @Test
    void getEmployeesByNameSearch() {
        when(employeeService.getEmployeeByName("Prasad")).thenReturn(Collections.singletonList(employeeData()));

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("Prasad");
        assertEquals(1, response.getBody().size());
        assertEquals("Prasad", response.getBody().get(0).getEmployeeName());
    }

    @Test
    void shouldThrowWhenNoEmployeesFoundByNameSearch() {
        when(employeeService.getEmployeeByName("Charlie")).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> employeeController.getEmployeesByNameSearch("Charlie"));
    }

    @Test
    void getEmployeeById() {
        Employee employee = employeeData();
        when(employeeService.getEmployeeById("1")).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");
        assertEquals("Prasad", response.getBody().getEmployeeName());
    }

    @Test
    void shouldThrowWhenEmployeeByIdNotFound() {
        when(employeeService.getEmployeeById("99")).thenThrow(new RuntimeException("Employee not found"));

        assertThrows(RuntimeException.class, () -> employeeController.getEmployeeById("99"));
    }

    @Test
    void getHighestSalaryOfEmployees() {
        when(employeeService.getHighestSalary()).thenReturn(200000);

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(200000, response.getBody());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() {
        List<String> topTen = List.of("Sayali", "Prasad");
        when(employeeService.getTopTenHighestPaidEmployeeNames()).thenReturn(topTen);

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();
        assertEquals(2, response.getBody().size());
        assertEquals("Sayali", response.getBody().get(0));
    }

    @Test
    void createEmployee() {
        Employee employee = employeeData();
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);
        assertEquals("Prasad", response.getBody().getEmployeeName());
    }

    @Test
    void deleteEmployeeById() {
        when(employeeService.deleteEmployeeById("1")).thenReturn("Employee deleted successfully");

        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
        assertEquals("Employee deleted successfully", response.getBody());
    }

    private List<Employee> employeeList() {
        List<Employee> employeeList = new ArrayList<>();

        Employee employee1 = new Employee();
        employee1.setId("1");
        employee1.setEmployeeName("Prasad");
        employee1.setEmployeeSalary(100000);
        employee1.setEmployeeAge(29);
        employee1.setEmployeeTitle("Engineer");
        employee1.setEmployeeEmail("prasad@Company.ie");

        Employee employee2 = new Employee();
        employee2.setId("1");
        employee2.setEmployeeName("Sayali");
        employee2.setEmployeeSalary(200000);
        employee2.setEmployeeAge(27);
        employee2.setEmployeeTitle("Manager");
        employee2.setEmployeeEmail("sayali@Company.ie");

        employeeList.add(employee1);
        employeeList.add(employee2);

        return employeeList;
    }

    private Employee employeeData() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeName("Prasad");
        employee.setEmployeeSalary(100000);
        employee.setEmployeeAge(29);
        employee.setEmployeeTitle("Engineer");
        employee.setEmployeeEmail("prasad@Company.ie");
        return employee;
    }
}
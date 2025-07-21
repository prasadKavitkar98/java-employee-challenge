package com.reliaquest.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.model.Employee;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldReturnAllEmployees() {

        // Given
        List<Employee> mockEmployees = employeeList();

        // Mocked
        when(employeeClient.fetchAllEmployees()).thenReturn(mockEmployees);

        // Actual Call
        List<Employee> result = employeeService.getAllEmployees();

        // Verify Result
        assertEquals(2, result.size());
        assertEquals("Prasad", result.get(0).getEmployeeName());
        assertEquals(200000, result.get(1).getEmployeeSalary());

        verify(employeeClient, times(1)).fetchAllEmployees();
    }

    @Test
    void shouldReturnEmployeebyName() {
        // Given
        String name = "Prasad";

        // Mocked
        when(employeeClient.fetchAllEmployees()).thenReturn(employeeList());

        // Actual Call
        List<Employee> result = employeeService.getEmployeeByName(name);

        // Verify Result
        assertNotNull(result);
        assertEquals(name, result.get(0).getEmployeeName());

        verify(employeeClient, times(1)).fetchAllEmployees();
    }

    @Test
    void shouldReturnEmployeebyId() {
        // Given
        String id = "1";

        // Mocked
        when(employeeClient.fetchEmployeeById(id)).thenReturn(employeeData());

        // Actual Call
        Employee result = employeeService.getEmployeeById(id);

        // Verify Result
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Prasad", result.getEmployeeName());

        verify(employeeClient, times(1)).fetchEmployeeById(id);
    }

    @Test
    void shouldReturnHighestSalary() {
        // mocked
        when(employeeClient.fetchAllEmployees()).thenReturn(employeeList());

        // Actual Call
        Integer result = employeeService.getHighestSalary();

        // Verify Result
        assertEquals(200000, result);
    }

    @Test
    void shouldReturnTopTenEmployeeNamesBySalary() {
        // mocked
        when(employeeClient.fetchAllEmployees()).thenReturn(employeeList());

        // Actual Call
        List<String> topTen = employeeService.getTopTenHighestPaidEmployeeNames();

        // Verify Result
        assertEquals(2, topTen.size());
        assertEquals("Sayali", topTen.get(0)); // Highest paid
    }

    @Test
    void shouldCreateEmployee() {
        // mocked
        when(employeeClient.createEmployee(employeeData())).thenReturn(employeeData());

        // Actual Call
        Employee created = employeeService.createEmployee(employeeData());

        // Verify Result
        assertEquals("Prasad", created.getEmployeeName());
        assertEquals(100000, created.getEmployeeSalary());
    }

    @Test
    void shouldDeleteEmployeeByIdSuccessfully() {
        // Given
        String name = "Prasad";
        String id = "1";

        // Mocked
        when(employeeClient.fetchEmployeeById(id)).thenReturn(employeeData());
        when(employeeClient.deleteEmployeeByName(name)).thenReturn("Employee deleted Successfully");

        // Actual
        String response = employeeService.deleteEmployeeById(id);

        // Verify Result
        assertEquals("Employee deleted Successfully", response);

        verify(employeeClient, times(1)).deleteEmployeeByName(name);
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

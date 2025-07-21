package com.reliaquest.api.client.impl;

import com.reliaquest.api.model.CreateEmployeeDTO;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeListResponse;
import com.reliaquest.api.model.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeClientImplTest {

    private RestTemplate restTemplate;
    private EmployeeClientImpl client;
    private final String baseUrl = "http://dummy.api/employees";

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        client = new EmployeeClientImpl(restTemplate, baseUrl);
    }

    @Test
    void fetchAllEmployees_shouldReturnList() {
        Employee employee = new Employee();
        employee.setEmployeeName("Alice");
        EmployeeListResponse response = new EmployeeListResponse();
        response.setData(List.of(employee));

        when(restTemplate.getForObject(baseUrl, EmployeeListResponse.class)).thenReturn(response);

        List<Employee> result = client.fetchAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getEmployeeName());
    }

    @Test
    void fetchAllEmployees_shouldReturnEmptyListWhenNull() {
        when(restTemplate.getForObject(baseUrl, EmployeeListResponse.class)).thenReturn(null);

        List<Employee> result = client.fetchAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchEmployeeById_shouldReturnEmployee() {
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEmployeeName("Bob");

        EmployeeResponse response = new EmployeeResponse();
        response.setData(employee);

        when(restTemplate.getForObject(baseUrl + "/" + UUID.fromString(id), EmployeeResponse.class)).thenReturn(response);

        Employee result = client.fetchEmployeeById(id);

        assertNotNull(result);
        assertEquals("Bob", result.getEmployeeName());
    }

    @Test
    void fetchEmployeeById_shouldThrowWhenNull() {
        String id = UUID.randomUUID().toString();
        when(restTemplate.getForObject(baseUrl + "/" + UUID.fromString(id), EmployeeResponse.class)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> client.fetchEmployeeById(id));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void fetchEmployeeById_shouldThrowWhenHttpError() {
        String id = UUID.randomUUID().toString();
        when(restTemplate.getForObject(baseUrl + "/" + UUID.fromString(id), EmployeeResponse.class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(RuntimeException.class, () -> client.fetchEmployeeById(id));
    }

    @Test
    void createEmployee_shouldReturnCreatedEmployee() {
        Employee newEmp = employeeData();
        EmployeeResponse response = new EmployeeResponse();
        response.setData(newEmp);

        when(restTemplate.postForObject(eq(baseUrl), any(CreateEmployeeDTO.class), eq(EmployeeResponse.class)))
                .thenReturn(response);

        Employee result = client.createEmployee(newEmp);

        assertNotNull(result);
        assertEquals("Prasad", result.getEmployeeName());
    }

    @Test
    void createEmployee_shouldThrowWhenResponseIsNull() {
        Employee newEmp = employeeData();

        when(restTemplate.postForObject(eq(baseUrl), any(CreateEmployeeDTO.class), eq(EmployeeResponse.class)))
                .thenReturn(null);

        assertThrows(RuntimeException.class, () -> client.createEmployee(newEmp));
    }

    @Test
    void deleteEmployeeByName_shouldSucceed() {
        String name = "Eve";

        doReturn(null).when(restTemplate)
                .exchange(eq(baseUrl), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class));

        String result = client.deleteEmployeeByName(name);

        assertEquals("Employee Deleted Successfully", result);
    }

    @Test
    void deleteEmployeeByName_shouldThrowHttpError() {
        String name = "Ghost";

        doThrow(HttpClientErrorException.NotFound.class)
                .when(restTemplate).exchange(eq(baseUrl), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class));

        assertThrows(RuntimeException.class, () -> client.deleteEmployeeByName(name));
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
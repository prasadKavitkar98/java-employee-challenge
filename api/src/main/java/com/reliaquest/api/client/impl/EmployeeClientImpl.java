package com.reliaquest.api.client.impl;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.model.*;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeClientImpl implements EmployeeClient {
    private final RestTemplate restTemplate;

    private final String baseUrl;

    public EmployeeClientImpl(RestTemplate restTemplate, @Value("${employee.api.base-url}") String baseURL) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseURL;
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        EmployeeListResponse response = restTemplate.getForObject(baseUrl, EmployeeListResponse.class);
        return response != null ? response.getData() : List.of();
    }

    @Override
    public Employee fetchEmployeeById(String id) {
        String url = baseUrl + "/" + UUID.fromString(id);

        try {
            EmployeeResponse response = restTemplate.getForObject(url, EmployeeResponse.class);
            if (response == null) {
                throw new RuntimeException("Employee id " + id + " not found");
            }
            return response.getData();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Employee fetching employee with Id " + id + e.getStatusCode());
        }
    }

    @Override
    public Employee createEmployee(Employee employee) {
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO(
                employee.getEmployeeName(),
                employee.getEmployeeSalary(),
                employee.getEmployeeAge(),
                employee.getEmployeeTitle());

        try {
            EmployeeResponse response = restTemplate.postForObject(baseUrl, createEmployeeDTO, EmployeeResponse.class);
            if (response == null || response.getData() == null) {
                throw new RuntimeException("Failed to create employee");
            }
            return response.getData();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error creating employee: " + e.getStatusCode());
        }
    }

    @Override
    public String deleteEmployeeByName(String name) {
        DeleteEmployeeDTO deleteEmployeeDTO = new DeleteEmployeeDTO(name);
        String url = baseUrl + "/" + deleteEmployeeDTO;

        try {
            HttpEntity<DeleteEmployeeDTO> request = new HttpEntity<>(deleteEmployeeDTO);
            restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Void.class);
            return "Employee Deleted Successfully";
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error deleting employee with Id " + name + e.getStatusCode());
        }
    }
}

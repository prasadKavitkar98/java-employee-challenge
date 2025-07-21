package com.reliaquest.api.client;

import com.reliaquest.api.model.Employee;
import java.util.List;

public interface EmployeeClient {

    List<Employee> fetchAllEmployees();

    Employee fetchEmployeeById(String id);

    Employee createEmployee(Employee employee);

    String deleteEmployeeByName(String name);
}

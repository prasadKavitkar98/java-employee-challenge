# Implement this API

#### In this assessment you will be tasked with filling out the functionality of different methods that will be listed further down.

These methods will require some level of API interactions with Mock Employee API at http://localhost:8112/api/v1/employee.

Please keep the following in mind when doing this assessment:
* clean coding practices
* test driven development
* logging
* scalability

### Endpoints to implement

_See `com.reliaquest.api.controller.IEmployeeController` for details._

getAllEmployees()

    output - list of employees
    description - this should return all employees

getEmployeesByNameSearch(...)

    path input - name fragment
    output - list of employees
    description - this should return all employees whose name contains or matches the string input provided

getEmployeeById(...)

    path input - employee ID
    output - employee
    description - this should return a single employee

getHighestSalaryOfEmployees()

    output - integer of the highest salary
    description - this should return a single integer indicating the highest salary of amongst all employees

getTop10HighestEarningEmployeeNames()

    output - list of employees
    description - this should return a list of the top 10 employees based off of their salaries

createEmployee(...)

    body input - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

deleteEmployeeById(...)

    path input - employee ID
    output - name of the employee
    description - this should delete the employee with specified id given, otherwise error

### Testing
Please include proper integration and/or unit tests.

### Notes from prasad

---
## API Package Structure

```
src/
├── main/
│   └── java/
│       └── com/reliaquest/api/
│           ├── controller/
│           │   ├── EmployeeController.java
│           │   └── IEmployeeController.java
│           ├── client/
│           │   ├── EmployeeClient.java
│           │   └── impl/
│           │       └── EmployeeClientImpl.java
│           ├── model/
│           │   ├── Employee.java
│           │   ├── CreateEmployeeDTO.java
│           │   ├── EmployeeResponse.java
│           │   ├── EmployeeListResponse.java
│           │   └── DeleteEmployeeDTO.java
│           └── service/
│               └── EmployeeService.java

├── test/
│   └── java/
│       └── com/reliaquest/api/
│           ├── controller/
│           │   └── EmployeeControllerTest.java
│           ├── client/
│           │   └── impl/
│           │       └── EmployeeClientImplTest.java
│           └── service/
│               └── EmployeeServiceTest.java
```

---
### Test Coverage Summary

The project is well-tested using JUnit 5 and Mockito, achieving strong coverage across core components:

| Package/Component         | Class Coverage | Method Coverage | Line Coverage | Branch Coverage |
|--------------------------|----------------|------------------|----------------|------------------|
| `controller`             | 100%           | 100%             | 94%            | 50%              |
| `client`                 | 100%           | 100%             | 96%            | 87%              |
| `service`                | 100%           | 100%             | 100%           | 50%              |
| `model`                  | N/A (No logic) | N/A              | 100%           | N/A              |

---

### GET Requests

1. **Get All Employees**  
   `GET /my-api/employee`

2. **Get Employee by ID**  
   `GET /my-api/employee/id/{uuid}`

3. **Get Employee by Name**  
   `GET /my-api/employee/name/{employeeName}`

4. **Get Highest Salary**  
   `GET /my-api/employee/highestSalary`

5. **Get Top 10 Highest Earning Employee Names**  
   `GET /my-api/employee/topTenHighestEarningEmployeeNames`

---

### Create Employee

- **POST**  
  `http://localhost:8111/my-api/employee`

- **Request Body (JSON)**:
```json
{
  "employee_name": "Prasad",
  "employee_salary": 60000,
  "employee_age": 29,
  "employee_title": "Software Engineer",
  "employee_email": "prasad@company.com"
}
```

- **Example Response**:
```json
{
  "id": "37db5780-04d8-4c4b-9bf9-538456269be8",
  "employee_name": "Sayali",
  "employee_salary": 600000,
  "employee_age": 29,
  "employee_title": "Manager",
  "employee_email": "livlaughlo@company.com"
}
```

---

###  Delete Employee by ID

- **DELETE**  
  `http://localhost:8111/my-api/employee/deleteId/{uuid}`

- **Response**:  
  `Employee Deleted Successfully`

---

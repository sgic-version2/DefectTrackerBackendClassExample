package com.sgic.employee.server.services;

import com.sgic.employee.server.entities.Employee;

public interface EmployeeService {
public Employee createEmployee(Employee employee);
public boolean isEmailAlreadyExist(String email);
}

package com.employee.details.service;

import com.employee.details.exception.EmployeeNotFoundException;
import com.employee.details.exception.EmployeeServiceException;
import com.employee.details.model.Employee;
import com.employee.details.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long empId) {
        return employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + empId + " not found"));
    }

    public Employee createEmployee(Employee employee) {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new EmployeeServiceException("Failed to create employee: " + e.getMessage());
        }
    }

    public Employee updateEmployee(Long empId, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + empId + " not found"));

        employee.setName(employeeDetails.getName());
        employee.setContactNo(employeeDetails.getContactNo());
        employee.setEmail(employeeDetails.getEmail());
        employee.setAddress(employeeDetails.getAddress());

        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new EmployeeServiceException("Failed to update employee: " + e.getMessage());
        }
    }

    public void deleteEmployee(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + empId + " not found"));

        try {
            employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new EmployeeServiceException("Failed to delete employee: " + e.getMessage());
        }
    }
}
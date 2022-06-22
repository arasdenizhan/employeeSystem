package com.denzhn.employeesystem.datasource.constants;

public interface EmployeeQueries {
    String CREATE = "INSERT INTO employee (age,name,salary) VALUES (?, ?, ?)";
    String LIST = "SELECT * FROM employee";
}

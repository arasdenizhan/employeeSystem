package com.denzhn.employeesystem.datasource.service;

import com.denzhn.employeesystem.datasource.dto.EmployeeDto;
import com.denzhn.employeesystem.exception.BusinessLayerException;

import java.sql.Connection;
import java.util.List;

public interface EmployeeService {
    boolean create(Connection connection, EmployeeDto employeeDto) throws BusinessLayerException;
    boolean update(Connection connection, EmployeeDto employeeDto);
    boolean delete(Connection connection, Long id);
    List<EmployeeDto> list(Connection connection) throws BusinessLayerException;
}

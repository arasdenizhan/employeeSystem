package com.denzhn.employeesystem.datasource.service;

import com.denzhn.employeesystem.datasource.dto.EmployeeDto;
import com.denzhn.employeesystem.exception.BusinessLayerException;

import java.util.List;

public interface EmployeeService {
    boolean create(EmployeeDto employeeDto) throws BusinessLayerException;
    boolean update(EmployeeDto employeeDto) throws BusinessLayerException;
    boolean delete(Long id) throws BusinessLayerException;
    List<EmployeeDto> list() throws BusinessLayerException;
    void handleConnection() throws BusinessLayerException;
}

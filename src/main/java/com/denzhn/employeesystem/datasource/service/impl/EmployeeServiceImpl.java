package com.denzhn.employeesystem.datasource.service.impl;

import com.denzhn.employeesystem.datasource.constants.EmployeeQueries;
import com.denzhn.employeesystem.datasource.dto.EmployeeDto;
import com.denzhn.employeesystem.datasource.service.EmployeeService;
import com.denzhn.employeesystem.exception.BusinessLayerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private PreparedStatement preparedStatement;

    @Override
    public boolean create(Connection connection, EmployeeDto employeeDto) throws BusinessLayerException {
        try {
            preparedStatement = connection.prepareStatement(EmployeeQueries.CREATE);
            preparedStatement.setInt(1, employeeDto.getAge());
            preparedStatement.setString(2, employeeDto.getName());
            preparedStatement.setDouble(3, employeeDto.getSalary());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Connection connection, EmployeeDto employeeDto) {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Long id) {
        return false;
    }

    @Override
    public List<EmployeeDto> list(Connection connection) throws BusinessLayerException {
        try {
            List<EmployeeDto> resultList = new ArrayList<>();
            preparedStatement = connection.prepareStatement(EmployeeQueries.LIST);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                resultList.add(new EmployeeDto(resultSet.getLong(1), resultSet.getString(3), resultSet.getInt(2), resultSet.getDouble(4)));
            }
            return resultList;
        } catch (SQLException e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }
}

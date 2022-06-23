package com.denzhn.employeesystem.datasource.service.impl;

import com.denzhn.employeesystem.datasource.connector.Connector;
import com.denzhn.employeesystem.datasource.connector.impl.PostgresConnector;
import com.denzhn.employeesystem.datasource.constants.EmployeeQueries;
import com.denzhn.employeesystem.datasource.dto.EmployeeDto;
import com.denzhn.employeesystem.datasource.service.EmployeeService;
import com.denzhn.employeesystem.exception.BusinessLayerException;
import com.denzhn.employeesystem.exception.DatasourceConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeServiceImpl implements EmployeeService {

    private Connection connection;
    private static final Connector connector = new PostgresConnector();
    private PreparedStatement preparedStatement;

    @Override
    public boolean create(EmployeeDto employeeDto) throws BusinessLayerException {
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
    public boolean update(EmployeeDto employeeDto) throws BusinessLayerException {
        try{
            preparedStatement = connection.prepareStatement(EmployeeQueries.UPDATE);
            preparedStatement.setInt(1, employeeDto.getAge());
            preparedStatement.setString(2, employeeDto.getName());
            preparedStatement.setDouble(3, employeeDto.getSalary());
            preparedStatement.setLong(4, employeeDto.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e){
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws BusinessLayerException {
        try{
            preparedStatement = connection.prepareStatement(EmployeeQueries.DELETE);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e){
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public List<EmployeeDto> list() throws BusinessLayerException {
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

    public void handleConnection() throws BusinessLayerException {
        try {
            if(Objects.isNull(this.connection) || !connector.checkConnection(this.connection))
                this.connection = connector.connect();
            checkDDL();
        } catch (SQLException | DatasourceConnectionException e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    private void checkDDL() throws SQLException {
        connection.prepareStatement(EmployeeQueries.DDL).executeUpdate();
    }


}

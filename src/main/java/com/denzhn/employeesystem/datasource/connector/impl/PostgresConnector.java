package com.denzhn.employeesystem.datasource.connector.impl;

import com.denzhn.employeesystem.datasource.connector.Connector;
import com.denzhn.employeesystem.datasource.constants.DatasourceConstants;
import com.denzhn.employeesystem.exception.DatasourceConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class PostgresConnector implements Connector {
    @Override
    public synchronized Connection connect() throws DatasourceConnectionException {
        try {
            Class.forName(DatasourceConstants.DRIVER);
            String url = DatasourceConstants.URL;
            Properties props = new Properties();
            props.setProperty("user", DatasourceConstants.USER);
            props.setProperty("password", DatasourceConstants.PASSWORD);
            return DriverManager.getConnection(url, props);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatasourceConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public synchronized boolean checkConnection(Connection connection) throws DatasourceConnectionException {
        try {
            return connection.isValid(3);
        } catch (SQLException e) {
            throw new DatasourceConnectionException(e.getMessage(), e);
        }
    }
}

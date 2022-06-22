package com.denzhn.employeesystem.datasource.connector;

import com.denzhn.employeesystem.exception.DatasourceConnectionException;

import java.sql.Connection;

public interface Connector {
    Connection connect() throws DatasourceConnectionException;
    boolean checkConnection(Connection connection) throws DatasourceConnectionException;
}

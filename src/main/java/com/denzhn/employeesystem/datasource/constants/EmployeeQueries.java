package com.denzhn.employeesystem.datasource.constants;

public interface EmployeeQueries {
    String DDL = "create table if not exists employee\n" +
            "(\n" +
            "    id     bigserial\n" +
            "        primary key,\n" +
            "    age    integer          not null,\n" +
            "    name   varchar(255)     not null,\n" +
            "    salary double precision not null\n" +
            ");\n" +
            "\n" +
            "alter table employee\n" +
            "    owner to postgres";
    String CREATE = "INSERT INTO employee (age,name,salary) VALUES (?, ?, ?)";
    String LIST = "SELECT * FROM employee ORDER BY id";
    String DELETE = "DELETE FROM employee WHERE employee.id = ?";
    String UPDATE = "UPDATE employee SET age = ?, name = ?, salary = ? WHERE employee.id= ?";
}

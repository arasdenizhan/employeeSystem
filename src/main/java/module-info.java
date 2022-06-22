module com.denzhn.employeesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires lombok;


    opens com.denzhn.employeesystem to javafx.fxml;
    opens com.denzhn.employeesystem.datasource.dto to javafx.base;
    exports com.denzhn.employeesystem;
}
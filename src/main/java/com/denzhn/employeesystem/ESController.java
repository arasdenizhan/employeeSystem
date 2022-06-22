package com.denzhn.employeesystem;

import com.denzhn.employeesystem.datasource.connector.Connector;
import com.denzhn.employeesystem.datasource.connector.impl.PostgresConnector;
import com.denzhn.employeesystem.datasource.dto.EmployeeDto;
import com.denzhn.employeesystem.datasource.service.EmployeeService;
import com.denzhn.employeesystem.datasource.service.impl.EmployeeServiceImpl;
import com.denzhn.employeesystem.exception.BusinessLayerException;
import com.denzhn.employeesystem.exception.DatasourceConnectionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.util.Objects;

public class ESController {

    private static final String EMPTY = "";
    private static final Connector connector = new PostgresConnector();
    private Connection connection;
    private static final EmployeeService service = new EmployeeServiceImpl();

    @FXML
    private Label connectionText;
    @FXML
    private Label createOperationStatus;
    @FXML
    private TextArea createErrorTextArea;
    @FXML
    private TextArea listErrorTextArea;
    @FXML
    private Label listOperationStatus;
    @FXML
    private TextField createNameField;
    @FXML
    private TextField createAgeField;
    @FXML
    private TextField createSalaryField;
    @FXML
    private TableView<EmployeeDto> listTableView;

    @FXML
    protected void onCheckConnectionButtonClick() {
        handleConnection();
    }

    @FXML
    protected void onListButtonClick() {
        try {
            handleConnection();
            prepareTableView(FXCollections.observableArrayList(service.list(this.connection)));
            listOperationStatus.setTextFill(Color.GREEN);
            listOperationStatus.setText("Successful!");
        } catch (BusinessLayerException e) {
            listOperationStatus.setTextFill(Color.RED);
            listOperationStatus.setText("Failed!");
            listErrorTextArea.setText(e.getMessage());
        }
    }

    @FXML
    protected void onCreateButtonClick(){
        try {
            handleConnection();
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName(createNameField.getText());
            employeeDto.setAge(Integer.parseInt(createAgeField.getText()));
            employeeDto.setSalary(Double.parseDouble(createSalaryField.getText()));
            if (service.create(this.connection, employeeDto)){
                createOperationStatus.setTextFill(Color.GREEN);
                createOperationStatus.setText("Successful!");
                createNameField.setText(EMPTY);
                createAgeField.setText(EMPTY);
                createSalaryField.setText(EMPTY);
            }
        } catch (BusinessLayerException e) {
            createOperationStatus.setTextFill(Color.RED);
            createOperationStatus.setText("Failed!");
            createErrorTextArea.setText(e.getMessage());
        }
    }

    private void handleConnection(){
        try {
            if(Objects.isNull(this.connection) || !connector.checkConnection(this.connection))
                this.connection = connector.connect();
            connectionText.setTextFill(Color.GREEN);
            connectionText.setText("Connection Status: Completed!");
            createErrorTextArea.setText(EMPTY);
            listErrorTextArea.setText(EMPTY);
        } catch (DatasourceConnectionException e) {
            connectionText.setTextFill(Color.RED);
            connectionText.setText("Connection Status: Failed!");
            createErrorTextArea.setText(e.getMessage());
            listErrorTextArea.setText(e.getMessage());
        }
    }

    private void prepareTableView(ObservableList<EmployeeDto> list){
        if(listTableView.getColumns().isEmpty()){
            TableColumn<EmployeeDto, Long> idColumn = new TableColumn<>("Id");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            TableColumn<EmployeeDto, Integer> ageColumn = new TableColumn<>("Age");
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
            TableColumn<EmployeeDto, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            TableColumn<EmployeeDto, Double> salaryColumn = new TableColumn<>("Salary");
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("Salary"));
            listTableView.getColumns().addAll(idColumn, ageColumn, nameColumn, salaryColumn);
        }
        listTableView.setItems(list);
    }
}
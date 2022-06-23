package com.denzhn.employeesystem;

import com.denzhn.employeesystem.datasource.dto.EmployeeDto;
import com.denzhn.employeesystem.datasource.service.EmployeeService;
import com.denzhn.employeesystem.datasource.service.impl.EmployeeServiceImpl;
import com.denzhn.employeesystem.exception.BusinessLayerException;
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

import java.util.Arrays;

public class ESController {

    private static final String EMPTY = "";
    private static final String SUCCESS = "Successful!";
    private static final String FAIL = "Failed!";
    private static final String NOT_VALID_FIELDS = "There are some empty fields.";
    private static final String FIELD_VALUE_ERROR = "Field values are not valid.";
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
    private Label deleteOperationStatus;
    @FXML
    private TextArea deleteErrorTextArea;
    @FXML
    private TextField deleteIdField;
    @FXML
    private TextField createNameField;
    @FXML
    private TextField createAgeField;
    @FXML
    private TextField createSalaryField;
    @FXML
    private TextField updateIdField;
    @FXML
    private TextField updateNameField;
    @FXML
    private TextField updateAgeField;
    @FXML
    private TextField updateSalaryField;
    @FXML
    private Label updateOperationStatus;
    @FXML
    private TextArea updateErrorTextArea;
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
            prepareTableView(FXCollections.observableArrayList(service.list()));
            listOperationStatus.setTextFill(Color.GREEN);
            listOperationStatus.setText(SUCCESS);
        } catch (BusinessLayerException e) {
            listOperationStatus.setTextFill(Color.RED);
            listOperationStatus.setText(FAIL);
            listErrorTextArea.setText(e.getMessage());
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            handleConnection();
            if(checkFields(deleteIdField.getText())){
                deleteOperationStatus.setTextFill(Color.RED);
                deleteOperationStatus.setText(FAIL);
                deleteErrorTextArea.setText(NOT_VALID_FIELDS);
                return;
            }
            if (service.delete(Long.parseLong(deleteIdField.getText()))){
                deleteOperationStatus.setTextFill(Color.GREEN);
                deleteOperationStatus.setText(SUCCESS);
                deleteErrorTextArea.setText(EMPTY);
            }
        } catch (BusinessLayerException e) {
            deleteOperationStatus.setTextFill(Color.RED);
            deleteOperationStatus.setText(FAIL);
            deleteErrorTextArea.setText(e.getMessage());
        } catch (NumberFormatException e){
            deleteOperationStatus.setTextFill(Color.RED);
            deleteOperationStatus.setText(FAIL);
            deleteErrorTextArea.setText(FIELD_VALUE_ERROR);
        }
    }

    @FXML
    protected void onUpdateButtonClick() {
        try {
            handleConnection();
            if (checkFields(updateIdField.getText(), updateNameField.getText(), updateAgeField.getText(), updateSalaryField.getText())){
                updateOperationStatus.setTextFill(Color.RED);
                updateOperationStatus.setText(FAIL);
                updateErrorTextArea.setText(NOT_VALID_FIELDS);
                return;
            }
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(Long.parseLong(updateIdField.getText()));
            employeeDto.setName(updateNameField.getText());
            employeeDto.setAge(Integer.parseInt(updateAgeField.getText()));
            employeeDto.setSalary(Double.parseDouble(updateSalaryField.getText()));
            if (service.update(employeeDto)){
                updateErrorTextArea.setText(EMPTY);
                updateIdField.setText(EMPTY);
                updateNameField.setText(EMPTY);
                updateAgeField.setText(EMPTY);
                updateSalaryField.setText(EMPTY);
                updateOperationStatus.setTextFill(Color.GREEN);
                updateOperationStatus.setText(SUCCESS);
            }
        } catch (BusinessLayerException e) {
            updateOperationStatus.setTextFill(Color.RED);
            updateOperationStatus.setText(FAIL);
            updateErrorTextArea.setText(e.getMessage());
        }  catch (NumberFormatException e){
            updateOperationStatus.setTextFill(Color.RED);
            updateOperationStatus.setText(FAIL);
            updateErrorTextArea.setText(FIELD_VALUE_ERROR);
        }
    }

    @FXML
    protected void onCreateButtonClick(){
        try {
            handleConnection();
            if (checkFields(createNameField.getText(), createAgeField.getText(), createSalaryField.getText())){
                createOperationStatus.setTextFill(Color.RED);
                createOperationStatus.setText(FAIL);
                createErrorTextArea.setText(NOT_VALID_FIELDS);
                return;
            }
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName(createNameField.getText());
            employeeDto.setAge(Integer.parseInt(createAgeField.getText()));
            employeeDto.setSalary(Double.parseDouble(createSalaryField.getText()));
            if (service.create(employeeDto)){
                createErrorTextArea.setText(EMPTY);
                createOperationStatus.setTextFill(Color.GREEN);
                createOperationStatus.setText(SUCCESS);
                createNameField.setText(EMPTY);
                createAgeField.setText(EMPTY);
                createSalaryField.setText(EMPTY);
            }
        } catch (BusinessLayerException e) {
            createOperationStatus.setTextFill(Color.RED);
            createOperationStatus.setText(FAIL);
            createErrorTextArea.setText(e.getMessage());
        } catch (NumberFormatException e){
            createOperationStatus.setTextFill(Color.RED);
            createOperationStatus.setText(FAIL);
            createErrorTextArea.setText(FIELD_VALUE_ERROR);
        }
    }

    private void handleConnection(){
        try {
            service.handleConnection();
            connectionText.setTextFill(Color.GREEN);
            connectionText.setText("Connection Status: " + SUCCESS);
            createErrorTextArea.setText(EMPTY);
            listErrorTextArea.setText(EMPTY);
            deleteErrorTextArea.setText(EMPTY);
            updateErrorTextArea.setText(EMPTY);
        } catch (BusinessLayerException e) {
            connectionText.setTextFill(Color.RED);
            connectionText.setText("Connection Status: " + FAIL);
            createErrorTextArea.setText(e.getMessage());
            listErrorTextArea.setText(e.getMessage());
            deleteErrorTextArea.setText(e.getMessage());
            updateErrorTextArea.setText(e.getMessage());
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

    private boolean checkFields(String... args){
        return Arrays.stream(args).anyMatch(String::isEmpty);
    }
}
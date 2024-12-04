package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tesda.tcsdi.simplepos.model.Employee;
import tesda.tcsdi.simplepos.model.dal.EmployeeFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeEditController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Employee, String> usernameCol;

    @FXML
    private TableColumn<Employee, String> nameCol;

    @FXML
    private TableColumn<Employee, String> roleCol;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleField;

    private Employee selectedEmployee;
    private final EmployeeFactory employeeFactory = new EmployeeFactory();
    private ObservableList<Employee> employeeList;
    private FilteredList<Employee> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeTableInit();
        textFieldValidations();
        roleComboBoxInit();
    }

    private void roleComboBoxInit() {
        ObservableList<String> roleOptions = FXCollections.observableArrayList("cashier", "manager");
        roleField.setItems(roleOptions);
        roleField.getSelectionModel().selectFirst();
    }

    private boolean areAllTextFieldValid() {
        boolean isValid = true;
        if(!isFieldValid(nameField)) isValid = false;
        if(!isFieldValid(usernameField)) isValid = false;
        if(!isFieldValid(passwordField)) isValid = false;
        return isValid;
    }

    private void textFieldValidations() {
        nameField.setOnKeyTyped(event -> isFieldValid(nameField));
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\w*[\\s+\\w*]*")) nameField.setText(oldValue);
        });
        usernameField.setOnKeyTyped(event -> isFieldValid(usernameField));
        usernameField.textProperty().addListener((observable,oldValue,newValue) -> {
            if (!newValue.matches("[\\w|\\d]*")) usernameField.setText(oldValue);
        });
        passwordField.setOnKeyTyped(event -> isFieldValid(passwordField));
    }

    private boolean isFieldValid(TextField textField) {
        boolean isNotBlank = true;
        String name = textField.getText();
        if(name == null || name.isBlank()) {
            isNotBlank = false;
        }
        String warningCSS = "-fx-border-color: red; -fx-background-color: #ffe6e6;";
        if(isNotBlank) textField.setStyle("");
        else textField.setStyle(warningCSS);
        return isNotBlank;
    }

    private void employeeTableInit() {
        employeeList = FXCollections.observableArrayList(employeeFactory.getAll());

        idCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
        roleCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));

        // For search
        filteredData = new FilteredList<>(employeeList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (employee.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (employee.getUsername().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
        employeeTable.setItems(sortedData);
    }

    private Employee textFieldToEmployee() {
        Employee employee = new Employee();
        int id = idField.getText() == null || idField.getText().isBlank() ? 0 : Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleField.getSelectionModel().getSelectedItem();
        employee.setId(id);
        employee.setName(name);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setRole(role);
        return employee;
    }

    @FXML
    void selectEmployeeFromTable(MouseEvent event) {
        selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if(selectedEmployee == null) return;
        idField.setText(String.valueOf(selectedEmployee.getId()));
        nameField.setText(selectedEmployee.getName());
        usernameField.setText(selectedEmployee.getUsername());
        passwordField.setText(selectedEmployee.getPassword());
        roleField.setValue(selectedEmployee.getRole());
    }

    @FXML
    void clearTextFields(ActionEvent event) {
        idField.clear();
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
//        roleField.getSelectionModel().clearSelection();
    }

    @FXML
    void newEmployee(ActionEvent event) {
        if(!areAllTextFieldValid()) return;
        Employee employee = textFieldToEmployee();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New employee confirmation");
        alert.setContentText("Are you sure you want to add \"" + employee.getName() + "\" as new employee?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            employeeFactory.create(employee);
            employeeTableInit();
        }
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        if(!areAllTextFieldValid()) return;
        Employee employee = textFieldToEmployee();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update employee confirmation");
        alert.setContentText("Are you sure you want to update \"" + employee.getName() + "\"'s employee details?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            employeeFactory.update(employee);
            employeeTableInit();
        }
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        if(!areAllTextFieldValid()) return;
        Employee employee = textFieldToEmployee();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete employee confirmation");
        alert.setContentText("Are you sure you want to delete \"" + employee.getName() + "\"?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            employeeFactory.delete(employee);
            employeeTableInit();
        }
    }
}

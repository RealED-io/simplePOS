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
import tesda.tcsdi.simplepos.model.Supplier;
import tesda.tcsdi.simplepos.model.dal.SupplierDB;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierEditController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, Integer> idCol;

    @FXML
    private TableColumn<Supplier, String> nameCol;

    @FXML
    private TableColumn<Supplier, String> phoneCol;

    @FXML
    private TableColumn<Supplier, String> emailCol;

    @FXML
    private TableColumn<Supplier, String> addressCol;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea addressField;

    private Supplier selectedSupplier;
    private final SupplierDB supplierFactory = new SupplierDB();
    private ObservableList<Supplier> supplierList;
    private FilteredList<Supplier> filteredData;

    @FXML
    void clearTextFields(ActionEvent event) {
        idField.clear();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
    }

    @FXML
    void selectSupplierFromTable(MouseEvent event) {
        selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if(selectedSupplier == null) return;
        idField.setText(String.valueOf(selectedSupplier.getId()));
        nameField.setText(selectedSupplier.getName());
        phoneField.setText(selectedSupplier.getPhoneNumber());
        emailField.setText(selectedSupplier.getEmail());
        addressField.setText(selectedSupplier.getAddress());
    }

    @FXML
    void deleteSupplier(ActionEvent event) {
        if(!isFieldValid(nameField)) return;
        Supplier supplier = textFieldToSupplier();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete supplier confirmation");
        alert.setContentText("Are you sure you want to delete \"" + supplier.getName() + "\"?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            supplierFactory.delete(supplier);
            supplierTableInit();
        }
    }

    @FXML
    void newSupplier(ActionEvent event) {
        if(!isFieldValid(nameField)) return;
        Supplier supplier = textFieldToSupplier();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New supplier confirmation");
        alert.setContentText("Are you sure you want to add \"" + supplier.getName() + "\" as new supplier?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            supplierFactory.create(supplier);
            supplierTableInit();
        }
    }

    @FXML
    void updateSupplier(ActionEvent event) {
        if(!isFieldValid(nameField)) return;
        Supplier supplier = textFieldToSupplier();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update supplier confirmation");
        alert.setContentText("Are you sure you want to update \"" + supplier.getName() + "\"'s supplier details?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            supplierFactory.update(supplier);
            supplierTableInit();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierTableInit();
        nameField.setOnKeyTyped(event -> isFieldValid(nameField));
    }

    private void supplierTableInit() {
        supplierList = FXCollections.observableArrayList(supplierFactory.getAll());

        idCol.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phoneNumber"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("address"));

        // For search
        filteredData = new FilteredList<>(supplierList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(supplier -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (supplier.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (supplier.getEmail().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (supplier.getAddress().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (supplier.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });
        SortedList<Supplier> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(supplierTable.comparatorProperty());
        supplierTable.setItems(sortedData);
    }

    private boolean isFieldValid(TextInputControl textField) {
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

    private Supplier textFieldToSupplier() {
        Supplier supplier = new Supplier();
        int id = idField.getText() == null || idField.getText().isBlank() ? 0 : Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        supplier.setId(id);
        supplier.setName(name);
        supplier.setPhoneNumber(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        return supplier;
    }
}

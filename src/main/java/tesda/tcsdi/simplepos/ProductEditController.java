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
import tesda.tcsdi.simplepos.model.Category;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.Supplier;
import tesda.tcsdi.simplepos.model.dal.CategoryFactory;
import tesda.tcsdi.simplepos.model.dal.ProductFactory;
import tesda.tcsdi.simplepos.model.dal.SupplierFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductEditController implements Initializable{

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private TableColumn<Product, String> barcodeCol;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableColumn<Product, Integer> quantityCol;

    @FXML
    private TableColumn<Product, String> quantityTypeCol;

    @FXML
    private TableColumn<Product, String> categoryCol;

    @FXML
    private TableColumn<Product, String> supplierCol;

    @FXML
    private TextField searchField;

    @FXML
    private TextField idField;

    @FXML
    private TextField barcodeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> quantityTypeField;

    @FXML
    private ComboBox<String> categoryField;

    @FXML
    private ComboBox<String> supplierField;

    private Product selectedItem;
    private final ProductFactory productFactory = new ProductFactory();
    private final CategoryFactory categoryFactory = new CategoryFactory();
    private final SupplierFactory supplierFactory = new SupplierFactory();
    private ObservableList<Product> productList;
    private FilteredList<Product> filteredData;
    private ObservableList<String> categoryOptions;
    private ObservableList<String> supplierOptions;

    @FXML
    void newProduct(ActionEvent event) {
        if (!areValidTextFields()) return;
        Product product = textFieldToProduct();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New item confirmation");
        alert.setContentText("Are you sure you want to add \"" + product.getName() + "\" as a new item?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            productFactory.create(product);
            productTableInit();
        }
    }

    @FXML
    void updateProduct(ActionEvent event) {
        if (!areValidTextFields()) return;
        Product product = textFieldToProduct();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update item confirmation");
        alert.setContentText("Are you sure you want to update \"" + product.getName() + "\"?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            productFactory.update(product);
            productTableInit();
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        if (!areValidTextFields()) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New item confirmation");
        alert.setContentText("Are you sure you want to add \"" + selectedItem.getName() + "\" as a new item?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            if (selectedItem.getId() != 0) productFactory.delete(selectedItem);
            productTableInit();
        }
    }

    @FXML
    void clearTextFields(ActionEvent event) {
        idField.clear();
        nameField.clear();
        barcodeField.clear();
        priceField.clear();
        quantityField.clear();
        quantityTypeField.getSelectionModel().clearSelection();
        categoryField.getSelectionModel().clearSelection();
        supplierField.getSelectionModel().clearSelection();
    }

    @FXML
    void selectProductFromTable(MouseEvent event) {
        selectedItem = productTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null) return;
        idField.setText(String.valueOf(selectedItem.getId()));
        nameField.setText(selectedItem.getName());
        barcodeField.setText(selectedItem.getBarcode());
        priceField.setText(String.valueOf(selectedItem.getPrice()));
        quantityField.setText(String.valueOf(selectedItem.getInventoryQuantity()));
        quantityTypeField.setValue(selectedItem.getQuantityType());
        categoryField.setValue(selectedItem.getCategory());
        supplierField.setValue(selectedItem.getSupplier());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTableInit();
        quantityTypeComboBoxInit();
        categoryComboBoxInit();
        supplierComboBoxInit();

        nameField.setOnKeyTyped(event -> areValidTextFields());
        barcodeField.setOnKeyTyped(event -> areValidTextFields());
        priceField.setOnKeyTyped(event -> areValidTextFields());
        quantityField.setOnKeyTyped(event -> areValidTextFields());
        quantityTypeField.setOnAction(event -> areValidTextFields());
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) quantityField.setText(oldValue);
        });
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) priceField.setText(oldValue);
        });
        }

    private void supplierComboBoxInit() {
        supplierOptions = FXCollections.observableArrayList(
                supplierFactory.getAll().stream()
                        .map(Supplier::getName)
                        .toList()
        );
        supplierField.setItems(supplierOptions);
    }

    private void quantityTypeComboBoxInit() {
        ObservableList<String> quantityTypeOptions = FXCollections.observableArrayList("counted", "weighted (kg)");
        quantityTypeField.setItems(quantityTypeOptions);
    }

    private void categoryComboBoxInit() {
        categoryOptions = FXCollections.observableArrayList(
                categoryFactory.getAll().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList())
        );
        categoryField.setItems(categoryOptions);
    }

    private void productTableInit() {
        productList = FXCollections.observableArrayList(productFactory.getAll());
        // Product CellValueFactory
        idCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        barcodeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("inventoryQuantity"));
        quantityTypeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("quantityType"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<Product, String>("supplier"));

        // For search
        filteredData = new FilteredList<>(productList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (product.getBarcode().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }

    private Product textFieldToProduct() {
        Product product = new Product();
        if(!idField.getText().isBlank()) product.setId(Integer.parseInt(idField.getText()));
        product.setBarcode(barcodeField.getText());
        product.setName(nameField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setInventoryQuantity(Integer.parseInt(quantityField.getText()));
        product.setQuantityType(quantityTypeField.getValue());
        product.setCategory(categoryField.getValue());
        if(product.getCategory() == null) product.setCategoryId(0);
        else if (!categoryOptions.contains(product.getCategory())) {
            Category newCategory = new Category();
            newCategory.setName(product.getCategory());
            newCategory = categoryFactory.create(newCategory);
            product.setCategoryId(newCategory.getId());
            categoryOptions.add(newCategory.getName());
        } else product.setCategoryId(categoryFactory.getByName(product.getCategory()).getId());

        product.setSupplier(supplierField.getValue());
        if(product.getSupplier() == null) product.setSupplierId(0);
        else if(!supplierOptions.contains(product.getSupplier())) {
            Supplier newSupplier = new Supplier();
            newSupplier.setName(product.getSupplier());
            newSupplier = supplierFactory.create(newSupplier);
            product.setSupplierId(newSupplier.getId());
            supplierOptions.add(newSupplier.getName());
        } else product.setSupplierId(supplierFactory.getByName(product.getSupplier()).getId());
        return product;
    }

    private boolean areValidTextFields() {
        String warningCSS = "-fx-border-color: red; -fx-background-color: #ffe6e6;";
        boolean valid = true;
        if(nameField.getText() == null || nameField.getText().isBlank()) {
            valid = false;
            nameField.setStyle(warningCSS);
        } else nameField.setStyle("");
        if(barcodeField.getText() == null || barcodeField.getText().isBlank() || barcodeField.getText().length() > 15 ||
                (idField.getText().isBlank() && productList.stream().map(Product::getBarcode).toList().contains(barcodeField.getText()))) {
            valid = false;
            barcodeField.setStyle(warningCSS);
        } else barcodeField.setStyle("");
        try {
            Double price = Double.valueOf(priceField.getText());
            priceField.setStyle("");
        } catch (Exception e) {
            valid = false;
            priceField.setStyle(warningCSS);
        }
        try {
            int quantity = Integer.valueOf(quantityField.getText());
            quantityField.setStyle("");
        } catch (Exception e) {
            valid = false;
            quantityField.setStyle(warningCSS);
        }
        if(quantityTypeField.getSelectionModel().isEmpty()) {
            valid = false;
            quantityTypeField.setStyle(warningCSS);
        } else {
            quantityTypeField.setStyle("");
        }
        return valid;
    }
}

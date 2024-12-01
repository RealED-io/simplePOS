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
import org.w3c.dom.events.MouseEvent;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.dal.ProductDB;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductEditController implements Initializable{

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, String> productBarcode;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Double> productPrice;

    @FXML
    private TableColumn<Product, Integer> productQuantity;

    @FXML
    private TableColumn<Product, String> productQuantityType;

    @FXML
    private TableColumn<Product, String> productCategory;

    @FXML
    private TableColumn<Product, String> productSupplier;

    @FXML
    private TextField searchField;

    @FXML
    void createProduct(ActionEvent event) {

    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setContentText("Are you sure you want to delete \"" + selectedItem.getName() + "\"?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            ProductDB productFactory = new ProductDB();
            productFactory.delete(selectedItem);
            filteredData.getSource().remove(selectedItem);
            selectedItem = null;
        }
    }

    @FXML
    void updateProduct(ActionEvent event) {
        
    }

    private Product selectedItem;
    private final ProductDB productFactory = new ProductDB();
    private FilteredList<Product> filteredData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Product> productList = FXCollections.observableArrayList(productFactory.getAll());
        // Product CellValueFactory
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productBarcode.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("inventoryQuantity"));
        productQuantityType.setCellValueFactory(new PropertyValueFactory<Product, String>("quantityType"));
        productCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        productSupplier.setCellValueFactory(new PropertyValueFactory<Product, String>("supplier"));

        // For search, filteredData contains data that does not fit filter
        filteredData = new FilteredList<>(productList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true; // Filter matches first name.
                else if (product.getBarcode().toLowerCase().contains(lowerCaseFilter)) return true; // Filter matches last name.
                else return false; // Does not match.
            });
        });


        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }


    @FXML
    public void onItemClicked(javafx.scene.input.MouseEvent mouseEvent) {
        selectedItem = productTable.getSelectionModel().getSelectedItem();
    }
}

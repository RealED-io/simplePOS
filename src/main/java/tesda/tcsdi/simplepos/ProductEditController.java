package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.dal.ProductDB;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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

    @FXML
    void createProduct(ActionEvent event) {


    }

    @FXML
    void newProduct(ActionEvent event) {
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
    void updateProduct(ActionEvent event) throws IOException {
//        ViewUtil.getStage(event).getScene().getRoot();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("product-dialog.fxml"));
//        Parent root = loader.load();
//        ProductDialogController controller = loader.getController();
//        controller.passProductToController(selectedItem);
//        Stage stage = ViewUtil.getStage(event);
//        stage.setScene(new Scene(root));
//        stage.show();
    }

    private Product selectedItem;
    private final ProductDB productFactory = new ProductDB();
    private FilteredList<Product> filteredData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Product> productList = FXCollections.observableArrayList(productFactory.getAll());
        // Product CellValueFactory
        idCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        barcodeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("inventoryQuantity"));
        quantityTypeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("quantityType"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<Product, String>("supplier"));

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
    public void onItemClicked(MouseEvent mouseEvent) {
        selectedItem = productTable.getSelectionModel().getSelectedItem();
    }
}

package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tesda.tcsdi.simplepos.model.Sales;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.Supplier;
import tesda.tcsdi.simplepos.model.dal.SalesFactory;
import tesda.tcsdi.simplepos.model.dal.ProductFactory;
import tesda.tcsdi.simplepos.model.dal.SupplierFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    @FXML
    private TextField inventorySearchField;

    @FXML
    private TableView<Product> inventoryReportTable;

    @FXML
    private TableColumn<Product, Integer> inventoryIdCol;

    @FXML
    private TableColumn<Product, String> inventoryNameCol;

    @FXML
    private TableColumn<Product, Integer> inventoryQuantityCol;

    @FXML
    private TextField salesSearchField;

    @FXML
    private TableView<Sales> salesReportTable;

    @FXML
    private TableColumn<Sales, Integer> salesProductIdCol;

    @FXML
    private TableColumn<Sales, String> salesNameCol;

    @FXML
    private TableColumn<Sales, Integer> salesQuantityCol;

    @FXML
    private TableColumn<Sales, Double> salesUnitCol;

    @FXML
    private TableColumn<Sales, Double> salesTotalCol;

    @FXML
    private TextField supplierId;

    @FXML
    private TextField supplierName;

    @FXML
    private TextField supplierPhone;

    @FXML
    private TextField supplierEmail;

    @FXML
    private TextArea supplierAddress;

    private Supplier supplierOfSelecteditem;
    private final SupplierFactory supplierFactory = new SupplierFactory();
    private final SalesFactory salesFactory = new SalesFactory();
    private final ProductFactory productFactory = new ProductFactory();
    private ObservableList<Sales> salesList;
    private FilteredList<Sales> salesFilterData;
    private ObservableList<Product> productList;
    private FilteredList<Product> productFilterData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salesReportTableInit();
        inventoryReportTableInit();
    }

    private void inventoryReportTableInit() {
        productList = FXCollections.observableArrayList(productFactory.getAll());
        inventoryIdCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        inventoryNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        inventoryQuantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("inventoryQuantity"));
        // For search
        productFilterData = new FilteredList<>(productList, b -> true);
        inventorySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            productFilterData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });
        SortedList<Product> sortedData = new SortedList<>(productFilterData);
        sortedData.comparatorProperty().bind(inventoryReportTable.comparatorProperty());
        inventoryReportTable.setItems(sortedData);
        inventoryReportTable.getSortOrder().add(inventoryQuantityCol);
    }

    private void salesReportTableInit() {
        salesList = FXCollections.observableArrayList(salesFactory.getSalesReport());
        salesProductIdCol.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("productId"));
        salesNameCol.setCellValueFactory(new PropertyValueFactory<Sales, String>("product"));
        salesQuantityCol.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("quantity"));
        salesUnitCol.setCellValueFactory(new PropertyValueFactory<Sales, Double>("unitPrice"));
        salesTotalCol.setCellValueFactory(new PropertyValueFactory<Sales, Double>("totalPrice"));
        // For search
        salesFilterData = new FilteredList<>(salesList, b -> true);
        salesSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            salesFilterData.setPredicate(sales -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (sales.getProduct().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });
        SortedList<Sales> sortedData = new SortedList<>(salesFilterData);
        sortedData.comparatorProperty().bind(salesReportTable.comparatorProperty());
        salesReportTable.setItems(sortedData);
        salesReportTable.getSortOrder().add(salesTotalCol);
    }

    @FXML
    void selectFromInventoryTable(MouseEvent event) {
        Product selectedProduct = inventoryReportTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) return;
        supplierOfSelecteditem = supplierFactory.getById(selectedProduct.getSupplierId());
        setSupplierTextField();
    }

    @FXML
    void selectFromSalesTable(MouseEvent event) {
        Sales selectedSale = salesReportTable.getSelectionModel().getSelectedItem();
        if(selectedSale == null) return;
        Product selectedProduct = productFactory.getById(selectedSale.getProductId());
        supplierOfSelecteditem = supplierFactory.getById(selectedProduct.getSupplierId());
        setSupplierTextField();
    }

    private void setSupplierTextField() {
        if(supplierOfSelecteditem == null){
         supplierId.clear();
         supplierName.clear();
         supplierPhone.clear();
         supplierEmail.clear();
         supplierAddress.clear();
         return;
        }
        int id = supplierOfSelecteditem.getId();
        String name = supplierOfSelecteditem.getName();
        String phone = supplierOfSelecteditem.getPhoneNumber();
        String email = supplierOfSelecteditem.getEmail();
        String address = supplierOfSelecteditem.getAddress();

        supplierId.setText(String.valueOf(id));
        supplierName.setText(name);
        supplierPhone.setText(phone);
        supplierEmail.setText(email);
        supplierAddress.setText(address);
    }
}

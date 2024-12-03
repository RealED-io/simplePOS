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
import tesda.tcsdi.simplepos.model.PerItemSale;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.Supplier;
import tesda.tcsdi.simplepos.model.dal.PerItemSaleDB;
import tesda.tcsdi.simplepos.model.dal.ProductDB;
import tesda.tcsdi.simplepos.model.dal.SupplierDB;

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
    private TableView<PerItemSale> salesReportTable;

    @FXML
    private TableColumn<PerItemSale, Integer> salesProductIdCol;

    @FXML
    private TableColumn<PerItemSale, String> salesNameCol;

    @FXML
    private TableColumn<PerItemSale, Integer> salesQuantityCol;

    @FXML
    private TableColumn<PerItemSale, Double> salesUnitCol;

    @FXML
    private TableColumn<PerItemSale, Double> salesTotalCol;

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
    private final SupplierDB supplierFactory = new SupplierDB();
    private final PerItemSaleDB salesFactory = new PerItemSaleDB();
    private final ProductDB productFactory = new ProductDB();
    private ObservableList<PerItemSale> salesList;
    private FilteredList<PerItemSale> salesFilterData;
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
        salesProductIdCol.setCellValueFactory(new PropertyValueFactory<PerItemSale, Integer>("productId"));
        salesNameCol.setCellValueFactory(new PropertyValueFactory<PerItemSale, String>("product"));
        salesQuantityCol.setCellValueFactory(new PropertyValueFactory<PerItemSale, Integer>("quantity"));
        salesUnitCol.setCellValueFactory(new PropertyValueFactory<PerItemSale, Double>("unitPrice"));
        salesTotalCol.setCellValueFactory(new PropertyValueFactory<PerItemSale, Double>("totalPrice"));
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
        SortedList<PerItemSale> sortedData = new SortedList<>(salesFilterData);
        sortedData.comparatorProperty().bind(salesReportTable.comparatorProperty());
        salesReportTable.setItems(sortedData);
        salesReportTable.getSortOrder().add(salesTotalCol);
    }

    @FXML
    void selectFromInventoryTable(MouseEvent event) {
        Product selectedProduct = inventoryReportTable.getSelectionModel().getSelectedItem();
        supplierOfSelecteditem = supplierFactory.getById(selectedProduct.getSupplierId());
        setSupplierField();
    }

    @FXML
    void selectFromSalesTable(MouseEvent event) {
        PerItemSale selectedSale = salesReportTable.getSelectionModel().getSelectedItem();
        Product selectedProduct = productFactory.getById(selectedSale.getProductId());
        supplierOfSelecteditem = supplierFactory.getById(selectedProduct.getSupplierId());
        setSupplierField();
    }

    private void setSupplierField() {
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

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
import tesda.tcsdi.simplepos.model.Invoice;
import tesda.tcsdi.simplepos.model.Sales;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.dal.InvoiceFactory;
import tesda.tcsdi.simplepos.model.dal.SalesFactory;
import tesda.tcsdi.simplepos.model.dal.ProductFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class CashierController implements Initializable {

    @FXML
    private TableView<Product> cartTable;

    @FXML
    private TableColumn<Product, String> cartName;

    @FXML
    private TableColumn<Product, Integer> cartQuantity;

    @FXML
    private TableColumn<Product, Double> cartAmount;

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
    private TableColumn<Product, Integer> productRemainingQuantity;

    @FXML
    private TableColumn<Product, String> productQuantityType;

    @FXML
    private TableColumn<Product, String> productCategory;


    @FXML
    private Button addToCartButton;

    @FXML
    private Button cartChangeQuantityButton;

    @FXML
    private Spinner<Integer> cartQuantitySpinner;

    @FXML
    private Spinner<Integer> productQuantitySpinner;

    @FXML
    private Button removeItemToCartButton;

    @FXML
    private TextField searchField;

    @FXML
    private TextField totalAmountText;

    private Product selectedProductItem;
    private Product selectedCartItem;
    private double totalAmount;
    private final ProductFactory productFactory = new ProductFactory();
    private ObservableList<Product> productList;
    private ObservableList<Product> cartList;
    private Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        totalAmount = 0;
        productList = FXCollections.observableArrayList(productFactory.getAll());
        for(Product product : productList) {
            product.setRemainingQuantity(product.getInventoryQuantity());
            product.setCartQuantity(0);
        }

        cartList = cartTable.getItems();

        // Cart CellValueFactory
        cartName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        cartAmount.setCellValueFactory(new PropertyValueFactory<Product, Double>("cartSubtotalAmount"));
        cartQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("cartQuantity"));

        // Product CellValueFactory
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productBarcode.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productRemainingQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("remainingQuantity"));
        productQuantityType.setCellValueFactory(new PropertyValueFactory<Product, String>("quantityType"));
        productCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));

        FilteredList<Product> filteredData = new FilteredList<>(productList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (product.getBarcode().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false; // Does not match.
            });
        });
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);

    }

    @FXML
    void cartTableRowClicked(MouseEvent event) {
        selectedCartItem = cartTable.getSelectionModel().getSelectedItem();
        updateCartTableSpinnerValue();
    }

    @FXML
    void productTableRowClicked(MouseEvent event) {
        selectedProductItem = productTable.getSelectionModel().getSelectedItem();
        updateProductTableSpinnerValue();
    }

    // TODO: check if item already exist at Cart
    @FXML
    void addItemToCart(MouseEvent event) {
        // Selected item/row in product TableView validity checking
        if(selectedProductItem == null) return;
        if(selectedProductItem.getInventoryQuantity() <= 0) return;
        if(selectedProductItem.getRemainingQuantity() <= 0) return;

        // Prevents error
        cartList = cartTable.getItems();

        // Checks if selected product to be added to cart is already at the cart
        selectedProductItem = addCartQuantity(selectedProductItem, productQuantitySpinner.getValue());
        for(Product cartItem : cartList) {
            if (cartItem.equals(selectedProductItem)) {
                saveToTable(cartList, selectedProductItem);
                saveToTable(productList, selectedProductItem);
                updateTotalAmount();
                return;
            }
        }
        // Case of first add to cart of item
        cartList.add(selectedProductItem);
        saveToTable(productList, selectedProductItem);
        updateTotalAmount();
    }

    @FXML
    void changeCartItemQuantity(MouseEvent event) {
        // Selected item/row in cart TableView validity checking
        if(selectedCartItem == null) return;
        if(selectedCartItem.getCartQuantity() <= 0) return;

        selectedCartItem = addCartQuantity(selectedCartItem,
                -selectedCartItem.getCartQuantity() + cartQuantitySpinner.getValue());
        saveToTable(productList, selectedCartItem);
        if (selectedCartItem.getCartQuantity() == 0) {
            cartList.remove(selectedCartItem);
            updateTotalAmount();
            return;
        }
        saveToTable(cartList, selectedCartItem);
        updateTotalAmount();
    }

    @FXML
    void removeItemToCart(MouseEvent event) {
        // Selected item/row in cart TableView validity checking
        if (selectedCartItem == null) return;
        if(selectedCartItem.getCartQuantity() <= 0) return;
        // Resets the quantities of the removed product
        selectedCartItem = addCartQuantity(selectedCartItem, -selectedCartItem.getCartQuantity());
        cartList.remove(selectedCartItem);
        saveToTable(productList, selectedCartItem);
        selectedCartItem = null;
        updateTotalAmount();
    }

    // TODO: update InvoiceFactory
    @FXML
    void checkoutCart(MouseEvent event) {
        // create invoice

        Invoice invoice = new Invoice();
        invoice.setEmployeeId(employee.getId());
        invoice.setTotalAmount(totalAmount);
        // store invoice to db
        InvoiceFactory invoiceFactory = new InvoiceFactory();
        invoice = invoiceFactory.create(invoice);
        String fileName = "invoice-" + invoice.getId() + "-" +
                invoice.getIssueDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyMMdd_HHmmss")) + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("invoice id: " + invoice.getId());
            printWriter.println("date: " + invoice.getIssueDate());
            printWriter.println("-".repeat(45));

            for(Product cartItem : cartList) {
                // store to per_item_sales table
                cartItem.setInventoryQuantity(cartItem.getRemainingQuantity());
                productFactory.update(cartItem);
                // store transaction to per_item_sales table
                Sales sale = new Sales();
                sale.setInvoiceId(invoice.getId());
                sale.setProductId(cartItem.getId());
                sale.setQuantity(cartItem.getCartQuantity());
                sale.setUnitPrice(cartItem.getPrice());
                SalesFactory SalesFactory = new SalesFactory();
                SalesFactory.create(sale);
                // sys out receipt
                String lineItem = cartItem.getCartQuantity() + " x " +
                        truncateOrPad(cartItem.getName(), 30) + " = " + cartItem.getCartSubtotalAmount();
                printWriter.println(lineItem);
                System.out.println(lineItem);
            }

            printWriter.println("-".repeat(45));
            printWriter.println("Total Amount: " + totalAmount);
            System.out.println("Total Amount: " + totalAmount);

            printWriter.flush(); // Ensure content is written to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartList.clear();
        updateTotalAmount();
        updateProductTableSpinnerValue();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Checkout");
        alert.setContentText("Congratulations on your purchase!");
        alert.showAndWait();
    }

    private void updateTotalAmount() {
        totalAmount = 0;
        for(Product cartItem : cartList) {
            totalAmount += cartItem.getPrice() * cartItem.getCartQuantity();
        }
        totalAmount = round(totalAmount * 100) / 100.0;
        totalAmountText.setText(String.valueOf(totalAmount));
    }

    private void updateProductTableSpinnerValue() {
        if (selectedProductItem == null) return;
        if (selectedProductItem.getRemainingQuantity() <= 0) {
            productQuantitySpinner.setDisable(true);
            addToCartButton.setDisable(true);
            return;
        }
        productQuantitySpinner.setDisable(false);
        addToCartButton.setDisable(false);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, selectedProductItem.getRemainingQuantity());
        valueFactory.setValue(1);
        productQuantitySpinner.setValueFactory(valueFactory);
    }

    private void updateCartTableSpinnerValue() {
        if (selectedCartItem == null) return;
        if (selectedCartItem.getCartQuantity() <= 0) {
            cartQuantitySpinner.setDisable(true);
            removeItemToCartButton.setDisable(true);
            cartChangeQuantityButton.setDisable(true);
            return;
        }
        cartQuantitySpinner.setDisable(false);
        removeItemToCartButton.setDisable(false);
        cartChangeQuantityButton.setDisable(false);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, selectedCartItem.getCartQuantity());
        valueFactory.setValue(selectedCartItem.getCartQuantity());
        cartQuantitySpinner.setValueFactory(valueFactory);
    }

    private Product addCartQuantity(Product selected, int plusMinusQuantity) {
        selected.setCartQuantity(selected.getCartQuantity() + plusMinusQuantity);
        selected.setRemainingQuantity(selected.getInventoryQuantity() - selected.getCartQuantity());
        selected.setCartSubtotalAmount(round(selected.getPrice() * selected.getCartQuantity() * 100) / 100.0);
        updateCartTableSpinnerValue();
        updateProductTableSpinnerValue();
        return selected;
    }

    private void saveToTable(ObservableList<Product> list, Product selected) {
        list.set(list.indexOf(selected), selected);
    }

    @FXML
    void logout(ActionEvent event) {
        cartList.clear();
        updateTotalAmount();
        updateCartTableSpinnerValue();
        updateProductTableSpinnerValue();
        ViewSwitcher.switchToLoginUI(ViewSwitcher.getStage(event));
    }

    public void passEmployeeToController(Employee employee) {
        this.employee = employee;
    }

    private String truncateOrPad(String input, int length) {
        if (input == null) {
            input = "";
        }
        if (input.length() > length) {
            return input.substring(0, length - 3) + "..."; // Keep space for ellipses
        }
        return String.format("%-" + length + "s", input);
    }
}

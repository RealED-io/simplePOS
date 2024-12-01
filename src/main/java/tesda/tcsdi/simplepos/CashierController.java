package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.dal.ProductDB;

import java.net.URL;
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

    // TODO: Remove or retain?
    private final ProductDB productFactory = new ProductDB();
    private ObservableList<Product> productList;
    private ObservableList<Product> cartList;

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

//        productTable.setItems(productList);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Product> filteredData = new FilteredList<>(productList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (product.getBarcode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
//                else if (String.valueOf(product.getSalary()).indexOf(lowerCaseFilter)!=-1)
//                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
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

    // TODO: checkoutCart
    @FXML
    void checkoutCart(MouseEvent event) {
        for(Product cartItem : cartList) {
            cartItem.setInventoryQuantity(cartItem.getRemainingQuantity());
            productFactory.update(cartItem);
            // Temporary println
            System.out.println("Checked out: " + cartItem.getCartQuantity() + " x " + cartItem.getName()
                    + " = " + cartItem.getCartSubtotalAmount());
        }
        System.out.println("Total Amount: " + totalAmount);
        cartList.clear();
        updateTotalAmount();
        updateProductTableSpinnerValue();
        // TODO: make dialog box
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
}

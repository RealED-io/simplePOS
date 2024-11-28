package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.dal.ProductDB;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TableColumn<Product, Integer> productQuantity;

    @FXML
    private TableColumn<Product, String> productQuantityType;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private Button cartChangeQuantityButton;

    @FXML
    private ComboBox<?> filterDropdown;

    @FXML
    private Spinner<Integer> cartQuantitySpinner;

    @FXML
    private Spinner<Integer> productQuantitySpinner;

    @FXML
    private Button removeItemButton;

    @FXML
    private Button searchButton;

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
        cartList = cartTable.getItems();
        // Cart CellValueFactory
        cartName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        cartAmount.setCellValueFactory(new PropertyValueFactory<Product, Double>("amount"));
        cartQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        // Product CellValueFactory
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productBarcode.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        productQuantityType.setCellValueFactory(new PropertyValueFactory<Product, String>("quantityType"));

        productTable.setItems(productList);
    }


    @FXML
    void cartTableRowClicked(MouseEvent event) {
        selectedCartItem = cartTable.getSelectionModel().getSelectedItem();
        setCartQuantitySpinnerValue();
    }

    @FXML
    void productTableRowClicked(MouseEvent event) {
        selectedProductItem = productTable.getSelectionModel().getSelectedItem();
        setProductQuantitySpinnerValue();
    }

    // TODO: check if item already exist at Cart
    @FXML
    void addItemToCart(MouseEvent event) {
        if(selectedProductItem == null) return;
        if(selectedProductItem.getQuantity() <= 0) return;
        // case if product already exist at cart
        if(!cartList.isEmpty()) {
            for(Product cartItem : cartList) {
                if (cartItem.getId() == selectedProductItem.getId()) {
                    updateTableQuantity(cartList, cartItem, productQuantitySpinner.getValue());
                    updateTableAmount(cartList, cartItem);
                    updateTableQuantity(productList, selectedProductItem, -productQuantitySpinner.getValue());
                    setProductQuantitySpinnerValue();
                    // TODO: codecleanup
                    updateTotalAmount();
                    return;
                }
            }
        }
        // Create product partial clone specifically for adding to cart
        Product cartItem = new Product();
        cartItem.setId(selectedProductItem.getId());
        cartItem.setName(selectedProductItem.getName());
        cartItem.setQuantity(productQuantitySpinner.getValue());
        cartItem.setPrice(selectedProductItem.getPrice());
        cartItem.setAmount(selectedProductItem.getPrice() * productQuantitySpinner.getValue());
        cartList = cartTable.getItems();
        cartList.add(cartItem);

        // TODO: codecleanup
        updateTotalAmount();

        updateTableQuantity(productList, selectedProductItem, -productQuantitySpinner.getValue());
        setProductQuantitySpinnerValue();
    }

    @FXML
    void changeCartItemQuantity(MouseEvent event) {

    }

    @FXML
    void checkoutCart(MouseEvent event) {

    }

    @FXML
    void removeItemToCart(MouseEvent event) {
        if (selectedCartItem == null) return;
        cartList.remove(selectedCartItem);
        int id = selectedCartItem.getId();
        for(Product product:productList) {
            if (product.getId() == id) {
                updateTableQuantity(productList, product, selectedCartItem.getQuantity());
                selectedCartItem = null;
                break;
            }
        }
        updateTotalAmount();
//        setCartQuantitySpinnerValue();
//        setProductQuantitySpinnerValue();
    }

    @FXML
    void searchProduct(MouseEvent event) {

    }

    private void setProductQuantitySpinnerValue() {
        if (selectedProductItem == null) return;
        int quantity = selectedProductItem.getQuantity();
        if (quantity <= 0) {
            productQuantitySpinner.setDisable(true);
            addToCartButton.setDisable(true);
            return;
        }
        productQuantitySpinner.setDisable(false);
        addToCartButton.setDisable(false);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, selectedProductItem.getQuantity());
        valueFactory.setValue(1);
        productQuantitySpinner.setValueFactory(valueFactory);
    }

    private void setCartQuantitySpinnerValue() {
        if (selectedCartItem == null) return;
        int quantity = selectedCartItem.getQuantity();
        if (quantity <= 0) {
            cartQuantitySpinner.setDisable(true);
            return;
        }
        cartQuantitySpinner.setDisable(false);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, quantity);
        valueFactory.setValue(1);
        cartQuantitySpinner.setValueFactory(valueFactory);
    }

    private boolean contains(ObservableList<Product> table, Product product){
        for(Product item: table)
            if (item.getId() == product.getId()) return true;
        return false;
    }

    private void updateTableQuantity(ObservableList<Product> list, Product selected, int quantity) {
        Product item = list.get(list.indexOf(selected));
        item.setQuantity(item.getQuantity() + quantity);
        list.set(list.indexOf(selected), item);
    }

    private void updateTableAmount(ObservableList<Product> list, Product selected) {
        Product item = list.get(list.indexOf(selected));
        item.setAmount(item.getQuantity() * item.getPrice());
        list.set(list.indexOf(selected), item);
    }
    // TODO: codecleanup
    private void updateTotalAmount() {
        totalAmount = 0;
        for(Product cartItem : cartList) {
            totalAmount += cartItem.getPrice() * cartItem.getQuantity();
        }
        totalAmountText.setText(String.valueOf(totalAmount));
    }
}

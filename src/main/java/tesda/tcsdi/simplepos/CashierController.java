package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tesda.tcsdi.simplepos.model.Cart;
import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.dal.ProductDB;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierController implements Initializable {

    @FXML
    private TableView<Cart> cartTable;

    @FXML
    private TableColumn<Cart, String> cartName;

    @FXML
    private TableColumn<Cart, Integer> cartQuantity;

    @FXML
    private TableColumn<Cart, Double> cartAmount;

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
    private Spinner<?> cartQuantitySpinner;

    @FXML
    private Spinner<?> productQuantitySpinner;

    @FXML
    private Button removeItemButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TextField totalAmountText;

    // TODO: Remove after testing
    ObservableList<Cart> cartList = FXCollections.observableArrayList(
            new Cart(1, "Test 1", 15, 25.0),
            new Cart(2, "Test 2", 12, 21.0),
            new Cart(3, "Test 3", 11, 15.0)
    );

    // TODO: Remove or retain?
    ProductDB productFactory = new ProductDB();
    ObservableList<Product> productList = FXCollections.observableArrayList(productFactory.getAll());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cart CellValueFactory
        cartName.setCellValueFactory(new PropertyValueFactory<Cart, String>("name"));
        cartAmount.setCellValueFactory(new PropertyValueFactory<Cart, Double>("amount"));
        cartQuantity.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("quantity"));

        cartTable.setItems(cartList);

        // Product CellValueFactory
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productBarcode.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        productQuantityType.setCellValueFactory(new PropertyValueFactory<Product, String>("quantityType"));

        System.out.println(productList);
        productTable.setItems(productList);

    }

    @FXML
    void addItemToCart(MouseEvent event) {

    }

    @FXML
    void changeCartItemQuantity(MouseEvent event) {

    }

    @FXML
    void checkoutCart(MouseEvent event) {

    }

    @FXML
    void removeItemToCart(MouseEvent event) {

    }

    @FXML
    void searchProduct(MouseEvent event) {

    }
}

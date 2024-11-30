package tesda.tcsdi.simplepos.model;

public class Product implements Model{
    private int id;
    private String name;
    private String barcode;
    private double price;
    private String category;
    private int inventoryQuantity;
    private String quantityType;
    private int supplierId;
    private String supplier;
    // Only used for cart items
    private Double cartSubtotalAmount;
    private int cartQuantity;
    private int remainingQuantity;

    public Product() {
    }

    @Override
    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public Product setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public Product setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
        return this;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public Product setQuantityType(String quantityType) {
        this.quantityType = quantityType;
        return this;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public Product setSupplierId(int supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public Product setSupplier(String supplier) {
        this.supplier = supplier;
        return this;
    }
    // cart items getter setter

    public Product setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
        return this;
    }
    public Double getCartSubtotalAmount() {
        return cartSubtotalAmount;
    }

    public Product setCartSubtotalAmount(Double cartSubtotalAmount) {
        this.cartSubtotalAmount = cartSubtotalAmount;
        return this;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public Product setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
        return this;
    }
}

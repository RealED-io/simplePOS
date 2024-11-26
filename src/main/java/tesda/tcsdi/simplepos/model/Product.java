package tesda.tcsdi.simplepos.model;

public class Product implements Model {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private String category;
    private int quantity;
    private String quantityType;
    private int supplierId;
    private String supplier;
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

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
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

    // TODO: implement getting supplier name to ProductDB
    public String getSupplier() {
        return supplier;
    }

    public Product setSupplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public Product save() {
        return null;
    }

    @Override
    public Product delete() {
        return null;
    }

    @Override
    public Product create() {
        return null;
    }

    @Override
    public Product read() {
        return null;
    }
}

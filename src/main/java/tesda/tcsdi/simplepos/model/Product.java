package tesda.tcsdi.simplepos.model;

import tesda.tcsdi.simplepos.model.dal.ProductDB;

public class Product {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private String category;
    private int quantity;
    private int supplier;

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

    public int getSupplier() {
        return supplier;
    }

    public Product setSupplier(int supplier) {
        this.supplier = supplier;
        return this;
    }
}

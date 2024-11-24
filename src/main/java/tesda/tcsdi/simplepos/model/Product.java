package tesda.tcsdi.simplepos.model;

public class Product {
    private int ID;
    private String name;
    private String barcode;
    private double suggestedPrice;
    private String category;
    private int inventoryID;

    public int getID() {
        return ID;
    }

    public Product setID(int ID) {
        this.ID = ID;
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

    public double getSuggestedPrice() {
        return suggestedPrice;
    }

    public Product setSuggestedPrice(double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public Product setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
        return this;
    }

    public Product update() {
        // TODO: updates DB
        return this;
    }

}

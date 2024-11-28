package tesda.tcsdi.simplepos.model;


public class Cart {
    private int productId;

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

    private String name;
    private int quantity;
    private double amount;

    public Cart(int productId, String name, int quantity, double amount) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

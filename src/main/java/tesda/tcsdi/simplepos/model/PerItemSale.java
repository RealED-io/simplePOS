package tesda.tcsdi.simplepos.model;

public class PerItemSale {
    private int id;
    private int invoiceId;
    private int productId;
    private String product;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice;

    public int getId() {
        return id;
    }

    public PerItemSale setId(int id) {
        this.id = id;
        return this;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public PerItemSale setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public PerItemSale setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public PerItemSale setProduct(String product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public PerItemSale setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public PerItemSale setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public PerItemSale setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}

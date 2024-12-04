package tesda.tcsdi.simplepos.model;

public class Sales {
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

    public Sales setId(int id) {
        this.id = id;
        return this;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public Sales setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public Sales setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public Sales setProduct(String product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Sales setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Sales setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Sales setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}

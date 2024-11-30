package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDB extends DatabaseUtil {

    private final String PRODUCT_CATEGORY_TABLE = "products LEFT JOIN categories ON products.category_id=categories.id";
    /**
     * Does not check the validity of ResulSet rs
     * @param rs
     * @return
     */
    public Product resultSetToProduct(ResultSet rs) {
        try {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setBarcode(rs.getString("barcode"));
            product.setPrice(rs.getDouble("price"));
            product.setInventoryQuantity(rs.getInt("quantity"));
            product.setQuantityType(rs.getString("quantity_type"));
            product.setCategoryId(rs.getInt("category_id"));
            product.setCategory(rs.getString("categories.name"));
            product.setSupplierId(rs.getInt("supplier_id"));
            if (product.getSupplierId() != 0) {
                SupplierDB supplier = new SupplierDB();
                String supplierName = supplier.getById(product.getSupplierId()).getName();
                product.setSupplier(supplierName);
            } else {
                product.setSupplier(null);
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product getById(int id) {
        String queryStatement = "SELECT * FROM " + PRODUCT_CATEGORY_TABLE + " WHERE products.id = ?";
        Product product = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) product = resultSetToProduct(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return product;
    }

    public Product getByBarcode(String barcode) {
        String queryStatement = "SELECT * FROM " + PRODUCT_CATEGORY_TABLE + " WHERE products.barcode = ?";
        Product product = null;
        try {
            ResultSet rs = query(queryStatement, barcode);
            if (rs.next()) product = resultSetToProduct(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return product;
    }


    // For incomplete barcode search
    public ArrayList<Product> searchByBarcode(String barcode) {
        return search("barcode", barcode, "name", false);
    }

    public ArrayList<Product> searchByName(String name) {
        return search("name", name, "name", false);
    }
    public ArrayList<Product> searchByCategory(String category) {
        return search("category", category, "category", false);
    }

    private ArrayList<Product> search(String col, String searchString, String orderBy, boolean descending) {
        String desc = descending ? "DESC" : "ASC";
        searchString = "%" + searchString + "%";
        String queryStatement = "SELECT * FROM " + PRODUCT_CATEGORY_TABLE + " WHERE "
                + col + " LIKE ? ORDER BY " + orderBy + " " + desc;
        ArrayList<Product> products = null;
        try (ResultSet rs = query(queryStatement, searchString)) {
            if (rs != null) {
                products = new ArrayList<>();
                while (rs.next()) products.add(resultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }

    public ArrayList<Product> getAll() {
        String queryStatement = "SELECT * FROM products LEFT JOIN categories ON products.category_id=categories.id";
        ArrayList<Product> products = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                products = new ArrayList<>();
                while (rs.next()) products.add(resultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }

    public void create(Product product) {
        String queryStatement = "INSERT INTO products " +
                "(name, barcode, price, quantity, quantity_type, category_id, supplier_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        save(queryStatement,
                String.valueOf(product.getName()),
                product.getBarcode(),
                String.valueOf(product.getInventoryQuantity()),
                product.getQuantityType(),
                String.valueOf(product.getCategoryId()),
                String.valueOf(product.getSupplierId())
        );
        closeConnection();
    }


    public void update(Product product) {
        String queryStatement = "UPDATE products " +
                "SET name = ?, " +
                "barcode = ?, " +
                "price = ?, " +
                "quantity = ?, " +
                "quantity_type = ?, " +
                "category_id = ?, " +
                "supplier_id = ? " +
                "WHERE id = ?";
        save(queryStatement,
                String.valueOf(product.getName()),
                product.getBarcode(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getInventoryQuantity()),
                product.getQuantityType(),
                String.valueOf(product.getCategoryId()),
                String.valueOf(product.getSupplierId()),
                String.valueOf(product.getId())
        );
        closeConnection();
    }

    public void delete(Product product) {
        String queryStatement = "DELETE FROM products WHERE id = ?";
        save(queryStatement, String.valueOf(product.getId()));
    }
}

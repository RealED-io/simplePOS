package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDB extends InventoryDB {


    private static ResultSet queryByColumn(String col, String q) {
        String query = "SELECT * FROM products LEFT JOIN categories WHERE ? = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, col);
            statement.setString(2, q);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static ResultSet queryByColumn(String col, int q) {
        return queryByColumn(col, Integer.toString(q));
    }

    private static Product resultSetToProduct(ResultSet rs) {
        try (rs) {
            // Checks if rs is empty
            if (!rs.next()) return null;
            Product product = new Product();
            product.setID(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setBarcode(rs.getString("barcode"));
            product.setSuggestedPrice(rs.getDouble("suggested_price"));
            product.setCategory(rs.getString("category"));
            return product;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Product getByInt(String dbColumn, int some_id) {
        try (ResultSet rs = queryByColumn(dbColumn, some_id)) {
            return resultSetToProduct(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Product getByID(int id) {
        Product product = getByInt("id", id);
        if (product != null) product.setInventoryID(getOldestStock(product.getID()));
        return product;
    }

    public static Product getByBarcode(int barcode) {
        Product product = getByInt("id", barcode);
        if (product != null) product.setInventoryID(getOldestStock(product.getID()));
        return product;
    }

    // can be migrated to another class InventoryDB
    public static Product getByInventoryID(int inventoryID) {
        Product product = getByInt("inventory_id", inventoryID);
        if (product != null) product.setInventoryID(inventoryID);
        return product;
    }

    public static ArrayList<Product> searchByName(String name) {
            String query = "SELECT * FROM products LEFT JOIN categories WHERE name LIKE %?%";
            try (Connection connection = DatabaseUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Product> products = new ArrayList<>();
                    while (rs.next()) {
                        products.add(resultSetToProduct(rs));
                    }
                    return products;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return null;
    }
}

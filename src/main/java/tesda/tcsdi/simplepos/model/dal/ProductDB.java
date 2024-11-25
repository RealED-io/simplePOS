package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDB extends DatabaseUtil{

    private static Product resultSetToProduct(ResultSet rs) {
        try (rs) {
            // Checks if rs is empty
            if (!rs.next()) return null;
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setBarcode(rs.getString("barcode"));
            product.setPrice(rs.getDouble("price"));
            product.setPrice(rs.getInt("quantity"));
            product.setCategory(rs.getString("category"));
            product.setSupplierId(rs.getInt("supplier_id"));
            return product;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Product getByID(int id) {
        String queryStatement = "SELECT * FROM products LEFT JOIN categories WHERE ? = ?";
        try (ResultSet rs = query(queryStatement, "id", Integer.toString(id)) ) {
            if (rs != null) return resultSetToProduct(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Product getByBarcode(String barcode) {
        String queryStatement = "SELECT * FROM products LEFT JOIN categories WHERE ? = ?";
        try (ResultSet rs = query(queryStatement, "barcode", barcode)) {
            if (rs != null) return resultSetToProduct(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Product> searchByName(String name) {
        return searchByName(name, "name");
    }

    public static ArrayList<Product> searchByName(String name, String orderBy) {
        return searchByName(name, orderBy, false);
    }

    public static ArrayList<Product> searchByName(String name, String orderBy, boolean descending) {
        String desc = descending ? "DESC" : "";
        String queryStatement = STR."SELECT * FROM products LEFT JOIN categories WHERE name LIKE %?% ORDER BY ? \{desc}";
        try (ResultSet rs = query(queryStatement, name, orderBy)) {
            ArrayList<Product> products = new ArrayList<>();
            if (rs != null) {
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

    public static Product save(Product product) {
        // TODO: implement save productDB method
        return product;
    }
}

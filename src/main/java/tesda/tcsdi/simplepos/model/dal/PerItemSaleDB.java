package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Invoice;
import tesda.tcsdi.simplepos.model.PerItemSale;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerItemSaleDB extends DatabaseUtil {
    private PerItemSale resultSetToPerItemSale(ResultSet rs) {
        try {
            PerItemSale perItemSale = new PerItemSale();
            perItemSale.setId(rs.getInt("id"));
            perItemSale.setInvoiceId(rs.getInt("invoice_id"));
            perItemSale.setProductId(rs.getInt("product_id"));
            if (perItemSale.getProductId() != 0) {
                ProductDB product = new ProductDB();
                String productName = product.getById(perItemSale.getProductId()).getName();
                perItemSale.setProduct(productName);
            } else {
                perItemSale.setProduct(null);
            }
            perItemSale.setQuantity(rs.getInt("quantity"));
            return perItemSale;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PerItemSale getById(int id) {
        String queryStatement = "SELECT * FROM per_item_sales WHERE id = ?";
        PerItemSale perItemSale = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) perItemSale = resultSetToPerItemSale(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return perItemSale;
    }

    public ArrayList<PerItemSale> getAll() {
        String queryStatement = "SELECT * FROM per_item_sales";
        ArrayList<PerItemSale> perItemSale = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                perItemSale = new ArrayList<>();
                while (rs.next()) perItemSale.add(resultSetToPerItemSale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return perItemSale;
    }

    public ArrayList<PerItemSale> getAllByInvoice(int id) {
        String queryStatement = "SELECT * FROM per_item_sales WHERE invoice_id = ?";
        ArrayList<PerItemSale> perItemSale = null;
        try (ResultSet rs = query(queryStatement, String.valueOf(id))) {
            if (rs != null) {
                perItemSale = new ArrayList<>();
                while (rs.next()) perItemSale.add(resultSetToPerItemSale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return perItemSale;
    }
}

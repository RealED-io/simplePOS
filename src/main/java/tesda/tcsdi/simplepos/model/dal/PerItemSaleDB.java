package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.PerItemSale;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerItemSaleDB extends DatabaseUtil {
    private PerItemSale resultSetToPerItemSale(ResultSet rs) {
        try {
            PerItemSale sale = new PerItemSale();
            try{
                sale.setId(rs.getInt("id"));
            } catch (SQLException ignored){}
            try {
                sale.setInvoiceId(rs.getInt("invoice_id"));
            } catch (SQLException ignored){}
            sale.setProductId(rs.getInt("product_id"));
            if (sale.getProductId() != 0) {
                ProductDB product = new ProductDB();
                String productName = product.getById(sale.getProductId()).getName();
                sale.setProduct(productName);
            } else {
                sale.setProduct(null);
            }
            sale.setQuantity(rs.getInt("quantity"));
            sale.setUnitPrice(rs.getDouble("actual_unit_price"));
            sale.setTotalPrice(rs.getDouble("actual_total_price"));
            return sale;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PerItemSale getById(int id) {
        String queryStatement = "SELECT * FROM per_item_sales WHERE id = ?";
        PerItemSale sale = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) sale = resultSetToPerItemSale(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sale;
    }

    public ArrayList<PerItemSale> getAll() {
        String queryStatement = "SELECT * FROM per_item_sales";
        ArrayList<PerItemSale> sales = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                sales = new ArrayList<>();
                while (rs.next()) sales.add(resultSetToPerItemSale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sales;
    }

    public ArrayList<PerItemSale> searchByInvoice(int id) {
        String queryStatement = "SELECT * FROM per_item_sales WHERE invoice_id = ?";
        ArrayList<PerItemSale> sales = null;
        try (ResultSet rs = query(queryStatement, String.valueOf(id))) {
            if (rs != null) {
                sales = new ArrayList<>();
                while (rs.next()) sales.add(resultSetToPerItemSale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sales;
    }

    public ArrayList<PerItemSale> getSalesReport() {
        String queryStatement = "SELECT product_id, " +
                "SUM(quantity) AS quantity, " +
                "AVG(actual_unit_price) AS actual_unit_price, " +
                "SUM(actual_total_price) AS actual_total_price " +
                "FROM simplepos.per_item_sales GROUP BY product_id";
        ArrayList<PerItemSale> sales = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                sales = new ArrayList<>();
                while (rs.next()) sales.add(resultSetToPerItemSale(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sales;
    }

    public PerItemSale create(PerItemSale sale) {
        String queryStatement = "INSERT INTO per_item_sales " +
                "(invoice_id, product_id, quantity, actual_unit_price) " +
                "VALUES (?, ?, ?, ?)";
        int id = save(queryStatement,
                String.valueOf(sale.getInvoiceId()),
                String.valueOf(sale.getProductId()),
                String.valueOf(sale.getQuantity()),
                String.valueOf(sale.getUnitPrice())
        );
        closeConnection();
        sale.setId(id);
        return sale;
    }

    public void delete(PerItemSale sale) {
        String queryStatement = "DELETE FROM per_item_sales WHERE id = ?";
        save(queryStatement, String.valueOf(sale.getId()) );
    }
}

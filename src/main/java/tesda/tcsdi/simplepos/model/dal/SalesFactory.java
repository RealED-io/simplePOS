package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesFactory extends DatabaseUtil {
    private Sales resultSetToSales(ResultSet rs) {
        try {
            Sales sale = new Sales();
            try{
                sale.setId(rs.getInt("id"));
            } catch (SQLException ignored){}
            try {
                sale.setInvoiceId(rs.getInt("invoice_id"));
            } catch (SQLException ignored){}
            sale.setProductId(rs.getInt("product_id"));
            if (sale.getProductId() != 0) {
                ProductFactory product = new ProductFactory();
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

    public Sales getById(int id) {
        String queryStatement = "SELECT * FROM per_item_sales WHERE id = ?";
        Sales sale = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) sale = resultSetToSales(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sale;
    }

    public ArrayList<Sales> getAll() {
        String queryStatement = "SELECT * FROM per_item_sales";
        ArrayList<Sales> sales = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                sales = new ArrayList<>();
                while (rs.next()) sales.add(resultSetToSales(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sales;
    }

    public ArrayList<Sales> searchByInvoice(int id) {
        String queryStatement = "SELECT * FROM per_item_sales WHERE invoice_id = ?";
        ArrayList<Sales> sales = null;
        try (ResultSet rs = query(queryStatement, String.valueOf(id))) {
            if (rs != null) {
                sales = new ArrayList<>();
                while (rs.next()) sales.add(resultSetToSales(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sales;
    }

    public ArrayList<Sales> getSalesReport() {
        String queryStatement = "SELECT product_id, " +
                "SUM(quantity) AS quantity, " +
                "AVG(actual_unit_price) AS actual_unit_price, " +
                "SUM(actual_total_price) AS actual_total_price " +
                "FROM simplepos.per_item_sales GROUP BY product_id";
        ArrayList<Sales> sales = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                sales = new ArrayList<>();
                while (rs.next()) sales.add(resultSetToSales(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return sales;
    }

    public Sales create(Sales sale) {
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

    public void delete(Sales sale) {
        String queryStatement = "DELETE FROM per_item_sales WHERE id = ?";
        save(queryStatement, String.valueOf(sale.getId()) );
    }
}

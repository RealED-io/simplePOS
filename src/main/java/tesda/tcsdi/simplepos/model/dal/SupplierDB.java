package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Product;
import tesda.tcsdi.simplepos.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDB extends DatabaseUtil{

    private Supplier resultSetToSupplier(ResultSet rs) {
        try {
            Supplier supplier = new Supplier();
            supplier.setId(rs.getInt("id"));
            supplier.setName(rs.getString("name"));
            supplier.setPhoneNumber(rs.getString("phone_number"));
            supplier.setEmail(rs.getString("email"));
            supplier.setAddress(rs.getString("address"));
            return supplier;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Supplier getById(int id) {
        String queryStatement = "SELECT * FROM suppliers WHERE id = ?";
        Supplier supplier = null;
        try (ResultSet rs = query(queryStatement, Integer.toString(id)) ) {
            if (rs.next()) supplier = resultSetToSupplier(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    public ArrayList<Supplier> searchByEmail(String email) {
        return search("email", email, "email", false);
    }

    public ArrayList<Supplier> searchByPhoneNumber(String phoneNumber) {
        return search("phone_number", phoneNumber, "name", false);
    }

    public ArrayList<Supplier> searchByName(String name) {
        return search("name", name, "name", false);
    }

    public ArrayList<Supplier> searchByName(String name, String orderBy) {
        return search("name", name, orderBy, false);
    }

    public ArrayList<Supplier> searchByName(String name, String orderBy, boolean descending) {
        return search("name", name, orderBy, descending);
    }

    private ArrayList<Supplier> search(String col, String searchString, String orderBy, boolean descending) {
        String desc = descending ? "DESC" : "ASC";
        searchString = "%" + searchString + "%";
        String queryStatement = "SELECT * FROM suppliers WHERE " + col + " LIKE ? ORDER BY" + orderBy + " " + desc;
        ArrayList<Supplier> suppliers = null;
        try (ResultSet rs = query(queryStatement, searchString)) {
            suppliers = new ArrayList<>();
            if (rs != null) {
                while (rs.next()) suppliers.add(resultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public ArrayList<Supplier> getAll() {
        String queryStatement = "SELECT * FROM suppliers";
        ArrayList<Supplier> suppliers = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                suppliers = new ArrayList<>();
                while (rs.next()) suppliers.add(resultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return suppliers;
    }

    public Supplier save(Supplier supplier) {
        // TODO: implement save productDB method
        return supplier;
    }
}

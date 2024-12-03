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

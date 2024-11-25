package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDB extends DatabaseUtil{

    private static Supplier resultSetToSupplier(ResultSet rs) {
        try (rs) {
            // Checks if rs is empty
            if (!rs.next()) return null;
            Supplier supplier = new Supplier();
            supplier.setId(rs.getInt("id"));
            supplier.setName(rs.getString("name"));
            supplier.setPhoneNumber(rs.getString("phone_number"));
            supplier.setEmail(rs.getString("email"));
            supplier.setAddress(rs.getString("address"));
            return supplier;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Supplier getById(int id) {
        String queryStatement = "SELECT * FROM suppliers WHERE ? = ?";
        try (ResultSet rs = query(queryStatement, "id", Integer.toString(id)) ) {
            if (rs != null) return resultSetToSupplier(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Supplier> searchByEmail(String email) {
        return search("email", email, "email", false);
    }

    public static ArrayList<Supplier> searchByPhoneNumber(String phoneNumber) {
        return search("phone_number", phoneNumber, "name", false);
    }

    public static ArrayList<Supplier> searchByName(String name) {
        return search("name", name, "name", false);
    }

    public static ArrayList<Supplier> searchByName(String name, String orderBy) {
        return search("name", name, orderBy, false);
    }

    public static ArrayList<Supplier> searchByName(String name, String orderBy, boolean descending) {
        return search("name", name, orderBy, descending);
    }

    private static ArrayList<Supplier> search(String col, String searchString, String orderBy, boolean descending) {
        String desc = descending ? "DESC" : "";
        String queryStatement = STR."SELECT * FROM suppliers WHERE \{col} LIKE %?% ORDER BY ? \{desc}";
        try (ResultSet rs = query(queryStatement, searchString, orderBy)) {
            ArrayList<Supplier> suppliers = new ArrayList<>();
            if (rs != null) {
                while (rs.next()) {
                    suppliers.add(resultSetToSupplier(rs));
                }
                return suppliers;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Supplier save(Supplier supplier) {
        // TODO: implement save productDB method
        return supplier;
    }
}

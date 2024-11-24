package tesda.tcsdi.simplepos.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDB {
    protected static final Connection connection = DatabaseUtil.getConnection();

    protected static int getOldestStock(int product_id) {
        String query = "SELECT id FROM inventory WHERE product_id = ? AND current_quantity > 0 ORDER BY exp_date";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, product_id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // save changes made in Product
    // can also have another that save changes in inventory like updateStock() or checkout()
    // if current stock turns to 0 give another inventory_id to Product
    public static void save() {

    }


}

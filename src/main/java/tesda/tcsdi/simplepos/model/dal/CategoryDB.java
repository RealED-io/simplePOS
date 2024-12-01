package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDB extends DatabaseUtil{
    private Category resultSetToCategory(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Category> getAll() {
        String queryStatement = "SELECT * FROM categories";
        ArrayList<Category> categories = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                categories = new ArrayList<>();
                while (rs.next()) categories.add(resultSetToCategory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categories;
    }
}

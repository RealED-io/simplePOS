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

    public Category getById(int id) {
        String queryStatement = "SELECT * FROM categories WHERE id = ?";
        Category category = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) category = resultSetToCategory(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return category;
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

    public Category create(Category category) {
        String queryStatement = "INSERT INTO categories " +
                "(name) " +
                "VALUES (?)";
        int id = save(queryStatement, category.getName());
        category.setId(id);
        closeConnection();
        return category;
    }

    public void update(Category category) {
        String queryStatement = "UPDATE categories SET name = ? WHERE id = ?";
        save(queryStatement, category.getName(), String.valueOf(category.getId()));
        closeConnection();
    }

    public void delete(Category category) {
        String queryStatement = "DELETE FROM categories WHERE id = ?";
        save(queryStatement, String.valueOf(category.getId()));
    }
}

package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDB extends DatabaseUtil{

    private static Employee resultSetToEmployee(ResultSet rs) {
        try (rs) {
            // Checks if rs is empty
            if (!rs.next()) return null;
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
            employee.setRole(rs.getString("role"));
            return employee;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Employee getById(int id) {
        String queryStatement = "SELECT * FROM employees WHERE ? = ?";
        try (ResultSet rs = query(queryStatement, "id", Integer.toString(id)) ) {
            if (rs != null) return resultSetToEmployee(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Employee> searchByName(String name) {
        return search("employee",  name, "name", false);
    }

    private static ArrayList<Employee> search(String col, String searchString, String orderBy, boolean descending) {
        String desc = descending ? "DESC" : "";
        searchString = STR."%\{searchString}%";
        String queryStatement = STR."SELECT * FROM employees WHERE ? LIKE ? ORDER BY ? \{desc}";
        try (ResultSet rs = query(queryStatement, col, searchString, orderBy)) {
            ArrayList<Employee> employees = new ArrayList<>();
            if (rs != null) {
                while (rs.next()) {
                    employees.add(resultSetToEmployee(rs));
                }
                return employees;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Employee save(Employee employee) {
        // TODO: implement save productDB method
        return employee;
    }
}

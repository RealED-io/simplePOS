package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Employee;
import tesda.tcsdi.simplepos.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDB extends DatabaseUtil{

    private Employee resultSetToEmployee(ResultSet rs) {
        try {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
            employee.setRole(rs.getString("role"));
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getById(int id) {
        String queryStatement = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) employee = resultSetToEmployee(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return employee;
    }

    public ArrayList<Employee> searchByName(String name) {
        return search("employee",  name, "name", false);
    }

    private ArrayList<Employee> search(String col, String searchString, String orderBy, boolean descending) {
        String desc = descending ? "DESC" : "ASC";
        searchString = "%" + searchString + "%";
        String queryStatement = "SELECT * FROM employees WHERE " + col + " LIKE ? ORDER BY " + orderBy + " " + desc;
        ArrayList<Employee> employees = null;
        try (ResultSet rs = query(queryStatement, orderBy)) {
            if (rs != null) {
                employees = new ArrayList<>();
                while (rs.next()) employees.add(resultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public ArrayList<Employee> getAll() {
        String queryStatement = "SELECT * FROM employees";
        ArrayList<Employee> employees = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                employees = new ArrayList<>();
                while (rs.next()) employees.add(resultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return employees;
    }

    public Employee save(Employee employee) {
        // TODO: implement save productDB method
        return employee;
    }
}

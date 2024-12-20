package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Employee;
import tesda.tcsdi.simplepos.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFactory extends DatabaseUtil{

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

    public Employee create(Employee employee) {
        String queryStatement = "INSERT INTO employees " +
                "(name, username, password, role) " +
                "VALUES (?, ?, ?, ?)";
        int id = save(queryStatement,
                employee.getName(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getRole()
        );
        employee.setId(id);
        closeConnection();
        return employee;
    }


    public void update(Employee employee) {
        String queryStatement = "UPDATE employees " +
                "SET name = ?, " +
                "username = ?, " +
                "password = ?, " +
                "role = ? " +
                "WHERE id = ?";
        save(queryStatement,
                employee.getName(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getRole(),
                String.valueOf(employee.getId())
        );
        closeConnection();
    }

    public void delete(Employee employee) {
        String queryStatement = "DELETE FROM employees WHERE id = ?";
        save(queryStatement, String.valueOf(employee.getId()));
    }
}

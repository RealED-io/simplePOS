package tesda.tcsdi.simplepos.model.dal;

import tesda.tcsdi.simplepos.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDB extends DatabaseUtil {

    private Invoice resultSetToInvoice(ResultSet rs) {
        try {
            Invoice invoice = new Invoice();
            invoice.setId(rs.getInt("id"));
            invoice.setEmployeeId(rs.getInt("employee_id"));
            if (invoice.getEmployeeId() != 0) {
                EmployeeDB employee = new EmployeeDB();
                String employeeName = employee.getById(invoice.getEmployeeId()).getName();
                invoice.setEmployee(employeeName);
            } else {
                invoice.setEmployee(null);
            }
            invoice.setTotalAmount(rs.getDouble("total_amount"));
            invoice.setIssueDate(rs.getTimestamp("issue_date"));
            return invoice;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Invoice getById(int id) {
        String queryStatement = "SELECT * FROM invoices WHERE id = ?";
        Invoice invoice = null;
        try {
            ResultSet rs = query(queryStatement, String.valueOf(id));
            if (rs.next()) invoice = resultSetToInvoice(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return invoice;
    }

    public ArrayList<Invoice> getAll() {
        String queryStatement = "SELECT * FROM invoices";
        ArrayList<Invoice> invoices = null;
        try (ResultSet rs = query(queryStatement)) {
            if (rs != null) {
                invoices = new ArrayList<>();
                while (rs.next()) invoices.add(resultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return invoices;
    }

    public Invoice create(Invoice invoice) {
        String queryStatement = "INSERT INTO invoices " +
                "(employee_id, total_amount) " +
                "VALUES (?, ?)";
        int id = save(queryStatement,
                String.valueOf(invoice.getEmployeeId()),
                String.valueOf(invoice.getTotalAmount())
        );
        closeConnection();
        invoice = getById(id);
        return invoice;
    }

    public void delete(Invoice invoice) {
        String queryStatement = "DELETE FROM invoice WHERE id = ?";
        save(queryStatement, String.valueOf(invoice.getId()));
    }
}

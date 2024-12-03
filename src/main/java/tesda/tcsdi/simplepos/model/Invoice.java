package tesda.tcsdi.simplepos.model;

import java.sql.Timestamp;

public class Invoice {
    private int id;
    private int employeeId;
    private String employee;
    private Double totalAmount;
    private Timestamp issueDate;

    public int getId() {
        return id;
    }

    public Invoice setId(int id) {
        this.id = id;
        return this;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Invoice setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getEmployee() {
        return employee;
    }

    public Invoice setEmployee(String employee) {
        this.employee = employee;
        return this;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Invoice setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public Invoice setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
        return this;
    }
}


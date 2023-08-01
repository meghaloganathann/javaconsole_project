package mini2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;
    private int customerId;
    private Date billDate;
    private double totalAmount;
    private String paymentMethod;

    // Constructors, getters, and setters

    public Bill() {}

    public Bill(int customerId, Date billDate, double totalAmount, String paymentMethod) {
        this.customerId = customerId;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Method to insert a new bill into the database
    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root", "1262709123")) {
            String insertQuery = "INSERT INTO Bills (customer_id, bill_date, total_amount, payment_method) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, new java.sql.Date(billDate.getTime()));
            preparedStatement.setDouble(3, totalAmount);
            preparedStatement.setString(4, paymentMethod);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                billId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch a bill from the database by bill ID
    public static Bill getBillById(int billId) {
        Bill bill = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String selectQuery = "SELECT * FROM Bills WHERE bill_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, billId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bill = new Bill();
                bill.setBillId(resultSet.getInt("bill_id"));
                bill.setCustomerId(resultSet.getInt("customer_id"));
                bill.setBillDate(resultSet.getDate("bill_date"));
                bill.setTotalAmount(resultSet.getDouble("total_amount"));
                bill.setPaymentMethod(resultSet.getString("payment_method"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    // Method to update a bill in the database
    public void updateBill() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String updateQuery = "UPDATE Bills SET customer_id = ?, bill_date = ?, total_amount = ?, payment_method = ? WHERE bill_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, new java.sql.Date(billDate.getTime()));
            preparedStatement.setDouble(3, totalAmount);
            preparedStatement.setString(4, paymentMethod);
            preparedStatement.setInt(5, billId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a bill from the database
    public void deleteBill() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String deleteQuery = "DELETE FROM Bills WHERE bill_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, billId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all bills from the database
    public static List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String selectQuery = "SELECT * FROM Bills";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setBillId(resultSet.getInt("bill_id"));
                bill.setCustomerId(resultSet.getInt("customer_id"));
                bill.setBillDate(resultSet.getDate("bill_date"));
                bill.setTotalAmount(resultSet.getDouble("total_amount"));
                bill.setPaymentMethod(resultSet.getString("payment_method"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    // Other methods as needed
    // ...
}


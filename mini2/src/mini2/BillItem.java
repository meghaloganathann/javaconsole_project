package mini2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillItem {
    private int billItemId;
    private int billId;
    private int productId;
    private int quantity;
    private double subtotal;

    // Constructors, getters, and setters

    public BillItem() {}

    public BillItem(int billId, int productId, int quantity, double subtotal) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getBillItemId() {
        return billItemId;
    }

    public void setBillItemId(int billItemId) {
        this.billItemId = billItemId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Method to insert a new bill item into the database
    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String insertQuery = "INSERT INTO Bill_Items (bill_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, billId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, subtotal);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                billItemId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch a bill item from the database by bill item ID
    public static BillItem getBillItemById(int billItemId) {
        BillItem billItem = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String selectQuery = "SELECT * FROM Bill_Items WHERE bill_item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, billItemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                billItem = new BillItem();
                billItem.setBillItemId(resultSet.getInt("bill_item_id"));
                billItem.setBillId(resultSet.getInt("bill_id"));
                billItem.setProductId(resultSet.getInt("product_id"));
                billItem.setQuantity(resultSet.getInt("quantity"));
                billItem.setSubtotal(resultSet.getDouble("subtotal"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItem;
    }

    // Method to update a bill item in the database
    public void updateBillItem() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String updateQuery = "UPDATE Bill_Items SET bill_id = ?, product_id = ?, quantity = ?, subtotal = ? WHERE bill_item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, billId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, subtotal);
            preparedStatement.setInt(5, billItemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a bill item from the database
    public void deleteBillItem() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String deleteQuery = "DELETE FROM Bill_Items WHERE bill_item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, billItemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all bill items from the database for a given bill ID
    public static List<BillItem> getAllBillItemsForBill(int billId) {
        List<BillItem> billItems = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String selectQuery = "SELECT * FROM Bill_Items WHERE bill_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, billId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BillItem billItem = new BillItem();
                billItem.setBillItemId(resultSet.getInt("bill_item_id"));
                billItem.setBillId(resultSet.getInt("bill_id"));
                billItem.setProductId(resultSet.getInt("product_id"));
                billItem.setQuantity(resultSet.getInt("quantity"));
                billItem.setSubtotal(resultSet.getDouble("subtotal"));
                billItems.add(billItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItems;
    }

    // Other methods as needed
    // ...
}

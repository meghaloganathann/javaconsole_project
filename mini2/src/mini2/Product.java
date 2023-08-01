package mini2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int stockQuantity;

    // Constructors, getters, and setters

    public Product() {}

    public Product(String productName, double price, int stockQuantity) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // Method to insert a new product into the database
    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String insertQuery = "INSERT INTO Products (product_name, price, stock_quantity) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, stockQuantity);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch a product from the database by product ID
    public static Product getProductById(int productId) {
        Product product = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String selectQuery = "SELECT * FROM Products WHERE product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStockQuantity(resultSet.getInt("stock_quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Method to update a product in the database
    public void updateProduct() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String updateQuery = "UPDATE Products SET product_name = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, stockQuantity);
            preparedStatement.setInt(4, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a product from the database
    public void deleteProduct() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String deleteQuery = "DELETE FROM Products WHERE product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all products from the database
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "1262709123")) {
            String selectQuery = "SELECT * FROM Products";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStockQuantity(resultSet.getInt("stock_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Other methods as needed
    // ...
}

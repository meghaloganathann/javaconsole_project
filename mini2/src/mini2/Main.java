package mini2;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addBillWithItems(scanner);
                    break;
                case 2:
                    displayAllBills();
                    break;
                case 3:
                    displayAllProducts();
                    break;
                case 4:
                    displayAllCustomers();
                    break;
                case 5:
                    System.out.println("Exiting the program...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n========== Grocery Store Management ==========");
        System.out.println("1. Add Bill with Items");
        System.out.println("2. Display All Bills");
        System.out.println("3. Display All Products");
        System.out.println("4. Display All Customers");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addBillWithItems(Scanner scanner) {
        System.out.println("===== Adding a Bill with Items =====");

        // Get customer details
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter customer phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String address = scanner.nextLine();

        // Create the customer object and save it to the database
        Customer customer = new Customer(customerName, email, phone, address);
        customer.saveToDatabase();

        // Get bill details
        System.out.print("Enter bill date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        Date billDate = java.sql.Date.valueOf(dateStr);
        System.out.print("Enter total amount: ");
        double totalAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter payment method: ");
        String paymentMethod = scanner.nextLine();

        // Create the bill object and save it to the database
        Bill bill = new Bill(customer.getCustomerId(), billDate, totalAmount, paymentMethod);
        bill.saveToDatabase();

        while (true) {
            System.out.print("Enter product ID (0 to finish adding items): ");
            int productId = scanner.nextInt();

            if (productId == 0) {
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            Product product = Product.getProductById(productId);
            if (product == null) {
                System.out.println("Product with ID " + productId + " not found. Please try again.");
            } else {
                double subtotal = product.getPrice() * quantity;
                BillItem billItem = new BillItem(bill.getBillId(), productId, quantity, subtotal);
                billItem.saveToDatabase();
            }
            scanner.nextLine(); // Consume the newline character
        }

        System.out.println("Bill with items added successfully!");
    }

    private static void displayAllBills() {
        List<Bill> allBills = Bill.getAllBills();
        System.out.println("\n========== All Bills ==========");
        for (Bill bill : allBills) {
            System.out.println(bill.toString());
        }
    }

    private static void displayAllProducts() {
        List<Product> allProducts = Product.getAllProducts();
        System.out.println("\n========== All Products ==========");
        for (Product product : allProducts) {
            System.out.println(product.toString());
        }
    }

    private static void displayAllCustomers() {
        List<Customer> allCustomers = Customer.getAllCustomers();
        System.out.println("\n========== All Customers ==========");
        for (Customer customer : allCustomers) {
            System.out.println(customer.toString());
        }
    }
}

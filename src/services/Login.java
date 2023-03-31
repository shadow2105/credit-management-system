package services;

import model.Admin;
import model.Customer;

import java.sql.*;
import java.util.Scanner;

public class Login {

    private static String loggedInAdmin;
    private static String loggedInCustomer;

    public static void adminLogin() {
        Scanner credentials = new Scanner(System.in);

        System.out.print("\nEnter Admin ID: ");
        String userId = credentials.nextLine();

        System.out.print("Enter Password: ");
        String password = credentials.nextLine();

        try {
            Connection connection = Database.connectDatabase();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM admins WHERE user_id = '" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String firstNameOutput = resultSet.getString("first_name");
                String lastNameOutput = resultSet.getString("last_name");
                String userIdOutput = resultSet.getString("user_id");
                String passwordOutput = resultSet.getString("passwd");

                Admin admin = new Admin(firstNameOutput, lastNameOutput, userIdOutput, passwordOutput);

                if (admin.getUserId().equals(userId) && admin.getPassword().equals(password)) {
                    System.out.println("\nWelcome back, " + admin.getFirstName() + " " + admin.getLastName() + "!");
                    do {
                        loggedInAdmin = admin.getUserId();
                        Menu.adminMenu(loggedInAdmin);
                    } while (!Menu.isAdminExit());
                } else {
                    System.out.println("\nInvalid Credentials. Try Again!");
                    adminLogin();
                }
            }
            else {
                System.out.println("\nAdmin not found!");
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void customerLogin() {
        Scanner credentials = new Scanner(System.in);

        System.out.print("\nEnter Customer ID: ");
        String userId = credentials.nextLine();

        System.out.print("Enter Password: ");
        String password = credentials.nextLine();

        try {
            Connection connection = Database.connectDatabase();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM customers WHERE user_id = '" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String firstNameOutput = resultSet.getString("first_name");
                String lastNameOutput = resultSet.getString("last_name");
                String userIdOutput = resultSet.getString("user_id");
                String passwordOutput = resultSet.getString("passwd");

                Customer customer = new Customer(firstNameOutput, lastNameOutput, userIdOutput, passwordOutput);

                if (customer.getUserId().equals(userId) && customer.getPassword().equals(password)) {
                    System.out.println("\nWelcome back, " + customer.getFirstName() + " " + customer.getLastName() + "!");
                    do {
                        loggedInCustomer = customer.getUserId();
                        Menu.customerMenu(loggedInCustomer);
                    } while (!Menu.isCustomerExit());
                } else {
                    System.out.println("\nInvalid Credentials. Try Again!");
                    customerLogin();
                }
            }
            else {
                System.out.println("\nCustomer not found!\nRegister or Sign Up using option 3 of the Main Menu.");
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void registerNewAccount() {
        System.out.println("\n----------------------- Customer Registration Form -----------------------\n");
        System.out.println("Mandatory Fields are marked with (*)");
        System.out.println("Press -1 to discard changes and go back to the Main Menu.");

        Scanner userData = new Scanner(System.in);

        System.out.print("\nEnter First Name (*): ");
        String firstName = Utilities.toTitleCase(userData.nextLine().trim());

        System.out.print("Enter Last Name (*): ");
        String lastName = Utilities.toTitleCase(userData.nextLine().trim());

        System.out.print("Enter Home Address (*): ");
        String address = userData.nextLine().trim().toUpperCase();

        System.out.print("Enter Mobile Number (*): ");
        String mobileNum = userData.nextLine().trim();

        System.out.print("Enter Email Address (*): ");
        String email = userData.nextLine().toLowerCase().trim();

        System.out.print("Enter Occupation (*): ");
        String occupation = Utilities.toTitleCase(userData.nextLine().trim());

        System.out.println("\n** Maximum 20 characters allowed for Customer ID and Password each (No spaces allowed) **");
        System.out.print("Enter Customer ID (*): ");
        String userId = userData.nextLine().trim();

        System.out.print("Enter Password (*): ");
        String password = userData.nextLine().trim();

        System.out.println("\n--------------------------------------------------------------------------\n");

        if (firstName.equals("-1") || lastName.equals("-1") || address.equals("-1") || mobileNum.equals("-1") || email.equals("-1") || userId.equals("-1") || password.equals("-1") || occupation.equals("-1")) {
            System.out.println("Changes discarded!\nNew Customer Registration unsuccessful.");
        }
        else if (firstName.equals("") || lastName.equals("") || address.equals("") || mobileNum.equals("") || email.equals("") || userId.equals("") || password.equals("") || occupation.equals("")) {
            System.out.println("\nFields marked with (*) cannot be left blank. Try Again!");
            registerNewAccount();
        }
        else {
            try {
                Connection connection = Database.connectDatabase();
                Statement statement = connection.createStatement();
                String query = "INSERT INTO " +
                        "customers (user_id, first_name, last_name, passwd, address, mobile_no, email, occupation) " +
                        "VALUES ('" + userId + "', '" + firstName + "', '" + lastName +  "', '" + password + "', '" +
                        address + "', '" + mobileNum + "', '" + email + "', '" + occupation + "' )";
                statement.executeUpdate(query);

                Customer customer = new Customer(firstName, lastName, userId, password, address, mobileNum, email, occupation);

                System.out.println("Customer ID: " + customer.getUserId() + " registered successfully.");
                System.out.println("Welcome to 'Credit Management System' " + customer.getFirstName() + " " + customer.getLastName() + "!");

                connection.close();
            }
            catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("\nCustomer with similar details already exists. Try Again!");
                System.out.println("Verify your details, try another Customer ID or Sign In using option 2 of the Main Menu.");
                registerNewAccount();
            }
            catch (SQLException e) {
                System.out.println(e);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void changePassword() {
        Scanner credential = new Scanner(System.in);

        System.out.print("\nEnter Email Address: ");
        String email = credential.nextLine().trim().toLowerCase();

        try {
            Connection connection = Database.connectDatabase();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM customers WHERE email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String firstNameOutput = resultSet.getString("first_name");
                String lastNameOutput = resultSet.getString("last_name");
                String userIdOutput = resultSet.getString("user_id");
                String passwordOutput = resultSet.getString("passwd");
                String emailOutput = resultSet.getString("email");

                Customer customer = new Customer(firstNameOutput, lastNameOutput, userIdOutput, passwordOutput);
                customer.setEmail(emailOutput);

                System.out.println("\nHello, " + customer.getFirstName() + " " + customer.getLastName() + "!");
                System.out.println("An Email to reset your Account password has been sent to your Email Address: " + customer.getEmail());

                System.out.println("\n............... Customer clicks the password reset link received in the Email - ");
                System.out.println("Press -1 to discard changes and go back to the Main Menu.");

                label:
                while(true) {
                    System.out.print("\nEnter New Password: ");
                    String newPassword = credential.nextLine().trim();

                    System.out.print("Re-enter New Password: ");
                    String newPasswordReEnter = credential.nextLine().trim();

                    if (newPassword.equals("-1") || newPasswordReEnter.equals("-1")) {
                        System.out.println("\nChanges discarded!\nPassword change unsuccessful.");
                        break label;
                    }
                    else if(newPassword == "") {
                        System.out.println("\nNew Password cannot be blank. Try Again!");
                    }
                    else if (newPasswordReEnter.equals(newPassword)) {
                        query = "UPDATE customers "
                                + "SET passwd = '" + newPassword + "' "
                                + "WHERE user_id = '" + customer.getUserId() + "'";
                        statement.executeUpdate(query);
                        System.out.println("\nPassword changed successfully for Customer ID: " + customer.getUserId());
                        break label;
                    } else {
                        System.out.println("\nPassword Mismatch. Try Again!");
                    }
                }
            }
            else {
                System.out.println("\n" + "Email: '" + email + "' is not associated with any Customer Account.\nVerify your Email Address or Register or Sign Up using option 3 of the Main Menu.");
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getLoggedInAdmin() {
        return loggedInAdmin;
    }
    public static String getLoggedInCustomer() {
        return loggedInCustomer;
    }
}

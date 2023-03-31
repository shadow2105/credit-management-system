package services;

import model.Admin;
import model.Customer;

import java.sql.*;
import java.util.Scanner;

public class AdminServices {

    public static void editAdminProfile() {
        System.out.println("\n----------------------- Admin Profile -----------------------");
        String userId = Login.getLoggedInAdmin();

        try {
            Connection connection = Database.connectDatabase();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM admins WHERE user_id = '" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            String firstNameOutput = resultSet.getString("first_name");
            String lastNameOutput = resultSet.getString("last_name");
            String userIdOutput = resultSet.getString("user_id");
            String passwordOutput = resultSet.getString("passwd");

            Admin admin = new Admin(firstNameOutput, lastNameOutput, userIdOutput, passwordOutput);

            System.out.println("Press q to go back to the Dashboard.");
            System.out.println("\n[ " + admin.getFirstName() + " " + admin.getLastName() + " ]" +
                    "\n1. Password: *************");
            System.out.print("\n** Field option number: ");

            Scanner option = new Scanner(System.in);
            String optionNum = option.nextLine();

            if (optionNum.trim().equalsIgnoreCase("q")) { Menu.adminMenu(userId); }
            else if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= 1)) {
                switch (optionNum) {
                    case "1" -> {
                        Scanner credential = new Scanner(System.in);
                        System.out.println("Press -1 to discard changes.");

                        label:
                        while (true) {
                            System.out.print("\nEnter New Password: ");
                            String newPassword = credential.nextLine().trim();

                            System.out.print("Re-enter New Password: ");
                            String newPasswordReEnter = credential.nextLine().trim();

                            if (newPassword.equals("-1") || newPasswordReEnter.equals("-1")) {
                                System.out.println("\nChanges discarded!\nPassword change unsuccessful.");
                                break label;
                            } else if (newPassword.equals("")) {
                                System.out.println("\nNew Password cannot be blank. Try Again!");
                            } else if (newPasswordReEnter.equals(newPassword)) {
                                query = "UPDATE admins "
                                        + "SET passwd = '" + newPassword + "' "
                                        + "WHERE user_id = '" + admin.getUserId() + "'";
                                statement.executeUpdate(query);
                                System.out.println("\nPassword changed successfully for Admin ID: " + admin.getUserId());
                                break label;
                            } else {
                                System.out.println("\nPassword Mismatch. Try Again!");
                            }
                        }
                    }
                }
                editAdminProfile();
            }
            else {
                System.out.println("\nInvalid Option. Try Again!");
                editAdminProfile();
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

    public static void viewAllCustomers() {
        String userId = Login.getLoggedInAdmin();
        int countCustomers = 0;
        int totalCustomers = countCustomers();
        if (totalCustomers == 0) {
            System.out.println("\nNo Registered Customers Found.\n");
        }
        else {
            System.out.println("\nTotal Number of Registered Customers: " + totalCustomers);
            System.out.println("---------------------------------------------------------" +
                    "--------------------------------------------------------------------");
            System.out.println("         " + "Customer Name" +
                    Utilities.leftPad("E-mail Address", 76, ' '));
            System.out.println("---------------------------------------------------------" +
                    "--------------------------------------------------------------------");

            try {
                Connection connection = Database.connectDatabase();
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM customers";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String firstNameOutput = resultSet.getString("first_name");
                    String lastNameOutput = resultSet.getString("last_name");
                    String emailOutput = resultSet.getString("email");

                    Customer customer = new Customer(firstNameOutput, lastNameOutput, emailOutput);

                    System.out.println(Utilities.leftPad(String.valueOf(countCustomers + 1), 5, '0') + " -> " +
                            Utilities.rightPad(customer.getFirstName() + " " + customer.getLastName(), 71, ' ') +
                            "    " + customer.getEmail());

                    countCustomers++;
                }

                System.out.println("\n---------------------------------------------------------" +
                        "--------------------------------------------------------------------\n");

                connection.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.print("Press q to go back to the Dashboard: ");
        Scanner userInput = new Scanner(System.in);

        if (userInput.nextLine().trim().equalsIgnoreCase("q")) { Menu.adminMenu(userId); }
        else { viewAllCustomers(); }
    }

    public static void viewCustomerCount() {
        String userId = Login.getLoggedInAdmin();
        System.out.println("\nTotal Number of Registered Customers: " + countCustomers());

        System.out.print("\nPress q to go back to the Dashboard: ");
        Scanner userInput = new Scanner(System.in);

        if (userInput.nextLine().trim().equalsIgnoreCase("q")) { Menu.adminMenu(userId); }
        else { viewCustomerCount(); }
    }


    private static int countCustomers() {
        int numOfCustomers = 0;

        try {
            Connection connection = Database.connectDatabase();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM customers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                numOfCustomers++;
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return numOfCustomers;
    }

}
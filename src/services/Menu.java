package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {
    private static boolean exit = false;
    private static boolean adminExit = false;
    private static boolean customerExit = false;

    public static void loginMenu() {
        System.out.println("\n<<<<<<<< Credit Management System >>>>>>>>\n");
        System.out.println("1. Admin Login\n" +
                "2. Customer Login\n" +
                "3. Register - to create a new account\n" +
                "4. Change Password\n" +
                "5. Exit Application");
        System.out.print("\n** Menu option number: ");

        Scanner option = new Scanner(System.in);
        String optionNum = option.nextLine();

        if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= 5)) {
            switch (optionNum) {
                case "1" -> Login.adminLogin();
                case "2" -> Login.customerLogin();
                case "3" -> Login.registerNewAccount();
                case "4" -> Login.changePassword();
                case "5" -> {
                    String prompt = Utilities.exit();
                    if (prompt.equals("Y") || prompt.equals("y")) {
                        System.out.println("\nThank you for using the application.\n");
                        exit = true;
                    }
                }
            }
        } else {
            System.out.println("\nInvalid Option. Try Again!");
        }
    }

    public static void adminMenu(String userId) {
        System.out.println("\n<<<<<<<< Admin Dashboard >>>>>>>>\n");
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss")));
        System.out.println("Admin: " + userId);
        System.out.println("\n1. Edit Admin Profile\n" +
                "2. View All Customers\n" +
                "3. View Customer Count\n" +
                "4. Log Out");
        System.out.print("\n** Menu option number: ");

        Scanner option = new Scanner(System.in);
        String optionNum = option.nextLine();

        if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= 4)) {
            switch (optionNum) {
                case "1" -> AdminServices.editAdminProfile();
                case "2" -> AdminServices.viewAllCustomers();
                case "3" -> AdminServices.viewCustomerCount();
                case "4" -> {
                    String prompt = Utilities.logOut();
                    if (prompt.equals("Y") || prompt.equals("y")) {
                        System.out.println("\nLogged Out Successfully!\n");
                        adminExit = true;
                    }
                }
            }
        } else {
            System.out.println("\nInvalid Option. Try Again!");
        }
    }

    public static void customerMenu(String userId) {
        System.out.println("\n<<<<<<<< Customer Dashboard >>>>>>>>\n");
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss")));
        System.out.println("Customer: " + userId);
        System.out.println("\n1. Edit Customer Profile\n" +
                "2. View Credit Accounts\n" +
                "3. Open New Credit Account\n" +
                "4. View Credit Account Statement\n" +
                "5. Make A Transaction\n" +
                "6. Log Out");
        System.out.print("\n** Menu option number: ");

        Scanner option = new Scanner(System.in);
        String optionNum = option.nextLine();

        if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= 6)) {
            switch (optionNum) {
                case "1" -> CustomerServices.editCustomerProfile();
                case "2" -> CustomerServices.viewAllCreditAccounts();
                case "3" -> CustomerServices.openNewCreditAccount();
                case "4" -> CustomerServices.viewCreditAccountStatement();
                case "5" -> CustomerServices.makeTransaction();
                case "6" -> {
                    String prompt = Utilities.logOut();
                    if (prompt.equals("Y") || prompt.equals("y")) {
                        System.out.println("\nLogged Out Successfully!\n");
                        customerExit = true;
                    }
                }
            }
        } else {
            System.out.println("\nInvalid Option. Try Again!");
        }
    }

    public static boolean isExit() {
        return exit;
    }
    public static boolean isAdminExit() {
        return adminExit;
    }
    public static boolean isCustomerExit() {
        return customerExit;
    }
}

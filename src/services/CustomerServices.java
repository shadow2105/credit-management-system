package services;

import model.CreditAccount;
import model.Customer;
import model.Transaction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerServices {
    public static void editCustomerProfile() {
        String userId = Login.getLoggedInCustomer();
        System.out.println("\n----------------------- Customer Profile -----------------------");

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM customers WHERE user_id = '" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            String firstNameOutput = resultSet.getString("first_name");
            String lastNameOutput = resultSet.getString("last_name");
            String addressOutput = resultSet.getString("address");
            String mobileNumOutput = resultSet.getString("mobile_no");
            String emailOutput = resultSet.getString("email");
            String occupationOutput = resultSet.getString("occupation");

            Customer customer = new Customer(firstNameOutput, lastNameOutput, addressOutput, mobileNumOutput, emailOutput, occupationOutput);
            customer.setUserId(userId);

            System.out.println("Press q to go back to the Dashboard.");
            System.out.println("\n[ " + customer.getFirstName() + " " + customer.getLastName() + " ]" +
                    "\n1. Home Address:  " + customer.getAddress() +
                    "\n2. Mobile Number: " + customer.getMobileNum() +
                    "\n3. Email Address: " + customer.getEmail() +
                    "\n4. Occupation:    " + customer.getOccupation());
            System.out.print("\n** Field option number: ");

            Scanner option = new Scanner(System.in);
            String optionNum = option.nextLine();

            if (optionNum.trim().equalsIgnoreCase("q")) { Menu.customerMenu(userId); }
            else if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= 4)) {
                switch (optionNum) {
                    case "1" -> {
                        Scanner userData = new Scanner(System.in);
                        System.out.println("Press -1 to discard changes.");

                        label:
                        while (true) {
                            System.out.print("\nEnter Home Address: ");
                            String newAddress = userData.nextLine().trim().toUpperCase();

                            if (newAddress.equals("-1")) {
                                System.out.println("\nChanges discarded!\nHome Address change unsuccessful.");
                                break label;
                            } else if (newAddress == "") {
                                System.out.println("\nNew Home Address cannot be blank. Try Again!");
                            } else {
                                query = "UPDATE customers "
                                        + "SET address = '" + newAddress + "' "
                                        + "WHERE user_id = '" + customer.getUserId() + "'";
                                statement.executeUpdate(query);
                                System.out.println("\nHome Address changed successfully for Customer ID: " + customer.getUserId());
                                break label;
                            }
                        }
                    }
                    case "2" -> {
                        Scanner userData = new Scanner(System.in);
                        System.out.println("Press -1 to discard changes.");

                        label:
                        while (true) {
                            System.out.print("\nEnter Mobile Number: ");
                            String newMobileNum = userData.nextLine().trim();

                            if (newMobileNum.equals("-1")) {
                                System.out.println("\nChanges discarded!\nMobile Number change unsuccessful.");
                                break label;
                            } else if (newMobileNum == "") {
                                System.out.println("\nNew Mobile Number cannot be blank. Try Again!");
                            } else {
                                query = "UPDATE customers "
                                        + "SET mobile_no = '" + newMobileNum + "' "
                                        + "WHERE user_id = '" + customer.getUserId() + "'";
                                statement.executeUpdate(query);
                                System.out.println("\nMobile Number changed successfully for Customer ID: " + customer.getUserId());
                                break label;
                            }
                        }
                    }
                    case "3" -> {
                        Scanner userData = new Scanner(System.in);
                        System.out.println("Press -1 to discard changes.");

                        label:
                        while (true) {
                            System.out.print("\nEnter Email Address: ");
                            String newEmail = userData.nextLine().toLowerCase().trim();

                            if (newEmail.equals("-1")) {
                                System.out.println("\nChanges discarded!\nEmail Address change unsuccessful.");
                                break label;
                            } else if (newEmail == "") {
                                System.out.println("\nNew Email Address cannot be blank. Try Again!");
                            } else {
                                query = "UPDATE customers "
                                        + "SET email = '" + newEmail + "' "
                                        + "WHERE user_id = '" + customer.getUserId() + "'";
                                statement.executeUpdate(query);
                                System.out.println("\nEmail Address changed successfully for Customer ID: " + customer.getUserId());
                                break label;
                            }
                        }
                    }
                    case "4" -> {
                        Scanner userData = new Scanner(System.in);
                        System.out.println("Press -1 to discard changes.");

                        label:
                        while (true) {
                            System.out.print("\nEnter Occupation: ");
                            String newOccupation = Utilities.toTitleCase(userData.nextLine().trim());

                            if (newOccupation.equals("-1")) {
                                System.out.println("\nChanges discarded!\nOccupation change unsuccessful.");
                                break label;
                            } else if (newOccupation == "") {
                                System.out.println("\nNew Occupation cannot be blank. Try Again!");
                            } else {
                                query = "UPDATE customers "
                                        + "SET occupation = '" + newOccupation + "' "
                                        + "WHERE user_id = '" + customer.getUserId() + "'";
                                statement.executeUpdate(query);
                                System.out.println("\nOccupation changed successfully for Customer ID: " + customer.getUserId());
                                break label;
                            }
                        }
                    }
                }
                editCustomerProfile();
            }
            else {
                System.out.println("\nInvalid Option. Try Again!");
                editCustomerProfile();
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void viewAllCreditAccounts() {
        String userId = Login.getLoggedInCustomer();
        System.out.println("\n----------------------- Credit Accounts -----------------------");

        int totalCreditAccounts = countCreditAccounts(userId);
        DecimalFormat myFormatter = new DecimalFormat("###,###,###.00");

        if (totalCreditAccounts == 0) {
            System.out.println("\nNo Registered Credit Accounts Found.\n");
        }
        else {
            System.out.println("\nTotal Number of Registered Credit Accounts: " + totalCreditAccounts);

            float totalCreditLimit = calculateTotalCreditLimit(userId);
            float totalAccountsBalance = calculateTotalAccountsBalance(userId);

            System.out.println("\nTotal Credit Limit            : $" + myFormatter.format(totalCreditLimit));
            System.out.println("Total Current Account Balance : $" + myFormatter.format(totalAccountsBalance));
            System.out.println("Total Credit Available        : $" + myFormatter.format(totalCreditLimit - totalAccountsBalance));
            System.out.println("\n---------------------------------------------------------" +
                    "--------------------------------------------------------------------");
            System.out.println("        " +
                    Utilities.rightPad("Account Number", 22, ' ') +
                    Utilities.rightPad("Credit Limit", 17, ' ') +
                    Utilities.rightPad("Current Balance", 21, ' ') +
                    "Credit Available");
            System.out.println("---------------------------------------------------------" +
                    "--------------------------------------------------------------------");

            ArrayList<CreditAccount> creditAccounts = getUserCreditAccounts(userId);
            int countCreditAccounts = 0;
            while (countCreditAccounts < creditAccounts.size()) {

                CreditAccount creditAccount = creditAccounts.get(countCreditAccounts);

                float creditLimit = creditAccount.getCreditLimit();
                float accountBalance = creditAccount.getCurrentBalance();

                System.out.println(Utilities.leftPad(String.valueOf(countCreditAccounts + 1), 3, '0') + " -> " +
                            Utilities.rightPad(creditAccount.getAccountNum(), 25, ' ') +
                            Utilities.rightPad("$" + myFormatter.format(creditLimit), 18, ' ') +
                            Utilities.rightPad("$" + myFormatter.format(accountBalance), 21, ' ') +
                            Utilities.rightPad("$" + myFormatter.format(creditLimit - accountBalance), 21, ' '));

                countCreditAccounts++;
            }

            System.out.println("\n---------------------------------------------------------" +
                        "--------------------------------------------------------------------\n");
        }

        System.out.print("Press q to go back to the Dashboard: ");
        Scanner userInput = new Scanner(System.in);

        if (userInput.nextLine().trim().equalsIgnoreCase("q")) { Menu.customerMenu(userId); }
        else { viewAllCreditAccounts(); }
    }

    public static void openNewCreditAccount() {
        String userId = Login.getLoggedInCustomer();
        System.out.println("\n----------------------- Credit Account Application Form -----------------------\n");
        System.out.println("Mandatory Fields are marked with (*)");
        System.out.println("Press -1 to discard changes.");

        Scanner userData = new Scanner(System.in);

        System.out.print("\nEnter Gross Annual Household Income (*): $");
        String grossHouseholdAnnualIncome = userData.nextLine().trim();

        System.out.print("Enter Email Address (to receive status updates): ");
        String email = userData.nextLine().toLowerCase().trim();

        System.out.println("\n--------------------------------------------------------------------------\n");

        if (grossHouseholdAnnualIncome.equals("-1") || email.equals("-1")) {
            System.out.println("Changes discarded!\nNew Credit Account Opening unsuccessful.\n");
        }
        else if (grossHouseholdAnnualIncome.equals("")) {
            System.out.println("Fields marked with (*) cannot be left blank. Try Again!");
            openNewCreditAccount();
        }
        else if (Utilities.isFloat(grossHouseholdAnnualIncome) && Float.parseFloat(grossHouseholdAnnualIncome) >= 0.0F) {
            System.out.println(">> By submitting the above Credit Account Application Form,\n" +
                    "you confirm to understand and agree to the Terms and Conditions.");

            float grossIncome = Float.parseFloat(grossHouseholdAnnualIncome);
            System.out.println("\n............... Customer submits Credit Account Application Form\nand waits for approval ...............");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Please Wait!");
            }

            Connection connection = Database.connectDatabase();

            if (grossIncome < 10000.00F) {
                System.out.println("\nNew Credit Account Not Approved!\n");
            }
            else {
                String accountNum = generateCreditAccountNum();

                int roundEstimate = (((int) grossIncome + 999) / 1000 ) * 1000;
                float creditLimit = (float) (0.1 * roundEstimate);

                float currentBalance = 0.0F;

                try {
                    Statement statement = connection.createStatement();
                    String query = "INSERT INTO " +
                            "credit_accounts (account_no, credit_limit, balance, user_id) " +
                            "VALUES ('" + accountNum + "', " + creditLimit + ", " + currentBalance +  ", '" + userId + "' )";
                    statement.executeUpdate(query);

                    CreditAccount creditAccount = new CreditAccount(accountNum, creditLimit, userId);

                    System.out.println("\nNew Credit Account Has Been Approved!");
                    System.out.println("Account Number: '" + creditAccount.getAccountNum() + "' opened successfully.");
                    System.out.println("\nView your Credit Accounts using option 2 of the Customer Dashboard.\n");

                    connection.close();
                }
                catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
        else {
            System.out.println("Invalid Gross Annual Household Income. Try Again!");
            openNewCreditAccount();
        }

        System.out.print("Press q to go back to the Dashboard: ");
        Scanner userInput = new Scanner(System.in);

        if (userInput.nextLine().trim().equalsIgnoreCase("q")) { Menu.customerMenu(userId); }
        else { openNewCreditAccount(); }
    }

    public static void viewCreditAccountStatement() {
        String userId = Login.getLoggedInCustomer();
        System.out.println("\n----------------------- Credit Account Statements -----------------------");

        int totalCreditAccounts = countCreditAccounts(userId);
        if (totalCreditAccounts == 0) {
            System.out.println("\nNo Registered Credit Accounts Found to view Account Statements.\n");

            System.out.print("Press q to go back to the Dashboard: ");
            Scanner userInput = new Scanner(System.in);

            if (userInput.nextLine().trim().equalsIgnoreCase("q")) { Menu.customerMenu(userId); }
            else { viewCreditAccountStatement(); }
        }
        else {
            ArrayList<CreditAccount> creditAccounts = getUserCreditAccounts(userId);
            int numOfCreditAccounts = creditAccounts.size();

            System.out.println("Press q to go back to the Dashboard.");
            for (int i = 0; i < numOfCreditAccounts; i++) {
                System.out.print("\n" + (i + 1) + ". " + creditAccounts.get(i));
            }
            System.out.print("\n\n** Credit Account option number: ");

            Scanner option = new Scanner(System.in);
            String optionNum = option.nextLine();

            if (optionNum.trim().equalsIgnoreCase("q")) {
                Menu.customerMenu(userId);
            }
            else if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= numOfCreditAccounts)) {
                CreditAccount selectedCreditAccount = creditAccounts.get(Integer.parseInt(optionNum)-1);

                generateAccountStatement(selectedCreditAccount, userId);

                System.out.print("\nPress q to go back: ");
                String userInput = option.nextLine();

                if (userInput.trim().equalsIgnoreCase("q")) {
                    viewCreditAccountStatement();
                }
                else {
                    viewCreditAccountStatement();
                }
            }
            else {
                System.out.println("\nInvalid Option. Try Again!");
                viewCreditAccountStatement();
            }
        }
    }

    public static void makeTransaction() {
        String userId = Login.getLoggedInCustomer();
        System.out.println("\n----------------------- Transaction Center -----------------------");

        int totalCreditAccounts = countCreditAccounts(userId);
        if (totalCreditAccounts == 0) {
            System.out.println("\nNo Registered Credit Accounts Found to make a Transaction.\n");

            System.out.print("Press q to go back to the Dashboard: ");
            Scanner userInput = new Scanner(System.in);

            if (userInput.nextLine().trim().equalsIgnoreCase("q")) { Menu.customerMenu(userId); }
            else { makeTransaction(); }
        }
        else {
            ArrayList<CreditAccount> creditAccounts = getUserCreditAccounts(userId);
            int numOfCreditAccounts = creditAccounts.size();

            System.out.println("Press q to go back to the Dashboard.");
            for (int i = 0; i < numOfCreditAccounts; i++) {
                System.out.print("\n" + (i + 1) + ". " + creditAccounts.get(i));
            }
            System.out.print("\n\n** Credit Account option number: ");

            Scanner option = new Scanner(System.in);
            String optionNum = option.nextLine();

            if (optionNum.trim().equalsIgnoreCase("q")) {
                Menu.customerMenu(userId);
            }
            else if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= numOfCreditAccounts)) {
                CreditAccount selectedCreditAccount = creditAccounts.get(Integer.parseInt(optionNum)-1);

                makeAccountTransaction(selectedCreditAccount);
                makeTransaction();
            }
            else {
                System.out.println("\nInvalid Option. Try Again!");
                makeTransaction();
            }
        }
    }


    private static int countCreditAccounts(String userId) {
        int countCreditAccounts = 0;

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(account_no) AS count FROM credit_accounts " +
                    "WHERE user_id='" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            countCreditAccounts = resultSet.getInt("count");

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return countCreditAccounts;
    }
    private static float calculateTotalAccountsBalance(String userId) {
        float totalAccountsBalance = 0.0F;

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT SUM(balance) AS total FROM credit_accounts " +
                    "WHERE user_id='" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            totalAccountsBalance = resultSet.getFloat("total");

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return totalAccountsBalance;
    }
    private static float calculateTotalCreditLimit(String userId) {
        float totalCreditLimit = 0.0F;

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT SUM(credit_limit) AS total FROM credit_accounts " +
                    "WHERE user_id='" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            totalCreditLimit = resultSet.getFloat("total");

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return totalCreditLimit;
    }
    private static ArrayList<CreditAccount> getUserCreditAccounts(String userId) {
        ArrayList<CreditAccount> creditAccounts = new ArrayList<>();

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM credit_accounts WHERE user_id='" + userId + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String accountNumOutput = resultSet.getString("account_no");
                float creditLimitOutput = resultSet.getFloat("credit_limit");
                float currentBalanceOutput = resultSet.getFloat("balance");
                String userIdOutput = resultSet.getString("user_id");

                CreditAccount creditAccount = new CreditAccount(userIdOutput, accountNumOutput, creditLimitOutput, currentBalanceOutput);

                creditAccounts.add(creditAccount);
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return creditAccounts;
    }
    private static String generateCreditAccountNum() {
        String accountNum = Utilities.generateRandomNumericString(16);
        int accountExists = 0;

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(account_no) AS count FROM credit_accounts " +
                    "WHERE account_no='" + accountNum + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            accountExists = resultSet.getInt("count");

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        if (accountExists != 0) { generateCreditAccountNum(); }

        return accountNum;
    }
    private static float getLastAccountBalanceForMonth(int month, int year, String accountNum) {
        float monthClosingBalance = 0.0F;

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT closing_balance FROM transactions " +
                    "WHERE account_no='" + accountNum + "' AND MONTH(transaction_date)='" + month + "' AND Year(transaction_date)='" + year + "' ORDER BY transaction_date DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                monthClosingBalance = resultSet.getFloat("closing_balance");
            }
            else {
                monthClosingBalance = 0.0F;
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return monthClosingBalance;
    }
    private static ArrayList<Transaction> getMonthlyAccountTransactions(int month, int year, String accountNum, char transaction_type) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM transactions " +
                    "WHERE account_no='" + accountNum + "' AND MONTH(transaction_date)='" + month + "' AND Year(transaction_date)='" + year + "' AND transaction_type='" + transaction_type + "' ORDER BY transaction_date";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String transactionIdOutput = resultSet.getString("transaction_id");
                String transactionDateOutput = resultSet.getString("transaction_date");
                Date postDateOutput = resultSet.getDate("post_date");
                String descriptionOutput = resultSet.getString("dscription");
                char typeOutput = resultSet.getString("transaction_type").charAt(0);
                float amountOutput = resultSet.getFloat("amount");
                String accountNumOutput = resultSet.getString("account_no");
                float closingBalanceOutput = resultSet.getFloat("closing_balance");

                Transaction transaction = new Transaction(transactionIdOutput, transactionDateOutput, postDateOutput, descriptionOutput, typeOutput, amountOutput, accountNumOutput, closingBalanceOutput);

                transactions.add(transaction);
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return transactions;
    }
    private static void generateAccountStatement(CreditAccount selectedCreditAccount, String userId) {
        DecimalFormat myFormatter = new DecimalFormat("###,###,###.00");

        System.out.println("\n----------------------- Credit Account Statements -----------------------\n");

        System.out.println("You selected Credit Account: " + selectedCreditAccount.getAccountNum());

        System.out.println("\nMandatory Fields are marked with (*)");
        System.out.println("Press -1 to discard changes.");

        Scanner userData = new Scanner(System.in);

        System.out.println("\nEnter 01 for 'Jan', 02 for 'Feb',......, 12 for 'Dec'");
        System.out.print("Enter Month Number [MM] (*): ");
        String statementMonth = userData.nextLine().trim();

        System.out.print("Enter Year [YYYY] (*) : ");
        String statementYear = userData.nextLine().trim();

        System.out.println("\n--------------------------------------------------------------------------\n");

        if (statementMonth.equals("-1") || statementYear.equals("-1")) {
            System.out.println("Changes discarded!");
        }
        else if (statementMonth.equals("") || statementYear.equals("")) {
            System.out.println("Fields marked with (*) cannot be left blank. Try Again!");
            generateAccountStatement(selectedCreditAccount, userId);
        }
        else if ((Utilities.isInteger(statementMonth) && Utilities.isInteger(statementYear)) && (Integer.parseInt(statementMonth) >= 1 && Integer.parseInt(statementMonth) <= 12) && (Integer.parseInt(statementYear) >= 1999 && Integer.parseInt(statementYear) <= Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"))))) {

            int month = Integer.parseInt(statementMonth);
            int year = Integer.parseInt(statementYear);

            /*if (ChronoUnit.DAYS.between(LocalDate.of(year, month, 1), LocalDate.now()) < 0) {
                System.out.println("Account Statement not available for " + Utilities.leftPad(String.valueOf(month), 2, '0') + ", " + year +
                        "\nPlease check again later!") ;
            }*/
            //else {
                String accountNum = selectedCreditAccount.getAccountNum();

                LocalDate localDate = LocalDate.of(year, month, 1);
                Date statementDate = Date.valueOf(localDate);

                String statementId = accountNum.substring(12) + statementDate.toString().replace("-", "") + userId.substring(userId.length() / 2);

                float last2MonthAmountDue = 0.0F;
                float lastMonthAmountDue = 0.0F;

                ArrayList<Transaction> payments;
                ArrayList<Transaction> credits;

                String statementPeriod;
                long daysBetween;

                if (month == 1) {
                    statementPeriod = String.valueOf(LocalDate.of(year - 1, 12, 1)) + " to " +
                            String.valueOf(statementDate.toLocalDate().minusDays(1));

                    daysBetween = ChronoUnit.DAYS.between(LocalDate.of(year - 1, 12, 1), statementDate.toLocalDate());

                    last2MonthAmountDue = getLastAccountBalanceForMonth(11, year - 1, accountNum);
                    lastMonthAmountDue = last2MonthAmountDue + getLastAccountBalanceForMonth(12, year - 1, accountNum);

                    payments = getMonthlyAccountTransactions(12, year - 1, accountNum, 'P');
                    credits = getMonthlyAccountTransactions(12, year - 1, accountNum, 'C');
                } else if (month == 2) {
                    statementPeriod = String.valueOf(LocalDate.of(year, month - 1, 1)) + " to " +
                            String.valueOf(statementDate.toLocalDate().minusDays(1));

                    daysBetween = ChronoUnit.DAYS.between(LocalDate.of(year, month - 1, 1), statementDate.toLocalDate());

                    last2MonthAmountDue = getLastAccountBalanceForMonth(12, year - 1, accountNum);
                    lastMonthAmountDue = last2MonthAmountDue + getLastAccountBalanceForMonth(month - 1, year, accountNum);

                    payments = getMonthlyAccountTransactions(month - 1, year, accountNum, 'P');
                    credits = getMonthlyAccountTransactions(month - 1, year, accountNum, 'C');
                } else {
                    statementPeriod = String.valueOf(LocalDate.of(year, month - 1, 1)) + " to " +
                            String.valueOf(statementDate.toLocalDate().minusDays(1));

                    daysBetween = ChronoUnit.DAYS.between(LocalDate.of(year, month - 1, 1), statementDate.toLocalDate().minusDays(2));

                    last2MonthAmountDue = getLastAccountBalanceForMonth(month - 2, year, accountNum);
                    lastMonthAmountDue = last2MonthAmountDue + getLastAccountBalanceForMonth(month - 1, year, accountNum);

                    payments = getMonthlyAccountTransactions(month - 1, year, accountNum, 'P');
                    credits = getMonthlyAccountTransactions(month - 1, year, accountNum, 'C');
                }

                float minimumPayment = (float) 0.01 * selectedCreditAccount.getCreditLimit();
                ;
                if (lastMonthAmountDue < minimumPayment) {
                    minimumPayment = lastMonthAmountDue;
                }

                Date dueDate = Date.valueOf(statementDate.toLocalDate().plusDays(daysBetween));

                String customerName = userId;
                String customerAddress = "";
                Connection connection = Database.connectDatabase();
                try {
                    Statement statement = connection.createStatement();
                    String query = "SELECT first_name, last_name, address FROM customers " +
                            "WHERE user_id='" + userId + "'";
                    ResultSet resultSet = statement.executeQuery(query);
                    resultSet.next();

                    String firstNameOutput = resultSet.getString("first_name");
                    String lastNameOutput = resultSet.getString("last_name");
                    String addressOutput = resultSet.getString("address");

                    customerName = firstNameOutput + " " + lastNameOutput;
                    customerAddress = addressOutput;

                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }

                System.out.println("\n............... Generating Credit Account Statement ...............\n\n");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Please Wait!");
                }

                String accountStatementSummary = "\n==========================================================================" +
                        "==========================================================================" +
                        "\n                                                               Credit Management System E-statement                    " +
                        "\n\n  " + customerName.toUpperCase() +
                        "\n  " + customerAddress +
                        "\n\n- Statement Id     : " + statementId +
                        "\n- Account Number   : " + accountNum +
                        "\n- Statement Date   : " + statementDate +
                        "\n- Statement Period : " + statementPeriod +
                        "\n\n  Account Summary ------------------------------------" +
                        "\n- Credit Limit     : $" + myFormatter.format(selectedCreditAccount.getCreditLimit()) +
                        "\n- Credit Available : $" + myFormatter.format(selectedCreditAccount.getCreditLimit() - lastMonthAmountDue) +
                        "\n- Amount Due       : $" + myFormatter.format(lastMonthAmountDue) +
                        "\n- Minimum Payment  : $" + myFormatter.format(minimumPayment) +
                        "\n- Due Date         : " + dueDate +
                        "\n\n  Transactions ---------------------------------------";


                String accountStatementPaymentsHeader = "\n\n- [Your Payments]" +
                        "\n--------------------------------------------------------------------------" +
                        "--------------------------------------------------------------------" +
                        "\n  Transaction ID    Transaction Date    Post Date     Description" + Utilities.leftPad("Amount", 77, ' ') +
                        "\n--------------------------------------------------------------------------" +
                        "--------------------------------------------------------------------";

                float totalPayments = 0.0F;
                String accountStatementPayments = "";
                for (Transaction payment : payments) {
                    accountStatementPayments += "\n     " + Utilities.rightPad(payment.getTransactionId(), 17, ' ') +
                            Utilities.rightPad(payment.getTransactionDate().substring(0, 10), 18, ' ') +
                            Utilities.rightPad(payment.getPostDate().toString(), 14, ' ') +
                            Utilities.rightPad(payment.getDescription(), 82, ' ') +
                            myFormatter.format(payment.getAmount());
                    totalPayments += payment.getAmount();
                }
                String accountStatementPaymentsFooter = "\n--------------------------------------------------------------------------" +
                        "--------------------------------------------------------------------" +
                        "\n  * Total Payments" + Utilities.leftPad("$" + myFormatter.format(totalPayments), 124, ' ');

                float totalCredits = 0.0F;
                String accountStatementCreditsHeader = "\n\n- [Your Credits and Charges]" +
                        "\n--------------------------------------------------------------------------" +
                        "--------------------------------------------------------------------" +
                        "\n  Transaction ID    Transaction Date    Post Date     Description" + Utilities.leftPad("Amount", 77, ' ') +
                        "\n--------------------------------------------------------------------------" +
                        "--------------------------------------------------------------------";

                String accountStatementCredits = "";
                for (Transaction credit : credits) {
                    accountStatementCredits += "\n     " + Utilities.rightPad(credit.getTransactionId(), 17, ' ') +
                            Utilities.rightPad(credit.getTransactionDate().substring(0, 10), 18, ' ') +
                            Utilities.rightPad(credit.getPostDate().toString(), 14, ' ') +
                            Utilities.rightPad(credit.getDescription(), 82, ' ') +
                            myFormatter.format(credit.getAmount());
                    totalCredits += credit.getAmount();
                }
                String accountStatementCreditsFooter = "\n--------------------------------------------------------------------------" +
                        "--------------------------------------------------------------------" +
                        "\n  * Total Credits" + Utilities.leftPad("$" + myFormatter.format(totalCredits), 125, ' ');

                String accountStatement = accountStatementSummary +
                        accountStatementPaymentsHeader +
                        accountStatementPayments +
                        accountStatementPaymentsFooter +
                        accountStatementCreditsHeader +
                        accountStatementCredits +
                        accountStatementCreditsFooter +
                        "\n\n\n- Amount Due = Previous Balance + (Total Credits - Total Payments) " +
                        "\n             = $" + myFormatter.format(last2MonthAmountDue) + " + ( $" +
                        myFormatter.format(totalCredits) + " - $" + myFormatter.format(totalPayments) + " )" +
                        "\n             = $" + myFormatter.format(lastMonthAmountDue) +
                        "\n\n==========================================================================" +
                        "==========================================================================";

                System.out.println(accountStatement);

                File filePath = new File("./estatements/" + statementId + ".txt");
                PrintWriter writeToFile = null;
                try {
                    if (!filePath.exists()) {
                        filePath.createNewFile();
                    }

                    writeToFile = new PrintWriter(filePath);
                    writeToFile.write(accountStatement);

                    writeToFile.close();

                    System.out.println("\nAccount Statement downloaded successfully to path: \n" + filePath.getAbsolutePath());
                }
                catch (IOException e) {
                    System.out.println("\nUnable to download Account Statement to the specified path.");
                }
            }
        //}
        else {
            System.out.println("Invalid Month or Year. Try Again!");
            generateAccountStatement(selectedCreditAccount, userId);
        }

    }
    private static String generateAccountTransactionId(String accountNum) {
        String transactionId = Utilities.generateRandomNumericString(6);
        int transactionExists = 0;

        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(transaction_id) AS count FROM transactions " +
                    "WHERE transaction_id='" + transactionId + "' AND account_no='" + accountNum + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            transactionExists = resultSet.getInt("count");

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        if (transactionExists != 0) { generateAccountTransactionId(accountNum); }

        return transactionId;
    }
    private static void makeAccountTransaction(CreditAccount selectedCreditAccount) {
        System.out.println("\n----------------------- Transaction Center -----------------------\n");
        System.out.println("You selected Credit Account: " + selectedCreditAccount.getAccountNum());
        Connection connection = Database.connectDatabase();
        try {
            Statement statement = connection.createStatement();
            System.out.println("Press q to go back.");
            System.out.println("\n1. Credit" +
                    "\n2. Payment");
            System.out.print("\n** Transaction Type option number: ");

            Scanner option = new Scanner(System.in);
            String optionNum = option.nextLine();

            if (optionNum.trim().equalsIgnoreCase("q")) {
                makeTransaction();
            } else if (Utilities.isInteger(optionNum) && (Integer.parseInt(optionNum) > 0 && Integer.parseInt(optionNum) <= 2)) {
                String accountNum = selectedCreditAccount.getAccountNum();
                float currentAccountBalance = selectedCreditAccount.getCurrentBalance();
                float creditLimit = selectedCreditAccount.getCreditLimit();

                String transactionId = generateAccountTransactionId(accountNum);

                LocalDateTime currentDateTime = LocalDateTime.now();
                String transactionDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                int daysToPost = ((int) (3 * Math.random())) + 2;
                Date postDate = Date.valueOf(currentDateTime.toLocalDate().plusDays(daysToPost));

                switch (optionNum) {
                    case "1" -> {
                        Scanner userData = new Scanner(System.in);
                        System.out.println("Mandatory Fields are marked with (*)");
                        System.out.println("Press -1 to discard changes.");

                        char type = 'C';

                        label:
                        while (true) {
                            System.out.print("\nEnter Transaction Description (*): ");
                            String description = Utilities.toTitleCase(userData.nextLine().trim());

                            System.out.print("Enter Transaction Amount (*): $");
                            String amount = userData.nextLine().trim();

                            if (description.equals("-1") || amount.equals("-1")) {
                                System.out.println("\nChanges discarded!\nTransaction unsuccessful.");
                                break label;
                            }
                            else if (description == "" || amount == "") {
                                System.out.println("\nFields marked with (*) cannot be left blank. Try Again!");
                            }
                            else if (Utilities.isFloat(amount) && Float.parseFloat(amount) >= 0.0F) {
                                float closingBalance = (currentAccountBalance + Float.parseFloat(amount));

                                if (closingBalance <= creditLimit) {
                                    String query = "UPDATE credit_accounts "
                                            + "SET balance = '" + closingBalance + "' "
                                            + "WHERE account_no = '" + accountNum + "'";
                                    statement.executeUpdate(query);

                                    query = "INSERT INTO " +
                                            "transactions (transaction_id, account_no, transaction_date, post_date, dscription, transaction_type, amount, closing_balance) " +
                                            "VALUES ('" + transactionId + "', '" + accountNum + "', '" + transactionDate +  "', '" + postDate + "', '" + description + "', '" + type + "', " + amount + ", " + closingBalance + " )";
                                    statement.executeUpdate(query);

                                    Transaction transaction = new Transaction(transactionId, transactionDate, postDate, description, type, Float.parseFloat(amount), accountNum, closingBalance);
                                    System.out.println("\n" + transaction);
                                    System.out.println("\nTransaction Completed Successfully!");
                                }
                                else {
                                    System.out.println("\nTransaction Failed!");
                                }
                                break label;
                            }
                            else {
                                System.out.println("Invalid Transaction Amount. Try Again!");
                            }
                        }
                    }
                    case "2" -> {
                        Scanner userData = new Scanner(System.in);
                        System.out.println("Mandatory Fields are marked with (*)");
                        System.out.println("Press -1 to discard changes.");

                        char type = 'P';
                        String description = "Payment - Thank you";

                        label:
                        while (true) {
                            System.out.print("\nEnter Payment Amount (*): $");
                            String amount = userData.nextLine().trim();

                            if (amount.equals("-1")) {
                                System.out.println("\nChanges discarded!\nTransaction unsuccessful.");
                                break label;
                            } else if (amount == "") {
                                System.out.println("\nFields marked with (*) cannot be left blank. Try Again!");
                            } else if (Utilities.isFloat(amount) && Float.parseFloat(amount) >= 0.0F) {
                                float closingBalance = (currentAccountBalance - Float.parseFloat(amount));

                                String query = "UPDATE credit_accounts "
                                        + "SET balance = '" + closingBalance + "' "
                                        + "WHERE account_no = '" + accountNum + "'";
                                statement.executeUpdate(query);

                                query = "INSERT INTO " +
                                        "transactions (transaction_id, account_no, transaction_date, post_date, dscription, transaction_type, amount, closing_balance) " +
                                        "VALUES ('" + transactionId + "', '" + accountNum + "', '" + transactionDate + "', '" + postDate + "', '" + description + "', '" + type + "', " + amount + ", " + closingBalance + " )";
                                statement.executeUpdate(query);

                                Transaction transaction = new Transaction(transactionId, transactionDate, postDate, description, type, Float.parseFloat(amount), accountNum, closingBalance);
                                System.out.println("\n" + transaction);
                                System.out.println("\nPayment Completed Successfully!");
                                break label;
                            } else {
                                System.out.println("Invalid Payment Amount. Try Again!");
                            }
                        }
                    }
                }
                //makeAccountTransaction(selectedCreditAccount);
            } else {
                System.out.println("\nInvalid Option. Try Again!");
                makeAccountTransaction(selectedCreditAccount);
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

}

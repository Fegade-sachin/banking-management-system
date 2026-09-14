package com.bank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.bank.constants.AccountType;
import com.bank.dao.AccountDao;
import com.bank.dao.CustomerDao;
import com.bank.dao.TransactionDao;
import com.bank.dao.impl.AccountDaoImpl;
import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.dao.impl.TransactionDaoImpl;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.service.AccountService;
import com.bank.service.CustomerService;
import com.bank.service.TransactionService;
import com.bank.service.impl.AccountServiceImpl;
import com.bank.service.impl.CustomerServiceImpl;
import com.bank.service.impl.TransactionServiceImpl;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static CustomerService customerService;
    private static AccountService accountService;
    private static TransactionService transactionService;

    public static void main(String[] args) {

        initializeServices();

        boolean running = true;

        while (running) {

            printMenu();

            int choice = readInt("Enter choice: ");

            try {

                switch (choice) {

                    case 1:
                        createCustomer();
                        break;

                    case 2:
                        createAccount();
                        break;

                    case 3:
                        deposit();
                        break;

                    case 4:
                        withdraw();
                        break;

                    case 5:
                        transfer();
                        break;

                    case 6:
                        checkBalance();
                        break;

                    case 7:
                        transactionHistory();
                        break;

                    case 8:
                        viewCustomers();
                        break;

                    case 9:
                        viewAccounts();
                        break;

                    case 10:
                        closeAccount();
                        break;

                    case 11:
                        running = false;
                        System.out.println("Thank you for using Banking System.");
                        break;

                    default:
                        System.out.println("Invalid choice.");

                }

            } catch (RuntimeException e) {

                System.out.println("X " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void initializeServices() {

        CustomerDao customerDao = new CustomerDaoImpl();

        AccountDao accountDao = new AccountDaoImpl();

        TransactionDao transactionDao =
                new TransactionDaoImpl();

        customerService =
                new CustomerServiceImpl(customerDao);

        accountService =
                new AccountServiceImpl(
                        accountDao,
                        customerDao,
                        transactionDao
                );

        transactionService =
                new TransactionServiceImpl(transactionDao);
    }

    private static void printMenu() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("       BANKING MANAGEMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Create Customer");
        System.out.println("2. Create Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. Check Balance");
        System.out.println("7. Transaction History");
        System.out.println("8. View Customers");
        System.out.println("9. View Accounts");
        System.out.println("10. Close Account");
        System.out.println("11. Exit");
        System.out.println("======================================");
    }

    private static void createCustomer() {

        String firstName = readString("First Name: ");

        String lastName = readString("Last Name: ");

        String email = readString("Email: ");

        String phone = readString("Phone: ");

        String address = readString("Address: ");

        Customer customer = new Customer(
                0,
                firstName,
                lastName,
                email,
                phone,
                address
        );

        customerService.createCustomer(customer);

        System.out.println(
                "Customer created successfully. Customer ID: "
                        + customer.getCustomerId()
        );
    }

    private static void createAccount() {

        long customerId =
                readLong("Customer ID: ");

        System.out.println("1. SAVINGS");
        System.out.println("2. CURRENT");

        int type = readInt("Select account type: ");

        AccountType accountType;

        if (type == 1) {
            accountType = AccountType.SAVINGS;
        } else if (type == 2) {
            accountType = AccountType.CURRENT;
        } else {
            System.out.println("Invalid account type.");
            return;
        }

        accountService.createAccount(
                customerId,
                accountType
        );
    }

    private static void deposit() {

        long accountNumber =
                readLong("Account Number: ");

        BigDecimal amount =
                readAmount("Deposit Amount: ");

        accountService.deposit(
                accountNumber,
                amount
        );

        System.out.println(" Deposit successful.");
    }

    private static void withdraw() {

        long accountNumber =
                readLong("Account Number: ");

        BigDecimal amount =
                readAmount("Withdraw Amount: ");

        accountService.withdraw(
                accountNumber,
                amount
        );

        System.out.println(" Withdrawal successful.");
    }

    private static void transfer() {

        long fromAccount =
                readLong("From Account: ");

        long toAccount =
                readLong("To Account: ");

        BigDecimal amount =
                readAmount("Transfer Amount: ");

        accountService.transfer(
                fromAccount,
                toAccount,
                amount
        );

        System.out.println(" Transfer successful.");
    }

    private static void checkBalance() {

        long accountNumber =
                readLong(" Account Number: ");

        BigDecimal balance =
                accountService.getBalance(accountNumber);

        System.out.println(
                "Current Balance: ₹" + balance
        );
    }

    private static void transactionHistory() {

        long accountNumber =
                readLong("Account Number: ");

        List<Transaction> transactions =
                transactionService
                        .getTransactions(accountNumber);

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transactions found."
            );

            return;
        }

        System.out.println();
        System.out.println("========== TRANSACTIONS ==========");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void viewCustomers() {

        List<Customer> customers =
                customerService.getAllCustomers();

        if (customers.isEmpty()) {

            System.out.println(
                    "No customers found."
            );

            return;
        }

        System.out.println();
        System.out.println("========== CUSTOMERS ==========");

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private static void viewAccounts() {

        long customerId =
                readLong("Customer ID: ");

        List<Account> accounts =
                accountService
                        .getAccountsByCustomer(customerId);

        if (accounts.isEmpty()) {

            System.out.println(
                    "No accounts found."
            );

            return;
        }

        System.out.println();
        System.out.println("========== ACCOUNTS ==========");

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static void closeAccount() {

        long accountNumber =
                readLong("Account Number: ");

        accountService.closeAccount(
                accountNumber
        );

        System.out.println(
                "6 Account closed successfully."
        );
    }

    private static int readInt(String message) {

        System.out.print(message);

        return Integer.parseInt(
                scanner.nextLine()
        );
    }

    private static long readLong(String message) {

        System.out.print(message);

        return Long.parseLong(
                scanner.nextLine()
        );
    }

    private static String readString(String message) {

        System.out.print(message);

        return scanner.nextLine();
    }

    private static BigDecimal readAmount(String message) {

        System.out.print(message);

        return new BigDecimal(
                scanner.nextLine()
        );
    }
}
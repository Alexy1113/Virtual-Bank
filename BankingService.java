import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankingService {
    private final Map<String, User> users = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public BankingService() {
        users.put("Alice", new User("Alice"));
        users.put("Bob", new User("Bob"));
        users.put("Charlie", new User("Charlie"));
        users.put("Diana", new User("Diana"));
    }

    public void run() {
        while (true) {
            System.out.print("Enter your name to login: ");
            if (!scanner.hasNextLine()) {
                if (scanner.hasNext()) {
                    System.out.println("Error: unexpected end of input.");
                }
                return;
            }
            String username = scanner.nextLine().trim();

            User user = users.get(username);
            if (user != null) {
                System.out.print("Welcome, " + user.getName() + "!");
                System.out.println();
                if (!handleUserSession(user)) {
                    return;
                }
            } else {
                System.out.println("User not found. Please try again.");
            }
        }
    }

    private boolean handleUserSession(User user) {
        while (true) {
            printMenu();
            if (!scanner.hasNextLine()) return false;
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1": showBalance(user); break;
                    case "2": depositMoney(user); break;
                    case "3": withdrawMoney(user); break;
                    case "4": sendMoney(user); break;
                    case "5": investInFunds(user); break;
                    case "6": transferBetweenAccounts(user); break;
                    case "7": withdrawAllInvestments(user); break;
                    case "8":
                        System.out.print("You have been logged out.");
                        System.out.println();
                        return true; // Logout
                    case "9":
                        System.out.println("Thank you for using our banking app. Goodbye!");
                        return false; // Exit
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Banking App Menu ---");
        System.out.println("1. Show balance");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Send money to a person");
        System.out.println("5. Invest in funds");
        System.out.println("6. Transfer between accounts");
        System.out.println("7. Withdraw all investments");
        System.out.println("8. Logout");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private void showBalance(User user) {
        user.getSavingsAccount().applyInterest();
        user.getInvestmentAccount().applyGrowth();

        System.out.println("Savings account balance: $" + user.getSavingsAccount().getBalance());
        System.out.println("Investment account balance:");
        System.out.printf("* Not Invested: $%.2f\n", user.getInvestmentAccount().getBalance());
    }

    private void depositMoney(User user) throws InvalidAmountException {
        System.out.print("Enter amount to deposit to savings account: $");
        try {
            BigDecimal amount = getAmount();
            if (user.getCash().compareTo(amount) < 0) {
                throw new InvalidAmountException("Deposit failed: Insufficient cash on hand");
            }
            user.setCash(user.getCash().subtract(amount));
            user.getSavingsAccount().deposit(amount);
            System.out.println("Deposit successful.");
        } catch (InvalidAmountException e) {
            if (!"Invalid amount format".equals(e.getMessage())) {
                throw e;
            }
        }
    }

    private void withdrawMoney(User user) throws InvalidAmountException {
        System.out.print("Enter amount to withdraw from savings account: $");
        BigDecimal amount = getAmount("Withdrawal failed");
        try {
            user.getSavingsAccount().withdraw(amount);
            user.setCash(user.getCash().add(amount));
            System.out.println("Withdrawal successful.");
        } catch (InvalidAmountException e) {
            throw new InvalidAmountException("Withdrawal failed: " + e.getMessage());
        }
    }

    private void sendMoney(User user) throws InvalidAmountException {
        System.out.println("Available recipients:");

        java.util.List<String> recipientNames = new java.util.ArrayList<>(users.keySet());
        recipientNames.remove(user.getName());
        java.util.Collections.sort(recipientNames);

        for (String name : recipientNames) {
            System.out.println(name);
        }

        System.out.print("Enter recipient's name: ");
        if (!scanner.hasNextLine()) return;
        String recipientName = scanner.nextLine().trim();
        User recipient = users.get(recipientName);
        if (recipient == null || recipient.getName().equals(user.getName())) {
            System.out.println("Invalid recipient.");
            return;
        }
        System.out.print("Enter amount to send: ");
        BigDecimal amount = getAmount("Send failed");
        user.getSavingsAccount().withdraw(amount); // This might throw insufficient funds
        recipient.getSavingsAccount().deposit(amount);
        System.out.println("Sent " + amount + " to " + recipientName + ".");
    }

    private void investInFunds(User user) throws InvalidAmountException {
        System.out.println("Select fund (1. LOW_RISK, 2. MEDIUM_RISK, 3. HIGH_RISK):");
        if (!scanner.hasNextLine()) return;
        String choice = scanner.nextLine().trim();
        Fund fund = choice.equals("1") ? Fund.LOW_RISK : choice.equals("2") ? Fund.MEDIUM_RISK : choice.equals("3") ? Fund.HIGH_RISK : null;
        if (fund == null) {
            System.out.println("Invalid fund selection.");
            return;
        }
        System.out.println("Enter amount to invest:");
        BigDecimal amount = getAmount();
        user.getInvestmentAccount().invest(fund, amount);
        System.out.println("Invested " + amount + " in " + fund);
    }

    private void transferBetweenAccounts(User user) throws InvalidAmountException {
        System.out.println("1. Savings to Investment\n2. Investment to Savings");
        if (!scanner.hasNextLine()) return;
        String direction = scanner.nextLine().trim();
        System.out.println("Enter amount:");
        BigDecimal amount = getAmount();
        if ("1".equals(direction)) {
            user.getSavingsAccount().withdraw(amount);
            user.getInvestmentAccount().deposit(amount);
            System.out.println("Transferred " + amount + " to Investment.");
        } else if ("2".equals(direction)) {
            user.getInvestmentAccount().withdraw(amount);
            user.getSavingsAccount().deposit(amount);
            System.out.println("Transferred " + amount + " to Savings.");
        } else {
            System.out.println("Invalid direction.");
        }
    }

    private void withdrawAllInvestments(User user) {
        user.getInvestmentAccount().withdrawAllInvestments();
        System.out.println("All investments withdrawn to investment account.");
    }

    private BigDecimal getAmount() throws InvalidAmountException {
        return getAmount("Deposit failed");
    }

    private BigDecimal getAmount(String errorPrefix) throws InvalidAmountException {
        if (!scanner.hasNextLine()) throw new InvalidAmountException("No input");
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException(errorPrefix + ": amount must be positive");
            return amount;
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Invalid amount format");
        }
    }
}

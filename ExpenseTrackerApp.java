import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Expense class to represent a single expense entry.
 */
class Expense {
    private int id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    // Static counter for generating unique IDs
    private static int nextId = 1;

    public Expense(double amount, String category, String dateStr, String description) {
        this.id = nextId++;
        this.amount = amount;
        this.category = category;
        try {
            this.date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            this.date = LocalDate.now(); // Default to today if invalid
            System.out.println("Invalid date format. Using today's date: " + this.date);
        }
        this.description = description;
    }

    // Getters
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("ID: %d | Date: %s | Category: %s | Amount: $%.2f | Description: %s",
                id, date, category, amount, description);
    }
}

/**
 * ExpenseTracker class to manage a collection of expenses.
 */
class ExpenseTracker {
    private List<Expense> expenses;

    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        System.out.println("Expense added successfully!");
    }

    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public void viewExpensesByCategory(String category) {
        List<Expense> filtered = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                filtered.add(expense);
            }
        }
        if (filtered.isEmpty()) {
            System.out.println("No expenses found for category: " + category);
            return;
        }
        System.out.println("\n--- Expenses for Category: " + category + " ---");
        for (Expense expense : filtered) {
            System.out.println(expense);
        }
    }

    public void summarizeExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to summarize.");
            return;
        }

        double totalAmount = 0.0;
        for (Expense expense : expenses) {
            totalAmount += expense.getAmount();
        }

        System.out.println("\n--- Expense Summary ---");
        System.out.printf("Total Expenses: $%.2f%n", totalAmount);

        // Summary by category
        System.out.println("\nBreakdown by Category:");
        java.util.Map<String, Double> categoryTotals = new java.util.HashMap<>();
        for (Expense expense : expenses) {
            String cat = expense.getCategory();
            categoryTotals.put(cat, categoryTotals.getOrDefault(cat, 0.0) + expense.getAmount());
        }

        categoryTotals.forEach((cat, amt) -> 
            System.out.printf("%s: $%.2f%n", cat, amt)
        );
    }
}

/**
 * Main class for the console-based Expense Tracker application.
 */
public class ExpenseTrackerApp {
    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Expense Tracker!");

        while (true) {
            System.out.println("\n=== Expense Tracker Menu ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Expenses by Category");
            System.out.println("4. Summarize Expenses");
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter amount: ");
                double amount;
                try {
                    amount = Double.parseDouble(scanner.nextLine().trim());
                    if (amount <= 0) {
                        System.out.println("Amount must be positive!");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount! Please enter a number.");
                    continue;
                }

                System.out.print("Enter category (e.g., Food, Transport): ");
                String category = scanner.nextLine().trim();
                if (category.isEmpty()) {
                    System.out.println("Category is required!");
                    continue;
                }

                System.out.print("Enter date (yyyy-MM-dd, or press Enter for today): ");
                String dateStr = scanner.nextLine().trim();
                if (dateStr.isEmpty()) {
                    dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }

                System.out.print("Enter description: ");
                String description = scanner.nextLine().trim();

                Expense expense = new Expense(amount, category, dateStr, description);
                tracker.addExpense(expense);

            } else if (choice.equals("2")) {
                tracker.viewAllExpenses();

            } else if (choice.equals("3")) {
                System.out.print("Enter category to filter: ");
                String category = scanner.nextLine().trim();
                if (!category.isEmpty()) {
                    tracker.viewExpensesByCategory(category);
                } else {
                    System.out.println("Please enter a category.");
                }

            } else if (choice.equals("4")) {
                tracker.summarizeExpenses();

            } else if (choice.equals("5")) {
                System.out.println("Thank you for using the Expense Tracker. Goodbye!");
                break;

            } else {
                System.out.println("Invalid choice! Please enter 1-5.");
            }
        }

        scanner.close();
    }
}

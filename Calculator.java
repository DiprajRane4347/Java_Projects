import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

    /**
     * Performs addition.
     * @param a The first operand.
     * @param b The second operand.
     * @return The sum of a and b.
     */
    public static double add(double a, double b) {
        return a + b;
    }

   
    public static double subtract(double a, double b) {
        return a - b;
    }

   
    public static double multiply(double a, double b) {
        return a * b;
    }
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Error: Cannot divide by zero.");
        }
        return a / b;
    }

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        double result = 0;
        boolean isValidCalculation = true;

        System.out.println("--- Simple Java Calculator ---");

        try {
           
            System.out.print("Enter first number (e.g., 10.5): ");
            double num1 = scanner.nextDouble();

            // 2. Get the operator
            System.out.print("Enter operator (+, -, *, /): ");
            
            char operator = scanner.next().charAt(0); 

            // 3. Get the second number
            System.out.print("Enter second number (e.g., 5): ");
            double num2 = scanner.nextDouble();

            // 4. Perform the calculation based on the operator
            switch (operator) {
                case '+':
                    result = add(num1, num2);
                    break;
                case '-':
                    result = subtract(num1, num2);
                    break;
                case '*':
                    result = multiply(num1, num2);
                    break;
                case '/':
                   
                    result = divide(num1, num2);
                    break;
                default:
                    System.out.println("Error: Invalid operator entered. Please use +, -, *, or /.");
                    isValidCalculation = false;
                    break;
            }

            // 5. Display the result if the calculation was valid
            if (isValidCalculation) {
                System.out.println("\nResult:");
                System.out.printf("$%f %c %f = %f\n", num1, operator, num2, result);
            }

        } catch (InputMismatchException e) {
           
            System.out.println("Error: Invalid input. Please enter valid numbers.");
        } catch (ArithmeticException e) {
            
            System.out.println(e.getMessage());
        } finally {
            
            scanner.close();
        }
    }
}

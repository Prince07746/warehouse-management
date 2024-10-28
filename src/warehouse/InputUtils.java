package warehouse;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static String getStringInput() {

        return scanner.nextLine();
    }

    public static int getIntInput() {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            try {
                number = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("\n Invalid input. Please enter a valid number: ");
                scanner.next(); // Clear invalid input
            }
        }
        scanner.nextLine(); // Clear the newline character after nextInt()
        return number;
    }


    public static double getDoubleInput() {
        double number = 0;
        boolean valid = false;

        while (!valid) {
            try {
                number = scanner.nextDouble();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("\n Invalid input. Please enter a valid decimal number: ");
                scanner.next(); // Clear invalid input
            }
        }
        scanner.nextLine(); // Clear the newline character after nextDouble()
        return number;
    }


    public static void closeScanner() {
        scanner.close();
    }
}

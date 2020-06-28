package by.yuntsevich.app;

import java.util.Scanner;

public class DataScanner {
    public static int enterIntFromConsole() {
        Scanner sc = new Scanner(System.in);
        int number = 0;
        while (!sc.hasNextInt()){
            sc.next();
        }
        number = sc.nextInt();
        return number;
    }

    public static int enterPositiveIntFromConsole() {
        int number = 0;
        while (number <= 0) {
            number = DataScanner.enterIntFromConsole();
        }
        return number;
    }

    public static double enterDoubleFromConsole() {
        Scanner sc = new Scanner(System.in);
        double number = 0.0;
        while (!sc.hasNextDouble()){
            sc.next();
        }
        number = sc.nextDouble();

        return number;
    }

    public static String enterLineFromConsole() {
        Scanner sc = new Scanner(System.in);
        String line = "";
        while (!sc.hasNextLine()){
            sc.next();
        }
        line = sc.nextLine();
        return line;
    }
}
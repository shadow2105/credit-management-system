package services;

import java.util.Scanner;

public class Utilities {
    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean isFloat(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            float num = Float.parseFloat(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        boolean convertNext = true;
        for (char ch : str.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            }
            else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            }
            else {
                ch = Character.toLowerCase(ch);
            }
           sb.append(ch);
        }

        return sb.toString();
    }
    public static String rightPad(String str, int width, char fill) {
        if(width > str.length()) {
            return str + new String(new char[width - str.length()]).replace('\0', fill);
        }
        else {
            return str.substring(0, width);
        }
    }
    public static String leftPad(String str, int width, char fill) {
        if(width > str.length()) {
            return new String(new char[width - str.length()]).replace('\0', fill) + str;
        }
        else {
            return str.substring(0, width);
        }
    }
    public static String generateRandomNumericString(int size) {
        String NumericString = "1234567890";

        // create StringBuffer size of NumericString
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {

            // generate a random number between
            // 0 to NumericString variable length-1
            int index = (int) ((NumericString.length()-1) * Math.random());

            // add Character one by one in end of sb
            sb.append(NumericString.charAt(index));
        }

        return sb.toString();
    }
    public static String exit() {
        System.out.println("\nAre you sure you want to exit the application?");
        System.out.print("Press Y|y to exit or N|n to stay: ");

        //Create a Scanner object to read from STDIN
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine();
    }
    public static String logOut() {
        System.out.println("\nAre you sure you want to log out?");
        System.out.print("Press Y|y to exit or N|n to stay: ");

        //Create a Scanner object to read from STDIN
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine();
    }
}

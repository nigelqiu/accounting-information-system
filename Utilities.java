package accountinginformationsystem;

import java.util.Scanner;
import java.io.File;

/**
 * Non-instantiating class to hold methods used in other classes.
 *
 * @author 324676840 - Nigel Qiu
 */
public class Utilities {

    private Scanner in = new Scanner(System.in); // Global scanner object for use in methods
    private String tmp; // Global temporary String for use in methods

    /**
     * Utility method to get a boolean from the user.
     *
     * @param mes Message to send to request response
     * @param fail Whether to return false if the user does not enter a valid
     * option
     * @return User input of boolean
     */
    public boolean inputBoolean(String mes, boolean fail) {
        while (true) {
            System.out.print(mes);
            tmp = in.nextLine().toUpperCase();
            if (tmp.equals("Y") || tmp.equals("YES")) { // Check if response is equivalent to true
                return true;
            } else if (fail) { // Check if it should return false as default
                return false;
            } else if (tmp.equals("N") || tmp.equals("NO")) { // Check if response is equivalent to false
                return false;
            } else { // Respond that input is not a boolean
                System.out.println("That is not a boolean.");
            }
        }
    }

    /**
     * Utility method to get an integer from the user.
     *
     * @param mes Message to send to request response
     * @param natural Whether the response needs to be greater than zero
     * @return User input of integer
     */
    public int inputInteger(String mes, boolean natural) {
        int res;
        while (true) {
            System.out.print(mes);
            tmp = in.nextLine();
            try {
                res = Integer.parseInt(tmp);
                if (!natural || res > 0) { // Check if number has to be greater than zero or if number is greater than zero
                    return res;
                } else { // Respond that input is not greater than zero
                    System.out.println("Your number must be greater than zero.");
                }
            } catch (Exception e) { // Respond that the input is not an integer
                System.out.println("That is not a valid integer input.");
            }
        }
    }

    /**
     * Utility method to get a double from the user.
     *
     * @param mes Message to send to request response
     * @param monetary Whether the response needs to be a valid monetary amount
     * @return User input of double
     */
    public double inputDouble(String mes, boolean monetary) {
        double res;
        while (true) {
            System.out.print(mes);
            tmp = in.nextLine();
            try {
                res = Double.parseDouble(tmp);
                // Check if the value must be monetary or 
                // if the input is greater than zero and as up to two digits after the decimal point
                if (!monetary || (res >= 0 && (Double.toString(res).length()
                        - Double.toString(res).indexOf('.') - 1) <= 2)) {
                    return res;
                } else { // Respond that input is not a monetary value
                    System.out.println("Your number must be a monetary value.");
                }
            } catch (Exception e) { // Respond that input is not a double
                System.out.println("That is not a valid double input.");
            }
        }
    }

    /**
     * Utility method to get a String from the user.
     *
     * @param mes Message to send to request response
     * @param options What the acceptable string inputs are (must be all
     * capitalized)
     * @return User input of String
     */
    public String inputString(String mes, String[] options) {
        while (true) {
            System.out.print(mes);
            tmp = in.nextLine().toUpperCase();
            for (String option : options) {
                if (tmp.equals(option)) { // Check if user input is a valid option
                    return option;
                }
            }
            System.out.println("Your response must be one of the given options.");
        }
    }

    /**
     * Utility method to get a File from the user.
     *
     * @param mes Message to send to request response
     * @param format File extension (must include the '.')
     * @param exists Whether the file must already exist
     * @return User input of File
     */
    public File inputFile(String mes, String format, boolean exists) {
        while (true) {
            System.out.print(mes);
            tmp = in.nextLine().toLowerCase();
            try {
                File f = new File("src/saves/" + tmp + format);
                if (!exists || f.exists()) {
                    return f;
                } else {
                    System.out.println("Your file name does not exist.");
                }
            } catch (Exception e) {
                System.out.println("That is not a valid file name.");
            }
        }
    }

}

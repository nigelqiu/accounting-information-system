package accountinginformationsystem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Receives user input and produces system output. Main method here.
 *
 * @author 324676840 - Nigel Qiu
 */
public class Runner {

    private static final Utilities U = new Utilities();
    private static final AccountingInformationSystem AIF
            = new AccountingInformationSystem();
    private static boolean saved = true;

    /**
     * Main method and main menu.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println(" *---------------------------------* ");
        System.out.println(" *| Accounting Information System |* ");
        System.out.println(" *---------------------------------* ");
        System.out.println();

        File auto = null;
        for (File f : new File("src/saves").listFiles()) {
            if (f.getName().startsWith("autosave") && f.getName().endsWith(".csv")) {
                auto = f;
                break;
            }
        }
        if (auto != null && U.inputBoolean("Would you like to load the last "
                + "autosave? (Y/N) ",
                false)) {
            Data.loadData(auto);
        }
        System.out.println();

        boolean run = true;
        while (run) {
            System.out.println("---Main Menu---");
            System.out.println("1. Add Account");
            System.out.println("2. Add Transaction");
            System.out.println("3. Edit Account");
            System.out.println("4. Edit Transaction");
            System.out.println("5. Delete Account");
            System.out.println("6. Delete Transaction");
            System.out.println("7. View Accounts");
            System.out.println("8. View Transaction");
            System.out.println("9. View Journal");
            System.out.println("10. View Ledger");
            System.out.println("11. Save Data");
            System.out.println("12. Load Data");
            System.out.println("13. Quit Program");
            System.out.println();

            int choice = U.inputInteger("Please select an option: ", true);
            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("--Account Creation--");
                    Data.createAccount();
                    saved = false;
                    System.out.println();
                    break;
                case 2:
                    System.out.println("--Transaction Creation--");
                    Data.createTransaction();
                    saved = false;
                    System.out.println();
                    break;
                case 3:
                    if (!Data.accountsIsEmpty()) {
                        System.out.println("--Account Revision--");
                        editAccount();
                        saved = false;
                    } else {
                        System.out.println("There are no accounts to edit.");
                    }
                    System.out.println();
                    break;
                case 4:
                    if (!Data.transactionsIsEmpty()) {
                        System.out.println("--Transaction Revision--");
                        editTransaction();
                        saved = false;
                    } else {
                        System.out.println("There are no transactions to edit.");
                    }
                    System.out.println();
                    break;
                case 5:
                    if (!Data.accountsIsEmpty()) {
                        System.out.println("--Account Deletion--");
                        deleteAccount();
                        saved = false;
                    } else {
                        System.out.println("There are no accounts to delete.");
                    }
                    System.out.println();
                    break;
                case 6:
                    if (!Data.transactionsIsEmpty()) {
                        System.out.println("--Transaction Deletion--");
                        deleteTransaction();
                        saved = false;
                    } else {
                        System.out.println("There are no transactions to delete.");
                    }
                    System.out.println();
                    break;
                case 7:
                    if (!Data.accountsIsEmpty()) {
                        System.out.println("--Accounts Viewing--");
                        printChart(AIF.getAccounts());
                    } else {
                        System.out.println("There are no accounts to view.");
                    }
                    System.out.println();
                    break;
                case 8:
                    if (!Data.transactionsIsEmpty()) {
                        System.out.println("--Transaction Viewing--");
                        viewTransaction();
                    } else {
                        System.out.println("There are no transactions to view.");
                    }
                    System.out.println();
                    break;
                case 9:
                    if (!Data.transactionsIsEmpty()) {
                        System.out.println("--Journal Viewing--");
                        viewJournal();
                    } else {
                        System.out.println("There are no journals to view.");
                    }
                    System.out.println();
                    break;
                case 10:
                    if (!Data.accountsIsEmpty()) {
                        System.out.println("--Ledger Viewing--");
                        viewLedger();
                    } else {
                        System.out.println("There are no ledgers to view.");
                    }
                    System.out.println();
                    break;
                case 11:
                    System.out.println("--Data Writing--");
                    Data.saveData(false);
                    saved = true;
                    System.out.println();
                    break;
                case 12:
                    if (!new File("src/saves").list().equals(new String[0])) {
                        System.out.println("--Data Reading--");
                        loadFile();
                        saved = true;
                    } else {
                        System.out.println("There are no files to load.");
                    }
                    System.out.println();
                    break;
                case 13:
                    System.out.println("--Program Exit--");
                    if (U.inputBoolean("Are you sure you want to quit the "
                            + "program? (Y/N) ", false)) {
                        System.out.println("Autosaving...");
                        Data.saveData(true);
                        run = false;
                        System.out.println("Exiting Accounting Information "
                                + "System...");
                    } else {
                        System.out.println("Cancelling close...");
                    }
                    break;
                default:
                    System.out.println("That is not an option.");
            }
        }
    }

    /**
     * Gets user input for account number to edit account.
     */
    private static void editAccount() {
        while (true) {
            int number = U.inputInteger("Please enter the number of the account "
                    + "you wish to edit: ", true);
            if (Data.accountExists(number)) {
                if (U.inputBoolean("Are you sure you wish to edit that "
                        + "account? (Y/N) ", false)) {
                    Data.editAccount(number);
                } else {
                    System.out.println("Cancelling account revision...");
                }
                break;
            } else {
                System.out.println("An account with the given number does not "
                        + "exist.");
            }
        }
    }

    /**
     * Gets user input for transaction number to edit transaction.
     */
    private static void editTransaction() {
        while (true) {
            int number = U.inputInteger("Please enter the number of the "
                    + "transaction you wish to edit: ", true);
            if (Data.transactionExists(number)) {
                if (U.inputBoolean("Are you sure you wish to edit that "
                        + "transaction? (Y/N) ", false)) {
                    Data.editTransaction(number);
                } else {
                    System.out.println("Cancelling transaction revision...");
                }
                break;
            } else {
                System.out.println("A transaction with the given number does not "
                        + "exist.");
            }
        }
    }

    /**
     * Gets user input for number of account to delete.
     */
    private static void deleteAccount() {
        while (true) {
            int number = U.inputInteger("Please enter the number of the account "
                    + "you wish to delete: ", true);
            if (Data.accountExists(number)) {
                Data.deleteAccount(number);
                break;
            } else {
                System.out.println("An account with the given number does not "
                        + "exist.");
            }
        }
    }

    /**
     * Gets user input for number of transaction to delete.
     */
    private static void deleteTransaction() {
        while (true) {
            int number = U.inputInteger("Please enter the number of the "
                    + "transaction you wish to delete: ", true);
            if (Data.transactionExists(number)) {
                Data.deleteTransaction(number);
                break;
            } else {
                System.out.println("A transaction with the given number does not "
                        + "exist.");
            }
        }
    }

    /**
     * Gets user input for number of transaction to view.
     */
    private static void viewTransaction() {
        while (true) {
            int number = U.inputInteger("Please enter the number of the "
                    + "transaction you wish to view: ", true);
            if (Data.transactionExists(number)) {
                printChart(AIF.getTransaction(number));
                break;
            } else {
                System.out.println("A transaction with the given number does not "
                        + "exist.");
            }
        }
    }

    /**
     * Gets user input for date of journal to view.
     */
    private static void viewJournal() {
        String[] dates = Data.getDates();
        Arrays.sort(dates);
        String[] ordered = new String[dates.length];
        for (int i = 0; i < dates.length; i++) {
            ordered[i] = dates[i].substring(4) + dates[i].substring(2, 4)
                    + dates[i].substring(0, 2);
        }
        Arrays.sort(ordered);
        for (int i = 0; i < ordered.length; i++) {
            ordered[i] = ordered[i].substring(6) + ordered[i].substring(4, 6)
                    + ordered[i].substring(0, 4);
        }

        boolean exit = false;
        Scanner reader = new Scanner(System.in);
        boolean print = true;
        int select = ordered.length - 1;
        while (!exit) {
            if (print) {
                printChart(AIF.getJournal(ordered[select]));
                print = false;
            }
            System.out.println();

            System.out.println("Enter \"next\" ('N') or \"forward\" ('F') to "
                    + "view the journal of the next available date.");
            System.out.println("Enter \"previous\" ('P') or \"back\" ('B') to "
                    + "view the journal of the previous available date.");
            System.out.println("Enter a date in format DDMMYYYY to view the "
                    + "journal on that date.");
            System.out.println("Enter \"dates\" ('D') to view all available dates.");
            System.out.println("Enter \"exit\" ('E') to quit journal viewing mode.");

            System.out.print("Please enter an option: ");
            String input = reader.nextLine().toUpperCase();
            try {
                Integer.parseInt(input);
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                try {
                    // Check if user input matches template format
                    dateFormat.parse(input);
                    if (Arrays.binarySearch(dates, input) >= 0) {
                        for (int i = 0; i < ordered.length; i++) {
                            if (ordered[i].equals(input)) {
                                select = i;
                                print = true;
                            }
                        }
                    } else {
                        System.out.println("There is no journal on that date.");
                    }
                } catch (Exception e) {
                    System.out.println("That is not a valid date.");
                }
            } catch (Exception e) {
                switch (input) {
                    case "NEXT":
                    case "N":
                    case "FORWARD":
                    case "F":
                        if (select != ordered.length - 1) {
                            select++;
                            print = true;
                        } else {
                            System.out.println("There are no further journals "
                                    + "forward.");
                        }
                        break;
                    case "PREVIOUS":
                    case "P":
                    case "BACK":
                    case "B":
                        if (select != 0) {
                            select--;
                            print = true;
                        } else {
                            System.out.println("There are no further journals "
                                    + "backward.");
                        }
                        break;
                    case "DATES":
                    case "D":
                        System.out.println("Dates (DDMMYYYY):");
                        for (String date : ordered) {
                            System.out.println(date);
                        }
                        break;
                    case "EXIT":
                    case "E":
                        exit = true;
                        break;
                    default:
                        System.out.println("That is not an option.");
                }
            }
            if (exit) {
                System.out.println("Exiting...");
            }
            System.out.println();
        }
    }

    /**
     * Gets user input for number of ledger to view.
     */
    private static void viewLedger() {
        while (true) {
            int number = U.inputInteger("Please enter the account number of the "
                    + "ledger you wish to view: ", true);
            if (Data.accountExists(number)) {
                printChart(AIF.getLedger(number));
                break;
            } else {
                System.out.println("A ledger with the given account number does "
                        + "not exist.");
            }
        }
    }

    /**
     * Gets user input for file to load.
     */
    private static void loadFile() {
        boolean run = true;
        if (!saved) {
            run = false;
            System.out.println("You have unsaved changes to your data.");
            if (U.inputBoolean("Are you sure you wish to proceed with data "
                    + "loading? (Y/N) ", false)) {
                run = true;
            } else {
                System.out.println("Cancelling data read...");
            }
        }
        if (run) {
            String autosave = null;
            ArrayList<String> files = new ArrayList<>();
            for (String fileName : new File("src/saves").list()) {
                if (fileName.startsWith("autosave") && fileName.endsWith(".csv")) {
                    autosave = fileName;
                } else if (fileName.endsWith(".csv")) {
                    files.add(fileName);
                }
            }

            if (autosave != null) {
                System.out.printf("The last autosave was on: %s%n",
                        autosave.substring(8, 22));
            }

            if (!files.isEmpty()) {
                System.out.println("Save files (DDMMYYYYHHMMSS):");
                for (String file : files) {
                    System.out.println(file.substring(0, 14));
                }
            }

            Scanner reader = new Scanner(System.in);
            boolean done = false;
            while (!done) {
                System.out.println("Enter \"autosave\" to load the last autosave "
                        + "or enter the save file indentification in the same "
                        + "format listed above (DDMMYYYYHHMMSSS).");
                System.out.print("Please enter your selection: ");
                String input = reader.nextLine().toUpperCase();

                if (input.equals("AUTOSAVE")) {
                    Data.loadData(new File("src/saves/" + autosave));
                    done = true;
                } else {
                    for (String file : files) {
                        if (input.equals(file.substring(0, 14))) {
                            Data.loadData(new File("src/saves/" + file));
                            done = true;
                            break;
                        }
                    }
                    if (!done) {
                        System.out.println("That is not a valid option.");
                    }
                }
            }
        }
    }

    /**
     * Prints the given data in chart form to the console. The data must be
     * structured as stated: All headers in the zero array, followed by the
     * contents of each column in the subsequent arrays. The number of content
     * (subsequent) arrays must match the length of the header array. Any
     * Strings in content arrays that begin with the '_' character will occupy
     * the remaining row width. Content arrays must be rectangular.
     *
     * @param data Content to print to console in chart form
     */
    public static void printChart(String[][] data) {
        System.out.println();
        System.out.println("Print start...");
        System.out.println();

        // Determining maximum String length in each column
        int[] colSize = new int[data.length - 1]; // Maximum number of characters in each column
        int max; // Maximum number of characters in column
        for (int i = 0; i < colSize.length; i++) {
            // Assign max to length of header in column
            max = data[0][i].length() + 1;
            // For each String in the column's array
            for (String s : data[i + 1]) {
                // If the String is longer, set max to that length
                if (!s.equals("") && s.charAt(0) != '_' && s.length() >= max) {
                    max = s.length() + 1;
                }
            }
            // Assign colSize at column index to max
            colSize[i] = max;
        }

        // Determining total width of chart
        int width = 1;
        for (int length : colSize) {
            for (int i = 0; i < length; i++) {
                width++;
            }
            width += 2;
        }
        // Check if full length lines are wider than calculated width
        for (int i = 0; i < data[1].length; i++) {
            int previous = 0;
            for (int j = 1; j < data.length; j++) {
                if (!data[j][i].equals("") && data[j][i].charAt(0) == '_') {
                    if (data[j][i].length() + previous + 3 > width) {
                        width = data[j][i].length() + previous + 3;
                    }
                } else {
                    previous += data[j][i].length() + 3;
                }
            }
        }

        // Printing chart to console
        String line = "";

        // Beginning line
        for (int i = 0;
                i < width;
                i++) {
            line += "-";
        }

        System.out.println(line);

        // Chart header
        line = "| ";
        for (int i = 0;
                i < data[0].length;
                i++) {
            line += data[0][i];
            for (int j = data[0][i].length(); j < colSize[i]; j++) {
                line += " ";
            }
            line += "| ";
        }

        System.out.println(line);

        // Header line
        line = "";
        for (int i = 0;
                i < width;
                i++) {
            line += "-";
        }

        System.out.println(line);

        // Chart content
        int length = -1; // Maximum number of items in any content array
        for (String[] s : data) {
            if (s.length > length) {
                length = s.length;
            }
        }

        for (int i = 0;
                i < length;
                i++) {
            line = "| ";
            for (int j = 1; j < data.length; j++) {
                if (!data[j][i].equals("") && data[j][i].charAt(0) == '_') { // Check if full line indicator
                    line += data[j][i].substring(1);
                    for (int k = line.length(); k < width - 1; k++) {
                        line += " ";
                    }
                    line += "|";
                    if (i != 0) { // Check if full line is not immediately below header
                        String tmp = line;
                        line = "";
                        for (int k = 0; k < width; k++) {
                            line += "-";
                        }
                        System.out.println(line);
                        line = tmp;
                    }
                    if (i != length - 1) { // Check if full line is not last content line
                        System.out.println(line);
                        line = "";
                        for (int k = 0; k < width; k++) {
                            line += "-";
                        }
                    }
                    break;
                } else {
                    line += data[j][i];
                    for (int k = data[j][i].length(); k < colSize[j - 1]; k++) {
                        line += " ";
                    }
                    line += "| ";
                }
            }
            System.out.println(line);
        }

        // Closing line
        line = "";
        for (int i = 0;
                i < width;
                i++) {
            line += "-";
        }
        System.out.println(line);

        System.out.println();
        System.out.println("Print complete.");
    }

}

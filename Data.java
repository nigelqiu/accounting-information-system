package accountinginformationsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Global data variables for accounts and transactions.
 *
 * @author 324676840 - Nigel Qiu
 */
public class Data {

    // Hash map of all dates and the number of occurences of that date
    private static HashMap<String, Integer> dates = new HashMap<>();
    // Hash map of all account names and account numbers
    private static HashMap<String, Integer> accounts = new HashMap<>();
    // Hash map of the number of occurences in transaction entries of each account number
    private static HashMap<Integer, Integer> accountUsage = new HashMap<>();
    // Array list of all transactions
    private static ArrayList<TransactionGeneral> transactions = new ArrayList<>();
    // Scanner object to utilize
    private static final Scanner IN = new Scanner(System.in);
    // Utilities object to utilize
    private static final Utilities U = new Utilities();

    /**
     * Determines whether the given date already exists.
     *
     * @param date Date to check existence of in format DDMMYYYY
     * @return Whether date has at least one occurrence
     */
    public static boolean dateExists(String date) {
        return dates.containsKey(date);
    }

    /**
     * Determines whether the given account name already exists.
     *
     * @param account Account name to check existence of
     * @return Whether the account name exists
     */
    public static boolean accountExists(String account) {
        return accounts.containsKey(account);
    }

    /**
     * Determines whether the given account number already exists.
     *
     * @param account Account number to check existence of (must be 4 digits)
     * @return Whether the account number exists
     */
    public static boolean accountExists(int account) {
        return accounts.containsValue(account);
    }

    /**
     * Determines whether the given transaction number already exists.
     *
     * @param transaction Transaction number to check existence of
     * @return Whether the transaction exists
     */
    public static boolean transactionExists(int transaction) {
        for (TransactionGeneral t : transactions) {
            if (t.getNUMBER() == transaction) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether there are any accounts in accounts hash map.
     *
     * @return Whether there are any accounts
     */
    public static boolean accountsIsEmpty() {
        return accounts.isEmpty();
    }

    /**
     * Determines whether there are any transactions in the transactions array
     * list.
     *
     * @return Whether there are any transactions
     */
    public static boolean transactionsIsEmpty() {
        return transactions.isEmpty();
    }

    /**
     * Adds or increments a date in the dates hash map. If the given date
     * already exists, it's value (number of occurrences) increments by 1.
     * Otherwise, a new map pair is created for the given date.
     *
     * @param date Date to add or increment in format DDMMYYYY
     */
    public static void addDate(String date) {
        if (dateExists(date)) {
            dates.replace(date, dates.get(date) + 1);
        } else {
            dates.put(date, 1);
        }
    }

    /**
     * Removes or decrements a date in dates hash map. If the given date exists,
     * it's value (number of occurrences) decrements by 1. If the value reaches
     * zero, the date is removed from the hash map.
     *
     * @param date Date to remove or decrement in format DDMMYYYY
     */
    public static void removeDate(String date) {
        if (dateExists(date)) {
            dates.replace(date, dates.get(date) - 1);
            if (dates.get(date) == 0) {
                dates.remove(date);
            }
        }
    }

    /**
     * Adds an account with given name and number to the accounts map and to the
     * account usage map if it does not already exist. Otherwise, increments the
     * account occurrence value by 1.
     *
     * @param name Account name to add
     * @param number Account number to add or increment
     */
    public static void addAccount(String name, int number) {
        if (accounts.containsValue(number)) {
            accountUsage.replace(number, accountUsage.get(number) + 1);
        } else {
            accounts.put(name, number);
            accountUsage.put(number, 1);
        }
    }

    /**
     * Creates an account.
     */
    public static void createAccount() {
        // Getting account name from user
        System.out.println("-Account Name-");
        String name;
        while (true) {
            System.out.print("Please enter the account name: ");
            name = IN.nextLine();
            if (!accountExists(name)) { // Check if name already exists
                if (U.inputBoolean("Are you sure you want to use this name? (Y/N) ",
                        false)) {
                    break;
                }
            } else {
                System.out.println("Your account name alreadys exists.");
            }
        }

        // Getting account number from user
        System.out.println("-Account Number-");
        int number;
        while (true) {
            number = U.inputInteger("Please enter the account number: ", true);
            if (number > 1000 && number < 6000) { // Check if number is within bounds for accounting identifiers
                if (!accountExists(number)) {
                    if (U.inputBoolean("Are you sure you want to use this "
                            + "number? (Y/N) ", false)) {
                        break;
                    }
                } else {
                    System.out.println("Your account number already exists.");
                }
            } else {
                System.out.println("Your number exceeds the bounds of "
                        + "accounting standards. Use a number between 1000 and "
                        + "6000.");
            }
        }

        // Putting account in hash maps
        accounts.put(name, number);
        accountUsage.put(number, 0);
        System.out.println("Account successfully created.");
        System.out.printf("Account: %s #%d%n", name, number);
    }

    /**
     * Edits the given account.
     *
     * @param account Account number of account to edit
     */
    public static void editAccount(int account) {
        System.out.println("Number of occurences of this account: "
                + accountUsage.get(account));

        // Getting account name from user
        System.out.println("-Account Name-");
        String name = getAccountName(account);
        System.out.println("Current account name: " + name);
        if (U.inputBoolean("Do you wish to change the account name? (Y/N) ",
                false)) {
            while (true) {
                System.out.print("Please enter the account name: ");
                name = IN.nextLine();
                if (!accountExists(name)) { // Check if name already exists
                    if (U.inputBoolean("Are you sure you want to use this name? "
                            + "(Y/N) ", false)) {
                        break;
                    }
                } else {
                    System.out.println("Your account name alreadys exists. Try "
                            + "a different one.");
                }
            }
        }

        // Getting account number from user
        System.out.println("-Account Number-");
        int number = account;
        System.out.println("Current account number: " + number);
        if (U.inputBoolean("Do you wish to change the account number? (Y/N) ",
                false)) {
            if (accountUsage.get(account) > 0) { // Check if number is used in transaction entries
                System.out.println("This account is already used in transactions.");
                if (U.inputBoolean("Are you sure you want to change it? (Y/N) ", false)) {
                    while (true) {
                        number = U.inputInteger("Please enter the account number: ",
                                true);
                        if (number > 1000 && number < 6000) { // Check if number is within bounds for accounting identifiers
                            if (!accountExists(number)) {
                                if (U.inputBoolean("Are you sure you want to use this "
                                        + "number? (Y/N) ", false)) {
                                    break;
                                }
                            } else {
                                System.out.println("Your account number already exists.");
                            }
                        } else {
                            System.out.println("Your number exceeds the bounds "
                                    + "of accounting standards. Use a number "
                                    + "between 1000 and 6000.");
                        }
                    }

                    // Correcting all transactions that used old number
                    for (TransactionGeneral transaction : transactions) {
                        ArrayList<Entry> entries = transaction.getEntries();
                        for (int i = 0; i < entries.size(); i++) {
                            if (entries.get(i).getREFERENCE() == account) {
                                entries.add(i, new Entry(entries.get(i).getAMOUNT(), number));
                                entries.remove(i + 1);
                            }
                        }
                    }

                    // Updating accounts transactions hash map
                    accountUsage.put(number, accountUsage.get(account));
                    accountUsage.remove(account);
                }
            } else {
                while (true) {
                    number = U.inputInteger("Please enter the account number: ",
                            true);
                    if (number > 1000 && number < 6000) { // Check if number is within bounds for accounting identifiers
                        if (U.inputBoolean("Are you sure you want to use this "
                                + "number? (Y/N) ", false)) {
                            break;
                        }
                    } else {
                        System.out.println("Your number exceeds the bounds of "
                                + "accounting standards. Use a number between "
                                + "1000 and 6000.");
                    }
                }
            }
        }

        // Updating account in hash map
        accounts.remove(getAccountName(account));
        accounts.put(name, number);
        System.out.println("Account successfully edited.");
        System.out.printf("Account: %s %d%n", name, number);
    }

    /**
     * Deleted the given account. Unable to delete accounts that are already
     * used in transactions.
     *
     * @param account Account number of account to delete
     */
    public static void deleteAccount(int account) {
        if (accountUsage.get(account) > 0) {
            System.out.println("That account is already utilized in transaction "
                    + "entries. It can not be deleted. Please edit the account "
                    + "instead.");
        } else {
            if (U.inputBoolean("Are you sure you wish to delete this account? (Y/N) ", true)) {
                accounts.remove(getAccountName(account));
                accountUsage.remove(account);
                System.out.println("Account successfully deleted.");
            } else {
                System.out.println("Account deletion cancelled.");
            }
        }
    }

    /**
     * Creates a transaction.
     */
    public static void createTransaction() {
        // Calculating transaction number
        int number = transactions.get(transactions.size() - 1).getNUMBER() + 1;

        // Getting transaction date from user
        System.out.println("-Transaction Date-");
        String date;
        if (U.inputBoolean("Use today's date for this transaction? (Y/N) ", false)) {
            // Use current date if user replies true
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            Date dte = new Date();
            date = dateFormat.format(dte);
        } else {
            // Else, have the user input their own date
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String tmp;
            while (true) {
                System.out.println("Please enter the date in the format DDMMYYYY: ");
                try {
                    tmp = IN.nextLine();
                    // Check if user input matches template format
                    dateFormat.parse(tmp);
                    break;
                } catch (Exception e) {
                    System.out.println("That input is not valid.");
                }
            }
            date = tmp;
        }
        addDate(date);

        // Getting transaction type from user
        System.out.println("-Transaction Type-");
        boolean done = false;
        while (!done) {
            System.out.println("Transaction types: ");
            System.out.println("1. General");
            System.out.println("2. Sale");
            System.out.println("3. Cash Receipt");
            System.out.println("4. Purchase");
            System.out.println("5. Cash Payment");
            int choice = U.inputInteger("Please enter a transaction type: ", true);
            switch (choice) {
                default:
                    if (!U.inputBoolean("Do you wish to default to a general "
                            + "transaction type? (Y/N) ", true)) {
                        break;
                    }
                case 1:
                    transactions.add(new TransactionGeneral(number, date, IN));
                    done = true;
                    break;
                case 2:
                    transactions.add(new TransactionSale(number, date));
                    done = true;
                    break;
                case 3:
                    transactions.add(new TransactionReceipt(number, date));
                    done = true;
                    break;
                case 4:
                    transactions.add(new TransactionPurchase(number, date));
                    done = true;
                    break;
                case 5:
                    transactions.add(new TransactionPayment(number, date));
                    done = true;
                    break;
            }
        }

        System.out.println("Transaction successfully created.");
    }

    /**
     * Edits the transaction with the given number.
     *
     * @param number Number of transaction to edit
     */
    public static void editTransaction(int number) {
        // Editing transaction date
        System.out.println("-Transaction Date-");
        if (U.inputBoolean("Do you wish to change the transaction date? (Y/N) ",
                true)) {
            // Else, have the user input their own date
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String date;
            while (true) {
                System.out.println("Please enter the date in the format DDMMYYYY: ");
                try {
                    date = IN.nextLine();
                    // Check if user input matches template format
                    dateFormat.parse(date);
                    break;
                } catch (Exception e) {
                    System.out.println("That input is not valid.");
                }
            }
            removeDate(getTransaction(number).getDate());
            getTransaction(number).setDate(date);
            addDate(date);
        }

        getTransaction(number).editTransaction(IN);

        System.out.println("Transaction successfully edited.");
    }

    /**
     * Deletes the transaction with the given number.
     *
     * @param number Number of transaction to delete
     */
    public static void deleteTransaction(int number) {
        if (U.inputBoolean("Are you sure you wish to delete this transaction? "
                + "(Y/N) ", true)) {
            TransactionGeneral transaction = getTransaction(number);
            removeDate(transaction.getDate());
            for (Entry entry : transaction.getEntries()) {
                accountEntry(entry.getREFERENCE(), -1);
            }
            transactions.remove(transaction);
            System.out.println("Transaction successfully deleted.");
        } else {
            System.out.println("Cancelling transaction deletion.");
        }
    }

    /**
     * Increments or decrements the number of occurrences of the given account
     * by the given value.
     *
     * @param account Account to change occurrence number of
     * @param num Amount to change occurrences by (can be positive or negative)
     */
    public static void accountEntry(int account, int num) {
        if ((accountUsage.containsKey(account))) {
            accountUsage.replace(account, accountUsage.get(account) + num);
            if (accountUsage.get(account) < 0) {
                accountUsage.remove(account);
                accounts.remove(getAccountName(account));
            }
        }
    }

    /**
     * Gets the dates that are currently used.
     *
     * @return String array of dates in format DDMMYYYY
     */
    public static String[] getDates() {
        return dates.keySet().toArray(new String[0]);
    }

    /**
     * Gets the hash map of accounts.
     *
     * @return Hash map of accounts
     */
    public static HashMap<String, Integer> getAccounts() {
        return accounts;
    }

    /**
     * Gets the account number for the given account name.
     *
     * @param account Name of account to get number for
     * @return Account number (4 digits)
     */
    public static int getAccountNumber(String account) {
        return accounts.get(account);
    }

    /**
     * Gets the account name for the given account number.
     *
     * @param account Number of account to get name for
     * @return Account name or null if the account number does not exist
     */
    public static String getAccountName(int account) {
        String[] keys = accounts.keySet().toArray(new String[0]);
        for (String key : keys) {
            if (accounts.get(key) == account) {
                return key;
            }
        }
        return null;
    }

    /**
     * Gets the array list of transactions.
     *
     * @return Array list of transactions
     */
    public static ArrayList<TransactionGeneral> getTransactions() {
        return transactions;
    }

    /**
     * Gets the transaction with the specified transaction number.
     *
     * @param number Transaction number to search for
     * @return Transaction with given number or null if not found
     */
    public static TransactionGeneral getTransaction(int number) {
        for (TransactionGeneral transaction : transactions) {
            if (transaction.getNUMBER() == number) {
                return transaction;
            }
        }
        return null;
    }

    /**
     * Writes the accounts and transactions to a file. Save files have the file
     * ending “.csv”. The file name is the date and time that the file was saved
     * in the format “DDMMYYYYHHMMSS” with time in the twenty-four hour format.
     * The except to that rule is the autosave file which has “autosave”
     * concatenated to the front of filename. The data for each file is
     * structured as follows:
     *
     * Number of accounts [For each account] Name,Number,Occurrences
     *
     * [For each transaction] Number,Date (DDMMYYYY),Description Number of
     * entries [For each entry] Amount (to two decimal places),Reference
     *
     * @param autosave Whether this save operation is an autosave
     */
    public static void saveData(boolean autosave) {
        String fileName = "src/saves/";
        if (autosave) {
            fileName += "autosave";
            for (File f : new File("src/saves").listFiles()) {
                if (f.getName().startsWith("autosave")) {
                    f.delete();
                    break;
                }
            }
        }
        fileName += new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
        fileName += ".csv";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));

            // Writing accounts data
            out.write(accounts.size() + "\n");
            for (int value : accounts.values()) {
                out.write(String.format("%s,%d,%d%n", getAccountName(value),
                        value, accountUsage.get(value)));
            }
            out.write("\n");

            // Writing transactions data
            for (int i = 0; i < transactions.size(); i++) {
                out.write(String.format("%d,%s,%s%n",
                        transactions.get(i).getNUMBER(),
                        transactions.get(i).getDate(),
                        transactions.get(i).getDescription()));
                out.write(transactions.get(i).getEntries().size() + "\n");
                for (Entry e : transactions.get(i).getEntries()) {
                    out.write(String.format("%.2f,%d%n", e.getAMOUNT(),
                            e.getREFERENCE()));
                }
                out.write("\n");
            }

            out.close();
            System.out.println("Data successfully writen to file.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Data save write failed.");
        }
    }

    /**
     * Reads the accounts and transactions from a given file. File must exist
     * and must contain data structured in the same format used in the save data
     * method. The data for each file is structured as follows:
     *
     * Number of accounts [For each account] Name,Number,Occurrences
     *
     * [For each transaction] Number,Date (DDMMYYYY),Description Number of
     * entries [For each entry] Amount (to two decimal places),Reference
     *
     * @param f File to read data from
     */
    public static void loadData(File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String[] tmp;
            // Reading accounts data
            int accountsNum = Integer.parseInt(reader.readLine());
            for (int i = 0; i < accountsNum; i++) {
                tmp = reader.readLine().split(",");
                accounts.put(tmp[0], Integer.parseInt(tmp[1]));
                accountUsage.put(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
            }
            reader.readLine();

            String line = reader.readLine();
            // Reading transactions data
            String[] header;
            int entriesNum;
            while (line != null) {
                header = line.split(",");
                entriesNum = Integer.parseInt(reader.readLine());
                ArrayList<Entry> entries = new ArrayList<>();
                for (int i = 0; i < entriesNum; i++) {
                    tmp = reader.readLine().split(",");
                    entries.add(new Entry(Double.parseDouble(tmp[0]),
                            Integer.parseInt(tmp[1])));
                }
                transactions.add(new TransactionGeneral(
                        Integer.parseInt(header[0]), header[1], entries,
                        header[2]));
                addDate(header[1]);
                reader.readLine();
                line = reader.readLine();
            }

            reader.close();
            System.out.println("Data successfully read from file.");
        } catch (Exception e) {
            System.err.println(e);
            System.out.println("Data load read failed.");
        }
    }
}

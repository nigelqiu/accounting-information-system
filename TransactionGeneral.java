package accountinginformationsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to store information for a general transaction.
 *
 * @author 324676840 - Nigel Qiu
 */
public class TransactionGeneral {

    private final int NUMBER; // Number of transaction
    private String date; // Date of transaction (DDMMYYYY)
    protected ArrayList<Entry> entries; // Entries in this transaction
    private String description; // Decription of transaction
    protected final Utilities U = new Utilities(); // Utilities object to utilize

    /**
     * Constructs a general transaction object.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date in format DDMMYYYY
     * @param reader Scanner object to utilize
     */
    public TransactionGeneral(int NUMBER, String date, Scanner reader) {
        this.NUMBER = NUMBER;
        this.date = date;
        entries = new ArrayList<>();
        createTransaction(reader);
    }

    /**
     * Constructs a general transaction object. For use in inherited classes.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date in format DDMMYYYY
     */
    protected TransactionGeneral(int NUMBER, String date) {
        this.NUMBER = NUMBER;
        this.date = date;
        entries = new ArrayList<>();
    }

    /**
     * Constructs a general transaction object. For use during transaction save
     * file loading.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date
     * @param entries Transaction entries (array list of Entry)
     * @param description Transaction description
     */
    TransactionGeneral(int NUMBER, String date, ArrayList<Entry> entries,
            String description) {
        this.NUMBER = NUMBER;
        this.date = date;
        this.entries = entries;
        this.description = description;
    }

    /**
     * Creates a transaction from user responses.
     *
     * @param in Scanner object to utilize
     */
    private void createTransaction(Scanner in) {
        // Gettings entries for this transaction
        System.out.println("-Transaction Entries-");
        while (true) {
            System.out.println("Creating entry " + (entries.size() + 1));

            // Getting entry account number
            int account;
            while (true) {
                account = U.inputInteger("Please enter an account number for "
                        + "this entry: ", true);
                if (Data.accountExists(account)) { // Check if account number exists
                    break;
                } else {
                    if (U.inputBoolean("Would you like to create a new acount? "
                            + "(Y/N) ", true)) {
                        Data.createAccount();
                    }
                }
            }

            // Getting whether this entry is debit or credit
            boolean debit = U.inputBoolean("Is this a debit entry? (Y/N) ",
                    false);

            // Getting entry amount
            double amount = U.inputDouble("Please enter the amount for this "
                    + "entry: ", true);
            if (!debit) {
                amount *= -1;
            }

            // Adding the entry
            entries.add(new Entry(amount, account));
            Data.accountEntry(account, 1);
            System.out.println("Entry successfully added.");
            System.out.println(entries.get(entries.size() - 1));

            // Transaction entry creation exit condition
            if (!entries.isEmpty()) {
                // Calculating difference between debit and credit totals
                double sum = 0;
                for (Entry e : entries) {
                    sum += e.getAMOUNT();
                }
                if (sum == 0) {
                    System.out.println("Debits and credits for this transaction "
                            + "currently balance.");
                    if (U.inputBoolean("Are you finished making the entries "
                            + "for this transaction? (Y/N) ", false)) {
                        break;
                    }
                } else if (sum > 0) {
                    System.out.printf("Debits currently exceed credits by $%.2f%n",
                            sum);
                } else {
                    System.out.printf("Credit currently exceed debits by $%.2f%n",
                            Math.abs(sum));
                }
            }
        }

        // Getting the description for this transaction
        System.out.println("-Transaction Description-");
        System.out.print("Please enter the description for this transaction: ");
        description = in.nextLine();
    }

    /**
     * Edits this transaction's information.
     *
     * @param in Scanner object to utilize
     */
    public void editTransaction(Scanner in) {

        // Editing transaction entries
        System.out.println("-Transaction Entries-");
        if (U.inputBoolean("Would you like to edit the transaction entries? "
                + "(Y/N) ", true)) {
            while (true) {

                // Adding transaction entries
                if (entries.isEmpty() || U.inputBoolean("Would you like to "
                        + "add entries? (Y/N) ", true)) {
                    while (true) {

                        // Getting entry account number
                        int account;
                        while (true) {
                            account = U.inputInteger("Please enter an account "
                                    + "number for this entry: ", true);
                            if (Data.accountExists(account)) { // Check if account number exists
                                break;
                            } else {
                                if (U.inputBoolean("Would you like to create a "
                                        + "new acount? (Y/N) ", true)) {
                                    Data.createAccount();
                                }
                            }
                        }

                        // Getting whether this entry is debit or credit
                        boolean debit = U.inputBoolean("Is this a debit entry? "
                                + "(Y/N) ", false);

                        // Getting entry amount
                        double amount = U.inputDouble("Please enter the amount "
                                + "for this entry: ", true);
                        if (!debit) {
                            amount *= -1;
                        }

                        // Adding the entry
                        entries.add(new Entry(amount, account));
                        Data.accountEntry(account, 1);
                        System.out.println("Entry successfully added.");
                        System.out.println(entries.get(entries.size() - 1));

                        if (U.inputBoolean("Are you finished making the entries "
                                + "for this transaction? (Y/N) ", false)) {
                            break;
                        }
                    }
                }

                // Editing transaction entries
                for (int i = 0; i < entries.size(); i++) {
                    System.out.println(entries.get(i));
                    if (U.inputBoolean("Would you like to delete this entry? "
                            + "(Y/N) ", true)) { // Deleting transaction entry
                        Data.accountEntry(entries.get(i).getREFERENCE(), -1);
                        entries.remove(i);
                        i--;
                    } else if (U.inputBoolean("Would you like to edit this "
                            + "entry? (Y/N) ", true)) { // Editing transaction entry
                        Data.accountEntry(entries.get(i).getREFERENCE(), -1);
                        // Getting entry account number
                        int account;
                        while (true) {
                            account = U.inputInteger("Please enter an account "
                                    + "number for this entry: ", true);
                            if (Data.accountExists(account)) { // Check if account number exists
                                break;
                            } else {
                                if (U.inputBoolean("Would you like to create a "
                                        + "new acount? (Y/N) ", true)) {
                                    Data.createAccount();
                                }
                            }
                        }

                        // Getting whether this entry is debit or credit
                        boolean debit = U.inputBoolean("Is this a debit entry? "
                                + "(Y/N) ", false);

                        // Getting the entry amount
                        double amount = U.inputDouble("Please enter the amount "
                                + "for this entry: ", true);
                        if (!debit) {
                            amount *= -1;
                        }

                        // Editing the entry
                        entries.remove(i);
                        Data.accountEntry(account, 1);
                        entries.add(i, new Entry(amount, account));
                        System.out.println("Entry successfully edited.");
                        System.out.println(i);
                    }
                }

                // Transaction entry revision exit condition
                if (entries.size() > 0) {
                    // Calculating difference between debit and credit totals
                    double sum = 0;
                    for (Entry e : entries) {
                        sum += e.getAMOUNT();
                    }
                    if (sum == 0) {
                        System.out.println("Debits and credits for this "
                                + "transaction currently balance.");
                        if (U.inputBoolean("Are you finished editing the entries "
                                + "for this transaction? (Y/N) ", false)) {
                            break;
                        }
                    } else if (sum > 0) {
                        System.out.printf("Debits currently exceed credits by "
                                + "$%.2f%n", sum);
                    } else {
                        System.out.printf("Credit currently exceed debits by "
                                + "$%.2f%n", Math.abs(sum));
                    }
                }
            }
        }

        // Editing transaction description
        if (U.inputBoolean("Would you like to edit the transaction description? "
                + "(Y/N) ", true)) {
            // Getting transaction description
            System.out.println("-Transaction Description-");
            System.out.print("Please enter the description for this transaction: ");
            description = in.nextLine();
        }
    }

    /**
     * Gets transaction number.
     *
     * @return Transaction number
     */
    public int getNUMBER() {
        return NUMBER;
    }

    /**
     * Gets transaction date.
     *
     * @return Transaction date (DDMMYYYY)
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets transaction entries.
     *
     * @return Array list of transaction entries.
     */
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    /**
     * Gets transaction description.
     *
     * @return Transaction description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets transaction date.
     *
     * @param date Transaction date in format DDMMYYYY
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets transaction description.
     *
     * @param description Transaction description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}

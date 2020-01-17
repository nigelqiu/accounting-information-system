package accountinginformationsystem;

/**
 * Class to store information for a cash receipt transaction.
 *
 * @author 324676840 - Nigel Qiu
 */
public class TransactionReceipt extends TransactionGeneral {

    /**
     * Constructs a cash receipt transaction object.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date in format DDMMYYYY
     */
    public TransactionReceipt(int NUMBER, String date) {
        super(NUMBER, date);
        createTransaction();
    }

    /**
     * Creates a transaction from user responses.
     */
    private void createTransaction() {
        // Getting transaction entries
        System.out.println("-Transaction Entries-");

        // Getting accounts receivable number
        int account;
        while (true) {
            account = U.inputInteger("Please enter the account number of the "
                    + "receipt provider: ", true);
            if (account < 1001 || account > 1999) {
                System.out.println("That account number is not an asset and "
                        + "therefore can not be an accounts receivable.");
            } else if (Data.accountExists(account)) {
                break;
            } else {
                System.out.println("That account does not exist.");
            }
        }

        // Getting receipt amount 
        double amount = U.inputDouble("Please enter the receipt amount: ", true);

        // Creating transaction entries
        entries.add(new Entry(amount, 1001));
        Data.addAccount("Bank", 1001);
        entries.add(new Entry(-amount, account));
        Data.accountEntry(account, 1);

        // Getting transaction description
        setDescription("Cash receipt from account #" + account);
    }
}

package accountinginformationsystem;

/**
 * Class to store information for a cash payment transaction.
 *
 * @author 324676840 - Nigel Qiu
 */
public class TransactionPayment extends TransactionGeneral {
    
    /**
     * Constructs a cash payment transaction object.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date in format DDMMYYYY
     */
    public TransactionPayment(int NUMBER, String date) {
        super(NUMBER, date);
        createTransaction();
    }

    /**
     * Creates a transaction from user responses.
     */
    private void createTransaction() {
        // Getting transaction entries
        System.out.println("-Transaction Entries-");
        
        // Getting payable account number
        int account;
        while (true) {
            account = U.inputInteger("Please enter the account number of the "
                    + "payment receipient: ", true);
            if (account < 2001 || account > 2999) {
                System.out.println("That account number is not a liability and "
                        + "therefore can not be an accounts payable.");
            } else if (Data.accountExists(account)) {
                break;
            } else {
                System.out.println("That account does not exist.");
            }
        }

        // Getting purchase amount 
        double amount = U.inputDouble("Please enter the payment amount: ", true);

        // Creating transaction entries
        entries.add(new Entry(amount, account));
        Data.accountEntry(account, 1);
        entries.add(new Entry(-amount, 1001));
        Data.addAccount("Bank", 1001);

        // Getting transaction description
        setDescription("Payment on account #" + account);
    }
}

package accountinginformationsystem;

/**
 * Class to store information for a purchase transaction.
 *
 * @author 324676840 - Nigel Qiu
 */
public class TransactionPurchase extends TransactionGeneral {

    /**
     * Constructs a purchase transaction object.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date in format DDMMYYYY
     */
    public TransactionPurchase(int NUMBER, String date) {
        super(NUMBER, date);
        createTransaction();
    }

    /**
     * Creates a transaction from user responses.
     */
    private void createTransaction() {
        // Getting transaction entries
        System.out.println("-Transaction Entries-");

        // Getting asset account number
        int asset;
        while (true) {
            asset = U.inputInteger("Please enter the account number of the "
                    + "asset purchased: ", true);
            if (asset < 1001 || asset > 1999) {
                System.out.println("That account number is not an assest.");
            } else if (Data.accountExists(asset)) {
                break;
            } else {
                if (U.inputBoolean("Would you like to create a new acount? (Y/N) ",
                        true)) {
                    Data.createAccount();
                }
            }
        }

        // Getting payable account number
        int account;
        while (true) {
            account = U.inputInteger("Please enter the account number of the "
                    + "asset seller: ", true);
            if (account < 2001 || account > 2999) {
                System.out.println("That account number is not a liability and "
                        + "therefore can not be an accounts payable.");
            } else if (Data.accountExists(account)) {
                break;
            } else {
                if (U.inputBoolean("Would you like to create a new acount? "
                        + "(Y/N) ", true)) {
                    Data.createAccount();
                }
            }
        }

        // Getting purchase amount 
        double amount = U.inputDouble("Please enter the purchase price: ", true);

        // Creating transaction entries
        entries.add(new Entry(amount, asset));
        Data.accountEntry(asset, 1);
        entries.add(new Entry(-amount, account));
        Data.accountEntry(account, 1);

        // Getting transaction description
        setDescription("Purchase on account #" + account);
    }
}

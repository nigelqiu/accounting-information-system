package accountinginformationsystem;

/**
 * Class to store information for a sale transaction.
 *
 * @author 324676840 - Nigel Qiu
 */
public class TransactionSale extends TransactionGeneral {

    /**
     * Constructs a sale transaction object.
     *
     * @param NUMBER Transaction number constant
     * @param date Transaction date in format DDMMYYYY
     */
    public TransactionSale(int NUMBER, String date) {
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
                    + "sale receiptant: ", true);
            if (account < 1001 || account > 1999) {
                System.out.println("That account number is not an assest and "
                        + "therefore can not be an accounts receivable.");
            } else if (Data.accountExists(account)) {
                break;
            } else {
                if (U.inputBoolean("Would you like to create a new acount? "
                        + "(Y/N) ", true)) {
                    Data.createAccount();
                }
            }
        }

        // Getting sale price 
        double sale = U.inputDouble("Please enter the sale price: ", true);
        // Getting cost of goods sold if business is merchandising
        double cost = U.inputDouble("Please enter the cost of goods sold: ", true);

        // Creating transaction entries
        entries.add(new Entry(sale, account));
        Data.accountEntry(account, 1);
        if (cost > 0) {
            entries.add(new Entry(cost, 5001));
            Data.addAccount("Cost of Goods Sold", 5001);
        }
        entries.add(new Entry(-sale, 4001));
        Data.addAccount("Sales", 4001);
        if (cost > 0) {
            entries.add(new Entry(-cost, 1011));
            Data.addAccount("Inventory", 1011);
        }

        // Getting transaction description
        setDescription("Sale to account #" + account);
    }
}

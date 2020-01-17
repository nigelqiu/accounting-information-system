package accountinginformationsystem;

/**
 * Class to store information about transaction entries.
 *
 * @author 324676840 - Nigel Qiu
 */
public class Entry {

    private final double AMOUNT; // Amount for this entry (positive for debit, negative for credit)
    private final int REFERENCE; // Account number (reference) for this entry

    /**
     * Constructs an Entry object.
     *
     * @param AMOUNT Amount of money to change
     * @param REFERENCE Number of account
     */
    public Entry(double AMOUNT, int REFERENCE) {
        this.AMOUNT = AMOUNT;
        this.REFERENCE = REFERENCE;
    }

    /**
     * Gets entry amount.
     *
     * @return Amount
     */
    public double getAMOUNT() {
        return AMOUNT;
    }

    /**
     * Gets entry account number.
     *
     * @return Account number
     */
    public int getREFERENCE() {
        return REFERENCE;
    }

    @Override
    public String toString() {
        String out = "Entry: ";
        if (AMOUNT > 0) {
            out += "DR ";
        } else {
            out += "CR ";
        }
        out += AMOUNT;
        out += " for account #";
        out += REFERENCE;
        return out;
    }

}

package accountinginformationsystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Generates data in a structured format for printing based on requests and
 * information in data class.
 *
 * @author 324676840 - Nigel Qiu
 */
public class AccountingInformationSystem {

    /**
     * Generates a list of accounts for the chart output method.
     *
     * @return String array of accounts data in chart format
     */
    public String[][] getAccounts() {
        String[][] out = new String[3][];

        // Headers
        out[0] = Arrays.asList("Account Number", "Account Name").toArray(new String[0]);

        // Ordering
        Integer[] values = Data.getAccounts().values().toArray(new Integer[0]);
        int[] ordering = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            int minVal = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < values.length; j++) {
                if (values[j] != -1 && values[j] < minVal) {
                    minVal = values[j];
                    minIndex = j;
                }
            }
            values[minIndex] = -1;
            ordering[i] = minIndex;
        }

        // Account numbers
        values = Data.getAccounts().values().toArray(new Integer[0]);
        String[] ordered = new String[values.length];
        for (int i = 0; i < ordered.length; i++) {
            ordered[i] = values[ordering[i]] + "";
        }
        out[1] = ordered;

        // Account names
        String[] keys = (String[]) Data.getAccounts().keySet()
                .toArray(new String[0]);
        ordered = new String[keys.length];
        for (int i = 0; i < ordered.length; i++) {
            ordered[i] = keys[ordering[i]];
        }
        out[2] = ordered;

        System.out.println("Accounts data chart successfully generated.");
        return out;
    }

    /**
     * Generates a list of information for the given transaction for the chart
     * output method.
     *
     * @param number Transaction to generate chart for
     * @return String array of transaction data in chart format
     */
    public String[][] getTransaction(int number) {
        TransactionGeneral transaction = Data.getTransaction(number);
        String[][] out = new String[5][];

        // Headers
        out[0] = Arrays.asList("Account Name", "Debit", "Credit", "Reference")
                .toArray(new String[0]);

        // Transaction number and date
        out[1] = new String[transaction.getEntries().size() + 2];
        Arrays.fill(out[1], "");
        try {
            out[1][0] = String.format("_Transaction #%d on %s",
                    transaction.getNUMBER(), new SimpleDateFormat("EEE, MMMM dd, yyyy")
                    .format(new SimpleDateFormat("ddMMyyyy")
                            .parse(transaction.getDate())));
        } catch (Exception e) {
            System.out.println("Transaction data chart generation failed.");
        }

        // Transaction entries
        // Sorting entries by debits then credits with lower reference numbers first
        ArrayList<Entry> unordered = new ArrayList<>(transaction.getEntries());
        ArrayList<Entry> ordered = new ArrayList<>(unordered.size());
        while (unordered.size() > 0) {
            boolean debitsFin = true;
            Entry selected = null;
            int ref = Integer.MAX_VALUE;
            for (Entry e : unordered) {
                if (e.getAMOUNT() > 0) {
                    if (e.getREFERENCE() < ref) {
                        selected = e;
                        ref = e.getREFERENCE();
                    }
                    debitsFin = false;
                }
            }
            if (!debitsFin) {
                ordered.add(selected);
                unordered.remove(selected);
            } else {
                for (Entry e : unordered) {
                    if (e.getREFERENCE() < ref) {
                        selected = e;
                        ref = e.getREFERENCE();
                    }
                }
                ordered.add(selected);
                unordered.remove(selected);
            }
        }
        // Adding entries to chart data
        out[2] = new String[ordered.size() + 2];
        Arrays.fill(out[2], "");
        out[3] = new String[ordered.size() + 2];
        Arrays.fill(out[3], "");
        out[4] = new String[ordered.size() + 2];
        Arrays.fill(out[4], "");
        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i).getAMOUNT() > 0) {
                out[1][i + 1] = Data.getAccountName(ordered.get(i).getREFERENCE());
                out[2][i + 1] = String.format("$%.2f", ordered.get(i).getAMOUNT());
            } else {
                out[1][i + 1] = "  " + Data.getAccountName(ordered.get(i)
                        .getREFERENCE());
                out[3][i + 1] = String.format("$%.2f", Math.abs(ordered.get(i)
                        .getAMOUNT()));
            }
            out[4][i + 1] = ordered.get(i).getREFERENCE() + "";
        }

        // Transaction description
        out[1][out[1].length - 1] = "_" + transaction.getDescription();

        System.out.println("Transaction data chart successfully generated.");
        return out;
    }

    /**
     * Generates a list of journal transaction information for the given date
     * for the chart output method.
     *
     * @param date Date of journal to generate data for in format DDMMYYYY (must
     * exist in transaction records)
     * @return String array of journal data in chart format
     */
    public String[][] getJournal(String date) {
        String[][] out = new String[5][];

        TransactionGeneral[] transactions = Data.getTransactions()
                .toArray(new TransactionGeneral[0]);
        ArrayList<Integer> ordered = new ArrayList<>();

        // Selecting and ordering transactions
        for (TransactionGeneral transaction : transactions) {
            if (transaction.getDate().equals(date)) {
                ordered.add(transaction.getNUMBER());
            }
        }
        Collections.sort(ordered);

        // Headers
        out[0] = Arrays.asList("Account Name", "Debit", "Credit", "Reference")
                .toArray(new String[0]);

        // Adding entries to chart
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> debits = new ArrayList<>();
        ArrayList<String> credits = new ArrayList<>();
        ArrayList<String> refs = new ArrayList<>();

        for (int i = 0; i < ordered.size(); i++) {
            String[][] tmp = getTransaction(ordered.get(i));
            names.addAll(Arrays.asList(tmp[1]));
            debits.addAll(Arrays.asList(tmp[2]));
            credits.addAll(Arrays.asList(tmp[3]));
            refs.addAll(Arrays.asList(tmp[4]));
            if (i != ordered.size() - 1) {
                names.add("_ ");
                debits.add(" ");
                credits.add(" ");
                refs.add(" ");
            }
        }

        out[1] = names.toArray(new String[0]);
        out[2] = debits.toArray(new String[0]);
        out[3] = credits.toArray(new String[0]);
        out[4] = refs.toArray(new String[0]);

        System.out.println("Journal data chart successfully generated.");
        return out;
    }

    /**
     * Generates a list of information for the given account for the chart
     * output method.
     *
     * @param ledger Account number to generate chart for
     * @return String array of ledger data in chart format
     */
    public String[][] getLedger(int ledger) {
        String[][] out = new String[5][];

        // Headers
        out[0] = Arrays.asList("#", "Description", "Debit", "Credit")
                .toArray(new String[0]);

        // Transaction entries
        TransactionGeneral[] transactions = Data.getTransactions()
                .toArray(new TransactionGeneral[0]);
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<String> debits = new ArrayList<>();
        ArrayList<String> credits = new ArrayList<>();

        numbers.add(String.format("_%s #%d", Data.getAccountName(ledger), ledger));
        descriptions.add("");
        debits.add("");
        credits.add("");

        for (TransactionGeneral t : transactions) {
            for (Entry e : t.getEntries()) {
                if (e.getREFERENCE() == ledger) {
                    numbers.add(t.getNUMBER() + "");
                    descriptions.add(t.getDescription());
                    if (e.getAMOUNT() > 0) {
                        debits.add(e.getAMOUNT() + "");
                        credits.add("");
                    } else {
                        credits.add(Math.abs(e.getAMOUNT()) + "");
                        debits.add("");
                    }
                }
            }
        }

        out[1] = numbers.toArray(new String[0]);
        out[2] = descriptions.toArray(new String[0]);
        out[3] = debits.toArray(new String[0]);
        out[4] = credits.toArray(new String[0]);

        System.out.println("Ledger data chart successfully generated.");
        return out;
    }

}

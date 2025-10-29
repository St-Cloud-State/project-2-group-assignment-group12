import java.util.ArrayList;
import java.util.List;

public class TransactionList {
    private List<Transaction> transactions;

    public TransactionList() {
        this.transactions = new ArrayList<Transaction>();
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return transactions.add(transaction);
    }

    public void displayAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public List<Transaction> getTransactionsByClientId(String clientId) {
        List<Transaction> result = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.getClientId().equals(clientId)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<Transaction>(transactions);
    }
}

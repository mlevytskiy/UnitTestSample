package org.unittestsample.database;

import java.util.List;

/**
 * Created by max on 3/19/15.
 */
public interface DAO {
    void save(Transaction transaction) throws PersistException;
    Transaction getTransactionById(String transaction);
    Transaction removeTransaction(String transactionId);
    List<Transaction> getAllTransactions();
}

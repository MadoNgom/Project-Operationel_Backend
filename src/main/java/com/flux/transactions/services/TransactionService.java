package com.flux.transactions.services;

import com.flux.transactions.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    void deleteTransaction(Long id);
}

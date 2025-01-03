package com.hpms.service;

import com.hpms.model.Transaction;
import com.hpms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Retrieve all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Retrieve a transaction by ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Transaction not found with ID: " + id));
    }

    // Save or update a transaction
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Change the status of a transaction
    public Transaction updateTransactionStatus(Long id, String status) {
        Transaction transaction = getTransactionById(id);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    // Delete a transaction (optional, if needed)
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}

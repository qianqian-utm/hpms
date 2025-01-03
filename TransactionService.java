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
        return transactionRepository.findAll(); // Retrieve all transactions from DB
    }

    // Retrieve a transaction by ID
    public Transaction getTransactionById(Long id) {
        // If not found, throw exception
        return transactionRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Transaction not found with ID: " + id));
    }

    // Save or update a transaction
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction); // Save transaction to DB (insert or update)
    }

    // Change the status of a transaction (This method will be used to update the transaction's status)
    public Transaction updateTransactionStatus(Long id, String status) {
        Transaction transaction = getTransactionById(id); // Fetch the transaction by ID
        transaction.setStatus(status); // Update status
        return transactionRepository.save(transaction); // Save back the updated transaction
    }

    // Delete a transaction by ID (optional)
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id); // Delete the transaction by ID
    }
}

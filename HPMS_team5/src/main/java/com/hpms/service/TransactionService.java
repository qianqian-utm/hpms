package com.hpms.service;

import com.hpms.model.Transaction;
import com.hpms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id);
        if (transaction == null) {
            throw new RuntimeException("Transaction not found with ID: " + id);
        }
        return transaction;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransactionStatus(Long id, String status) {
        Transaction transaction = getTransactionById(id);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
package com.hpms.repository;

import com.hpms.model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Transaction> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Transaction", Transaction.class).list();
        }
    }

    public Transaction findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Transaction.class, id);
        }
    }

    public Transaction save(Transaction transaction) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.saveOrUpdate(transaction);
            tx.commit();
            return transaction;
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, id);
            if (transaction != null) {
                session.delete(transaction);
            }
            tx.commit();
        }
    }
}
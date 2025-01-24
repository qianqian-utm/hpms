package com.hpms.service;

import com.hpms.model.Appointment;
import com.hpms.model.TransactionRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionRecordService {
	private final SessionFactory sessionFactory;
	private final AppointmentService appointmentService;

	@Autowired
	public TransactionRecordService(SessionFactory sessionFactory,AppointmentService appointmentService) {
		this.sessionFactory = sessionFactory;
		this.appointmentService = appointmentService;
	}

	@Transactional
	public TransactionRecord createTransactionRecord(TransactionRecord transactionRecord) {
	    try (Session session = sessionFactory.openSession()) {
	        Transaction transaction = session.beginTransaction();
	        session.save(transactionRecord);
	        transaction.commit();
	        return transactionRecord;
	    }
	}

	@Transactional
	public void updateTransactionRecord(TransactionRecord transactionRecord) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			session.update(transactionRecord);
			transaction.commit();
		}
	}

	@Transactional
	public void deleteTransactionRecord(Integer id) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			try {
				Appointment appointment = appointmentService.findByTransactionRecordId(id);
				appointment.setTransactionRecord(null);
				session.update(appointment);

				TransactionRecord transactionRecord = session.get(TransactionRecord.class, id);
				session.delete(transactionRecord);

				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw e;
			}
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<TransactionRecord> getAllTransactionRecords() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from TransactionRecord").list();
		}
	}

	@Transactional
	public TransactionRecord getTransactionRecordById(Integer id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(TransactionRecord.class, id);
		}
	}
}
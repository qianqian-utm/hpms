package com.hpms.service;

import com.hpms.model.Appointment;
import com.hpms.model.MedicalRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MedicalRecordService {
	private final SessionFactory sessionFactory;
	private final AppointmentService appointmentService;

	@Autowired
	public MedicalRecordService(SessionFactory sessionFactory, AppointmentService appointmentService) {
		this.sessionFactory = sessionFactory;
		this.appointmentService = appointmentService;
	}

	@Transactional
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			session.save(medicalRecord);
			transaction.commit();
			return medicalRecord;
		}
	}

	@Transactional
	public void updateMedicalRecord(MedicalRecord medicalRecord) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			session.update(medicalRecord);
			transaction.commit();
		}
	}

	@Transactional
	public void deleteMedicalRecord(Integer id) {
		try (Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			try {
				Appointment appointment = appointmentService.findByMedicalRecordId(id);
				appointment.setMedicalRecord(null);
				session.update(appointment);

				MedicalRecord medicalRecord = session.get(MedicalRecord.class, id);
				session.delete(medicalRecord);

				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<MedicalRecord> getAllMedicalRecords() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from MedicalRecord").list();
		}
	}

	@Transactional
	public MedicalRecord getMedicalRecordById(Integer id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(MedicalRecord.class, id);
		}
	}
}
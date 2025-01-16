package com.hpms.service;

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

    @Autowired
    public MedicalRecordService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(medicalRecord);
            transaction.commit();
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
    public void deleteMedicalRecord(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            MedicalRecord medicalRecord = session.get(MedicalRecord.class, id);
            if (medicalRecord != null) {
                session.delete(medicalRecord);
            }
            transaction.commit();
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
    public MedicalRecord getMedicalRecordById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MedicalRecord.class, id);
        }
    }
}

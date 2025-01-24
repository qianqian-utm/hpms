package com.hpms.service;

import com.hpms.model.Appointment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void createAppointment(Appointment appointment) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			session.save(appointment);
			transaction.commit();
		}
	}


	@Transactional
	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointmentsByUser(int userId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Appointment WHERE (patient.id = :userId) or (doctor.id = :userId)")
					.setParameter("userId", userId).list();
		}
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointmentsByDoctor(int doctorId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Appointment WHERE doctor.id = :doctorId")
					.setParameter("doctorId", doctorId).list();
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointmentsByPatient(int patientId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Appointment WHERE patient.id = :patientId")
					.setParameter("patientId", patientId).list();
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointmentsByDate(Date appointmentDate) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Appointment WHERE appointmentDate = :appointmentDate")
					.setParameter("appointmentDate", appointmentDate).list();
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Appointment> getAllAppointments() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Appointment order by appointment_date desc, appointment_status desc")
					.list();
		}
	}

	@Transactional
	public void deleteAppointment(int appointmentId) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Appointment appointment = session.get(Appointment.class, appointmentId);
			if (appointment != null) {
				session.delete(appointment);
			}
			transaction.commit();
		}
	}

	@Transactional
	public Appointment getAppointmentById(int appointmentId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Appointment.class, appointmentId);
		}
	}

	@Transactional
	public void updateAppointment(Appointment updatedAppointment) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Appointment existingAppointment = session.get(Appointment.class, updatedAppointment.getId());
			if (existingAppointment != null) {
				existingAppointment.setDoctor(updatedAppointment.getDoctor());
				existingAppointment.setPatient(updatedAppointment.getPatient());
				existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
				existingAppointment.setStartTime(updatedAppointment.getStartTime());
				existingAppointment.setEndTime(updatedAppointment.getEndTime());
				existingAppointment.setAppointmentStatus(updatedAppointment.getAppointmentStatus());
				existingAppointment.setAppointmentType(updatedAppointment.getAppointmentType());
				existingAppointment.setRemarks(updatedAppointment.getRemarks());
				existingAppointment.setMedicalRecord(updatedAppointment.getMedicalRecord());
				existingAppointment.setTransactionRecord(updatedAppointment.getTransactionRecord());
				session.update(existingAppointment);
			}
			transaction.commit();
		}
	}

	@Transactional
	public List<Appointment> searchAppointments(String title, String date, String status) {
		try (Session session = sessionFactory.openSession()) {
			StringBuilder hql = new StringBuilder("FROM Appointment WHERE 1=1");

			if (title != null && !title.isEmpty()) {
				hql.append(" AND title LIKE :title");
			}
			if (date != null && !date.isEmpty()) {
				hql.append(" AND date = :date");
			}
			if (status != null && !status.isEmpty()) {
				hql.append(" AND status = :status");
			}

			Query<Appointment> query = session.createQuery(hql.toString(), Appointment.class);

			if (title != null && !title.isEmpty()) {
				query.setParameter("title", "%" + title + "%");
			}
			if (date != null && !date.isEmpty()) {
				query.setParameter("date", date);
			}
			if (status != null && !status.isEmpty()) {
				query.setParameter("status", status);
			}

			return query.getResultList();
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Appointment> findByPatientId(Integer patientId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(
					"FROM Appointment WHERE patient.id = :patientId ORDER BY appointmentDate DESC, startTime DESC")
					.setParameter("patientId", patientId).list();
		}
	}

	@Transactional
	public Appointment findByMedicalRecordId(Integer medicalRecordId) {
		try (Session session = sessionFactory.openSession()) {
			return (Appointment) session.createQuery("FROM Appointment WHERE medicalRecord.id = :medicalRecordId")
					.setParameter("medicalRecordId", medicalRecordId).uniqueResult();
		}
	}

	@Transactional
	public Appointment findByTransactionRecordId(Integer transactionRecordId) {
		try (Session session = sessionFactory.openSession()) {
			return (Appointment) session.createQuery("FROM Appointment WHERE transactionRecord.id = :transactionRecordId")
					.setParameter("transactionRecordId", transactionRecordId).uniqueResult();
		}
	}
	
	@Transactional 
    public void deleteAppointments(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            String hql = "DELETE FROM Appointment WHERE patient.id = :userId OR doctor.id = :userId";
            session.createQuery(hql)
                .setParameter("userId", userId)
                .executeUpdate();
                
            transaction.commit();
        }
    }
}
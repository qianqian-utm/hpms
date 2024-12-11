package model;

import java.sql.Time;
import java.util.Date;

public class Appointment {
	private User doctor;
	private User patient;
	private Date appointment_date;
	private Time start_time;
	private Time end_time;
	private int appointment_status;
	private int appointment_type;
	private TransactionRecord transaction_record;
	
	// Constructors
	public Appointment(User doctor,User patient,Date appointment_date,Time start_time,Time end_time,int appointment_status,int appointment_type) {
		this.doctor = doctor;
		this.patient = patient;
		this.appointment_date = appointment_date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.appointment_status = appointment_status;
		this.appointment_type = appointment_type;
	}

	// Getters and setters
	
	public User getDoctor() {
		return doctor;
	}
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
	public User getPatient() {
		return patient;
	}
	public void setPatient(User patient) {
		this.patient = patient;
	}
	public Date getAppointment_date() {
		return appointment_date;
	}
	public void setAppointment_date(Date appointment_date) {
		this.appointment_date = appointment_date;
	}
	public Time getStart_time() {
		return start_time;
	}
	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}
	public Time getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}
	public int getAppointment_status() {
		return appointment_status;
	}
	public void setAppointment_status(int appointment_status) {
		this.appointment_status = appointment_status;
	}
	public int getAppointment_type() {
		return appointment_type;
	}
	public void setAppointment_type(int appointment_type) {
		this.appointment_type = appointment_type;
	}
	public TransactionRecord getTransaction_record() {
		return transaction_record;
	}
	public void setTransaction_record(TransactionRecord transaction_record) {
		this.transaction_record = transaction_record;
	}
	
}

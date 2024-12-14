package com.hpms.model;

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
	public Date getAppointmentDate() {
		return appointment_date;
	}
	public void setAppointmentDate(Date appointment_date) {
		this.appointment_date = appointment_date;
	}
	public Time getStartTime() {
		return start_time;
	}
	public void setStartTime(Time start_time) {
		this.start_time = start_time;
	}
	public Time getEndTime() {
		return end_time;
	}
	public void setEndTime(Time end_time) {
		this.end_time = end_time;
	}
	public int getAppointmentStatus() {
		return appointment_status;
	}
	public void setAppointmentStatus(int appointment_status) {
		this.appointment_status = appointment_status;
	}
	public int getAppointmentType() {
		return appointment_type;
	}
	public void setAppointmentType(int appointment_type) {
		this.appointment_type = appointment_type;
	}
	public TransactionRecord getTransactionRecord() {
		return transaction_record;
	}
	public void setTransactionRecord(TransactionRecord transaction_record) {
		this.transaction_record = transaction_record;
	}
	
}

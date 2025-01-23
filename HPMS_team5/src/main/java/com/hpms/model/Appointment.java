package com.hpms.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Appointment {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id") 
	private int id;  
	private User doctor;
	private User patient;
	private Date appointmentDate;
	private Time startTime;
	private Time endTime;
	private int appointmentStatus;
	private int appointmentType;
	private TransactionRecord transactionRecord;
	
	// Constructors
	public Appointment(User doctor,User patient,Date appointment_date,Time start_time,Time end_time,int appointment_status,int appointment_type) {
		this.doctor = doctor;
		this.patient = patient;
		this.appointmentDate = appointment_date;
		this.startTime = start_time;
		this.endTime = end_time;
		this.appointmentStatus = appointment_status;
		this.appointmentType = appointment_type;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
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
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointment_date) {
		this.appointmentDate = appointment_date;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time start_time) {
		this.startTime = start_time;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time end_time) {
		this.endTime = end_time;
	}
	public int getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(int appointment_status) {
		this.appointmentStatus = appointment_status;
	}
	public int getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(int appointment_type) {
		this.appointmentType = appointment_type;
	}
	public TransactionRecord getTransactionRecord() {
		return transactionRecord;
	}
	public void setTransactionRecord(TransactionRecord transaction_record) {
		this.transactionRecord = transaction_record;
	}
	
}

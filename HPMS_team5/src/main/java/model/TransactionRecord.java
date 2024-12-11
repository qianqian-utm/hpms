package model;

public class TransactionRecord {
	private Appointment appointment;
	private double amount;
	
	// Constructors
	public TransactionRecord(Appointment appointment,double amount) {
		this.appointment = appointment;
		this.amount = amount;
	}

	// Getters and setters
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}

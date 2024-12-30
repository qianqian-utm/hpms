package com.hpms.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    private int role;
    private int gender;
// Constructors
	public User() {
		
	}

	public User( String first_name, String last_name, String email, String password, String phone_number, int role, int gender) {
		this.firstName = first_name;
		this.lastName = last_name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phone_number;
		this.role = role;
		this.gender = gender;
	}
	
	public User(String first_name, String last_name, String email, String phone_number, int role, int gender) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.phoneNumber = phone_number;
        this.role = role;
        this.gender = gender;
    }

//	Getters and Setters
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String last_name) {
		this.lastName = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phone_number) {
		this.phoneNumber = phone_number;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
}

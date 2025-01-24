package com.hpms.controller;

import com.hpms.model.Appointment;
import com.hpms.model.TransactionRecord;
import com.hpms.service.AppointmentService;
import com.hpms.service.TransactionRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

@Controller
@RequestMapping("/transactionrecords")
public class TransactionRecordController {
	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private TransactionRecordService transactionRecordService;

	private boolean isAdmin() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@GetMapping("/add/{appointmentId}")
	public String addTransactionRecordForm(@PathVariable Integer appointmentId, Model model) {
		Appointment appointment = appointmentService.getAppointmentById(appointmentId);

		if (appointment == null) {
			return "redirect:/appointments?error=Appointment not found";
		}

		if (!isAdmin()) {
			return "redirect:/appointments?error=Unauthorized access";
		}

		model.addAttribute("appointment", appointment);
		return "transactionRecordForm";
	}

	@PostMapping("/add")
	public String addTransactionRecord(@ModelAttribute TransactionRecord transactionRecord, HttpServletRequest request,
	        Model model) {
	    try {
	        Appointment appointment = appointmentService
	                .getAppointmentById(Integer.parseInt(request.getParameter("appointment.id")));

	        if (!isAdmin()) {
	            return "redirect:/appointments?error=Unauthorized access";
	        }

	        transactionRecord.setDateCreated(new Date());
	        transactionRecord.setAppointment(appointment);
	        
	        TransactionRecord savedRecord = transactionRecordService.createTransactionRecord(transactionRecord);

	        appointment.setTransactionRecord(savedRecord);
	        appointmentService.updateAppointment(appointment);
	        return "redirect:/appointments/view/" + appointment.getId();

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", e.getMessage());
	        return "transactionRecordForm";
	    }
	}

	@GetMapping("/edit/{id}")
	public String editTransactionRecordForm(@PathVariable Integer id, Model model) {
		TransactionRecord transactionRecord = transactionRecordService.getTransactionRecordById(id);

		if (transactionRecord == null) {
			return "redirect:/appointments?error=Transaction record not found";
		}

		Appointment appointment = transactionRecord.getAppointment();

		if (!isAdmin()) {
			return "redirect:/appointments?error=Unauthorized access";
		}

		model.addAttribute("transactionRecord", transactionRecord);
		model.addAttribute("appointment", appointment);
		return "transactionRecordForm";
	}

	@PostMapping("/edit/{id}")
	public String editTransactionRecord(@PathVariable Integer id, @ModelAttribute TransactionRecord transactionRecord,
	        HttpServletRequest request, Model model) {
	    try {
	        TransactionRecord existingRecord = transactionRecordService.getTransactionRecordById(id);
	        
	        if (existingRecord == null) {
	            return "redirect:/appointments?error=Transaction record not found";
	        }
	        
	        Appointment appointment = existingRecord.getAppointment();
	        
	        if (!isAdmin()) {
	            return "redirect:/appointments?error=Unauthorized access";
	        }

	        existingRecord.setAmount(transactionRecord.getAmount());
	        existingRecord.setTransactionStatus(transactionRecord.getTransactionStatus());
	        existingRecord.setRemarks(transactionRecord.getRemarks());
	        
	        transactionRecordService.updateTransactionRecord(existingRecord);

	        return "redirect:/appointments/view/" + appointment.getId();

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", e.getMessage());
	        return "transactionRecordForm";
	    }
	}

	@GetMapping("/delete/{id}")
	public String deleteTransactionRecord(@PathVariable Integer id) {
		if (!isAdmin()) {
			return "redirect:/appointments?error=unauthorized";
		}


	    Appointment appointment = appointmentService.findByTransactionRecordId(id);
	    if (appointment.getTransactionRecord() == null || appointment.getTransactionRecord().getId() != id) {
	        return "redirect:/appointments?error=notfound";
	    }
	    
		transactionRecordService.deleteTransactionRecord(id);
	    return "redirect:/appointments/view/" + appointment.getId() + "?success=Transaction record deleted successfully";
	}
}
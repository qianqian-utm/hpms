package com.hpms.controller;

import com.hpms.model.Appointment;
import com.hpms.model.MedicalRecord;
import com.hpms.model.User;
import com.hpms.service.AppointmentService;
import com.hpms.service.IUserService;
import com.hpms.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/medicalrecords")
public class MedicalRecordController {
	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private IUserService userService;

	private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUserByEmail(auth.getName());
	}

	private boolean isAdmin() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@GetMapping("/add/{appointmentId}")
	public String addMedicalRecordForm(@PathVariable Integer appointmentId, Model model) {
		User currentUser = getCurrentUser();
		Appointment appointment = appointmentService.getAppointmentById(appointmentId);

		if (appointment == null) {
			return "redirect:/appointments?error=Appointment not found";
		}

		if (!isAdmin() && currentUser.getId() != appointment.getDoctor().getId()) {
			return "redirect:/appointments?error=Unauthorized access";
		}

		model.addAttribute("appointment", appointment);
		return "medicalRecordForm";
	}

	@PostMapping("/add")
	public String addMedicalRecord(@ModelAttribute MedicalRecord medicalRecord, HttpServletRequest request,
			Model model) {
		try {
			User currentUser = getCurrentUser();
			Appointment appointment = appointmentService
					.getAppointmentById(Integer.parseInt(request.getParameter("appointment.id")));

			if (!isAdmin() && currentUser.getId() != appointment.getDoctor().getId()) {
				return "redirect:/appointments?error=Unauthorized access";
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date recordDate = dateFormat.parse(request.getParameter("date"));
			medicalRecord.setDate(recordDate);

			MedicalRecord savedRecord = medicalRecordService.createMedicalRecord(medicalRecord);

			appointment.setMedicalRecord(savedRecord);
			appointmentService.updateAppointment(appointment);

			return "redirect:/appointments/view/" + appointment.getId();

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "medicalRecordForm";
		}
	}

	@GetMapping("/edit/{id}")
	public String editMedicalRecordForm(@PathVariable Integer id, Model model) {
		User currentUser = getCurrentUser();
		MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);

		if (medicalRecord == null) {
			return "redirect:/appointments?error=Medical record not found";
		}

		Appointment appointment = medicalRecord.getAppointment();

		if (!isAdmin() && currentUser.getId() != appointment.getDoctor().getId()) {
			return "redirect:/appointments?error=Unauthorized access";
		}

		model.addAttribute("medicalRecord", medicalRecord);
		model.addAttribute("appointment", appointment);
		return "medicalRecordForm";
	}

	@PostMapping("/edit/{id}")
	public String editMedicalRecord(@PathVariable Integer id, @ModelAttribute MedicalRecord medicalRecord,
			HttpServletRequest request, Model model) {
		try {
			User currentUser = getCurrentUser();
			MedicalRecord existingRecord = medicalRecordService.getMedicalRecordById(id);
			Appointment appointment = existingRecord.getAppointment();

			if (!isAdmin() && currentUser.getId() != appointment.getDoctor().getId()) {
				return "redirect:/appointments?error=Unauthorized access";
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date recordDate = dateFormat.parse(request.getParameter("date"));

			medicalRecord.setId(id);
			medicalRecord.setDate(recordDate);
			medicalRecord.setAppointment(appointment);
			appointment.setMedicalRecord(medicalRecord);

			medicalRecordService.updateMedicalRecord(medicalRecord);
			appointmentService.updateAppointment(appointment);

			return "redirect:/appointments/view/" + appointment.getId();

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "medicalRecordForm";
		}
	}
}
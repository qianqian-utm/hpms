package com.hpms.controller;

import com.hpms.model.Appointment;
import com.hpms.model.User;
import com.hpms.service.AppointmentService;
import com.hpms.service.MedicalRecordService;
import com.hpms.service.TransactionRecordService;
import com.hpms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private TransactionRecordService transactionRecordService;

	@Autowired
	private IUserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Time.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				try {
					setValue(Time.valueOf(text + ":00"));
				} catch (IllegalArgumentException e) {
					setValue(null);
				}
			}
		});
	}

	private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUserByEmail(auth.getName());
	}

	private boolean isAdmin() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@GetMapping("")
	public String listAppointments(Model model) {
		User currentUser = getCurrentUser();
		List<Appointment> appointments;

		if (isAdmin()) {
			appointments = appointmentService.getAllAppointments();
		} else {
			appointments = appointmentService.getAppointmentsByPatient(currentUser.getId());
		}

		model.addAttribute("doctors", userService.getDoctors());
		model.addAttribute("patients", userService.getPatients());
		model.addAttribute("appointments", appointments);
		model.addAttribute("currentUser", currentUser);
		return "appointment_listing";
	}
	
	@GetMapping("/add")
	public String addAppointmentForm(Model model) {
		User currentUser = getCurrentUser();
		model.addAttribute("doctors", userService.getDoctors());

		if (isAdmin()) {
			model.addAttribute("patients", userService.getPatients());
		} else {
			model.addAttribute("patient", currentUser);
		}

		return "addAppointment";
	}

	@PostMapping("/add")
	public String addAppointment(@ModelAttribute("appointment") Appointment appointment, HttpServletRequest request,
			Model model) {
		try {
			User currentUser = getCurrentUser();

			// If not admin, force the patient to be the current user
			if (!isAdmin()) {
				appointment.setPatient(currentUser);
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			Date appointmentDate = dateFormat.parse(request.getParameter("appointmentDate"));
			appointment.setAppointmentDate(appointmentDate);

			Time startTime = new Time(timeFormat.parse(request.getParameter("startTime")).getTime());
			Time endTime = new Time(timeFormat.parse(request.getParameter("endTime")).getTime());
			appointment.setStartTime(startTime);
			appointment.setEndTime(endTime);

			if (request.getParameter("appointmentStatus") == null) {
				appointment.setAppointmentStatus(1);
			}

			appointmentService.createAppointment(appointment);
			return "redirect:/appointments";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "addAppointment";
		}
	}

	@GetMapping("/edit/{id}")
	public String editAppointmentForm(@PathVariable Integer id, Model model) {
		User currentUser = getCurrentUser();

		Appointment appointment = appointmentService.getAppointmentById(id);
		if (appointment == null) {
			return "redirect:/appointments?error=Appointment not found";
		}

		if (!isAdmin() && appointment.getPatient().getId() != currentUser.getId()) {
			return "redirect:/appointments?error=Unauthorized access";
		}

		model.addAttribute("appointment", appointment);
		model.addAttribute("doctors", userService.getDoctors());

		if (isAdmin()) {
			model.addAttribute("patients", userService.getPatients());
		} else {
			model.addAttribute("patient", currentUser);
		}

		return "editAppointment";
	}

	@PostMapping("/edit/{id}")
	public String editAppointment(@PathVariable Integer id, @ModelAttribute("appointment") Appointment appointment,
			HttpServletRequest request, Model model) {
		try {
			User currentUser = getCurrentUser();
			Appointment existingAppointment = appointmentService.getAppointmentById(id);

			// Security check
			if (!isAdmin() && existingAppointment.getPatient().getId() != (currentUser.getId())) {
				return "redirect:/appointments?error=Unauthorized access";
			}

			// If not admin, ensure patient remains unchanged
			if (!isAdmin()) {
				appointment.setPatient(currentUser);
			}

			// Parse and set dates/times
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			Date appointmentDate = dateFormat.parse(request.getParameter("appointmentDate"));
			Time startTime = new Time(timeFormat.parse(request.getParameter("startTime")).getTime());
			Time endTime = new Time(timeFormat.parse(request.getParameter("endTime")).getTime());

			appointment.setAppointmentDate(appointmentDate);
			appointment.setStartTime(startTime);
			appointment.setEndTime(endTime);
			appointment.setId(id);

			appointmentService.updateAppointment(appointment);
			return "redirect:/appointments";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "editAppointment";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteAppointment(@PathVariable Integer id) {
		User currentUser = getCurrentUser();
		Appointment appointment = appointmentService.getAppointmentById(id);

		if (!isAdmin() && appointment.getPatient().getId() != currentUser.getId()) {
			return "redirect:/appointments?error=unauthorized";
		}

		// Delete linked records first
		if (appointment.getMedicalRecord() != null) {
			medicalRecordService.deleteMedicalRecord(appointment.getMedicalRecord().getId());
		}
		if (appointment.getTransactionRecord() != null) {
			transactionRecordService.deleteTransactionRecord(appointment.getTransactionRecord().getId());
		}

		appointmentService.deleteAppointment(id);
		return "redirect:/appointments";
	}

	@GetMapping("/view/{id}")
	public String viewAppointment(@PathVariable Integer id, 
	                           @RequestParam(required = false) String error,
	                           @RequestParam(required = false) String success,
	                           Model model) {
	   User currentUser = getCurrentUser();
	   Appointment appointment = appointmentService.getAppointmentById(id);

	   if (appointment == null) {
	       return "redirect:/appointments?error=Appointment not found";
	   }

	   if (!isAdmin() && currentUser.getId() != appointment.getPatient().getId()) {
	       return "redirect:/appointments?error=Unauthorized access";
	   }

	   model.addAttribute("appointment", appointment);
	   model.addAttribute("medicalRecord", appointment.getMedicalRecord());
	   model.addAttribute("transactionRecord", appointment.getTransactionRecord());
	   model.addAttribute("isAdminUser", isAdmin());
	   model.addAttribute("error", error);
	   model.addAttribute("success", success);

	   return "appointmentDetail";
	}

	@GetMapping("/cancel/{id}")
	public String cancelAppointment(@PathVariable Integer id) {
		User currentUser = getCurrentUser();
		Appointment appointment = appointmentService.getAppointmentById(id);

		if (appointment == null) {
			return "redirect:/appointments?error=Appointment not found";
		}

		if (appointment.getPatient().getId() != currentUser.getId()) {
			return "redirect:/appointments?error=Unauthorized access";
		}

		if (appointment.getAppointmentStatus() == 3 || appointment.getAppointmentStatus() == 4) {
			return "redirect:/appointments?error=Cannot cancel this appointment";
		}

		appointment.setAppointmentStatus(3);
		appointmentService.updateAppointment(appointment);

		return "redirect:/appointments?success=Appointment cancelled successfully";
	}
}
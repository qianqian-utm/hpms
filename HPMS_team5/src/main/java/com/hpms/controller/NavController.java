package com.hpms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hpms.model.User;
import com.hpms.service.UserService;

@Controller
public class NavController {
	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public ModelAndView dashboard() {
		return new ModelAndView("dashboard");
	}

	@GetMapping("/transaction")
	public ModelAndView transactionPage() {
		return new ModelAndView("transaction_record");
	}

	@GetMapping("/manageSchedule")
	public ModelAndView manageSchedule() {
		return new ModelAndView("ManageSchedule");
	}

	@GetMapping("/userlisting")
	public ModelAndView userListing(@ModelAttribute("successMessage") String successMessage, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (isUserLoggedIn(session)) {
			// Retrieve user list from service or session
			List<User> users = userService.getUserList();

			// Add users to the model
			ModelAndView modelAndView = new ModelAndView("layout");
			modelAndView.addObject("currentPage", "userlisting");
			modelAndView.addObject("users", users);
			modelAndView.addObject("successMessage", successMessage);
			request.setAttribute("title", "User Listing");
			request.setAttribute("content", "user_listing"); 
			return modelAndView;
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	private boolean isUserLoggedIn(HttpSession session) {
		if (session.getAttribute("loggedUser")!=null) {
			return true;
		}
		return false;
	}

	@GetMapping("/appointmentlisting")
	public String appointmentListing(HttpServletRequest request) {
		return "appointment_listing";
	}

}
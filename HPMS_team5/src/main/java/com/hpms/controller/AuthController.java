package com.hpms.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hpms.model.User;
import com.hpms.service.UserService;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ModelAndView checkUserStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();

		// Check if loggedUser attribute exists in the session
		if (session.getAttribute("loggedUser") != null) {
			// User is logged in, redirect to dashboard
			return new ModelAndView("redirect:/dashboard");
		} else {
			// User is not logged in, redirect to login page
			return new ModelAndView("redirect:/login");
		}
	}


	//	by default, requestMapping = get request
	// we can specify via GetMapping / PostMapping
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("register");
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard() {
		return new ModelAndView("dashboard");
	}

	@GetMapping("/userlisting")
	public ModelAndView userListing(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (isUserLoggedIn(session)) {
			// Retrieve user list from service or session
			ArrayList<User> users = userService.getUserList();

			// Add users to the model
			ModelAndView modelAndView = new ModelAndView("user_listing");
			modelAndView.addObject("users", users);
			return modelAndView;
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@GetMapping("/add_user")
	public ModelAndView addUserForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (isUserLoggedIn(session)) {
			//Check if user is admin role
			User loggedInUser = (User) session.getAttribute("loggedUser");
			if(loggedInUser.getRole()!=1) {
				return new ModelAndView("redirect:/login");
			} else {
				return new ModelAndView("add_user_form");
			}
		}
		return new ModelAndView("redirect:/login");
	}


	@GetMapping("/editaccount")
	public ModelAndView editAccountForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (isUserLoggedIn(session)) {
			//Check if user is admin role
			User loggedInUser = (User) session.getAttribute("loggedUser");

			ModelAndView modelAndView = new ModelAndView("edit_account_form");
			modelAndView.addObject("loggedInUser", loggedInUser);
			return modelAndView;
		}
		return new ModelAndView("redirect:/login");
	}

	private boolean isUserLoggedIn(HttpSession session) {
		if (session.getAttribute("loggedUser")!=null) {
			return true;
		}
		return false;
	}
}
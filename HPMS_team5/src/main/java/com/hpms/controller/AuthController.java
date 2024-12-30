package com.hpms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.model.User;
import com.hpms.service.UserService;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/home")
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


	@GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByEmailAndPassword(email, password);
            if (user != null) {
                session.setAttribute("loggedUser", user);
                return user.getRole() == 1 ? "redirect:/userlisting" : "redirect:/appointmentlisting";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
        }
        return "redirect:/login";
    }

	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("register");
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user, 
	                           @RequestParam String confirmPassword,
	                           RedirectAttributes redirectAttributes) {
	    if (!user.getPassword().equals(confirmPassword)) {
	        redirectAttributes.addFlashAttribute("error", "Passwords do not match");
	        return "redirect:/register";
	    }
	    
	    user.setRole(2); // Set default role to 2 (patient)
	    
	    try {
	        userService.addUser(user);
	        redirectAttributes.addFlashAttribute("message", "Registration successful");
	        return "redirect:/login";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/register";
	    }
	}

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
			ModelAndView modelAndView = new ModelAndView("user_listing");
			modelAndView.addObject("users", users);
			modelAndView.addObject("successMessage", successMessage);
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

	@PostMapping("/add_user")
	public String addUser(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
	    User loggedInUser = (User) session.getAttribute("loggedUser");
	    if (loggedInUser == null || loggedInUser.getRole() != 1) {
	        return "redirect:/login";
	    }

	    String phoneNumber = user.getPhoneNumber();
	    if (phoneNumber == null || phoneNumber.length() < 4) {
	        redirectAttributes.addFlashAttribute("error", "Invalid phone number");
	        return "redirect:/add_user";
	    }
	    user.setPassword(phoneNumber.substring(phoneNumber.length() - 4));

	    try {
	        userService.addUser(user);
	        redirectAttributes.addFlashAttribute("successMessage", "User added successfully");
	        return "redirect:/userlisting";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/add_user";
	    }
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
	
	@GetMapping("/appointmentlisting")
	public String appointmentListing(HttpServletRequest request) {
		return "appointment_listing";
	}

}
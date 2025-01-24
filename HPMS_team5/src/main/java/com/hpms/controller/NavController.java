package com.hpms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.model.User;
import com.hpms.service.IUserService;

@Controller
public class NavController {
	@Autowired
	private IUserService userService;

	@GetMapping("/transaction")
	public ModelAndView transactionPage() {
		return new ModelAndView("transaction_record");
	}

	@GetMapping("/manageSchedule")
	public ModelAndView manageSchedule() {
		return new ModelAndView("ManageSchedule");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/userlisting")
	public String userListing(@ModelAttribute("successMessage") String successMessage, Model model) {
	    List<User> users = userService.getUserList();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = userService.getUserByEmail(auth.getName());
	    
	    model.addAttribute("users", users);
	    model.addAttribute("currentUser", currentUser);
	    model.addAttribute("successMessage", successMessage);
	    return "user_listing";
	}

	@GetMapping("/editaccount")
	public String editAccountForm(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userEmail = auth.getName(); // Gets the email
	    
	    User user = userService.getUserByEmail(userEmail);
	    model.addAttribute("loggedInUser", user);
		return "editaccount";
	}

	@PostMapping("/editaccount")
	public ModelAndView updateAccount(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userEmail = auth.getName();
	    User currentUser = userService.getUserByEmail(userEmail);

	    if (!userEmail.equals(user.getEmail())) {
	        User existingUser = userService.getUserByEmail(user.getEmail());
	        if (existingUser != null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Email already exists");
	            return new ModelAndView("redirect:/editaccount");
	        }
	    }

	    user.setId(currentUser.getId());
	    user.setPassword(currentUser.getPassword());
	    user.setRole(currentUser.getRole());
	    userService.updateUser(user);

	    redirectAttributes.addFlashAttribute("successMessage", "Account updated successfully");
	    return new ModelAndView("redirect:/editaccount");
	}
}
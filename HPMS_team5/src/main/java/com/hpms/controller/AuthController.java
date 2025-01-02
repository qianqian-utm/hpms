package com.hpms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/login")
	public String login(Model model) {
	    return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam String email,
	                       @RequestParam String password,
	                       HttpSession session,
	                       Model model,
	                       RedirectAttributes redirectAttributes) {
	    try {
	        User user = userService.getUserByEmailAndPassword(email, password);
	        if (user != null) {
	            session.setAttribute("loggedUser", user);
	            return user.getRole() == 1 ? "redirect:/userlisting" : "redirect:/appointmentlisting";
	        }
	        redirectAttributes.addFlashAttribute("error", "Invalid email or password");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "An error occurred");
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
	    // Check if email already exists
	    if (userService.getUserByEmail(user.getEmail())) {
	        redirectAttributes.addFlashAttribute("error", "Email already registered");
	        return "redirect:/register";
	    }

	    // Validate password match
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
	
	@GetMapping("/signout")
	public String signOut(HttpSession session) {
	    session.invalidate();
	    return "redirect:/login";
	}

}
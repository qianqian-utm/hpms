package com.hpms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

	private final UserService userService;
	private static final String REDIRECT_LOGIN = "redirect:/login";
	private static final String ERROR_ATTRIBUTE = "error";

	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public ModelAndView checkUserStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute("loggedUser") != null) {
			return new ModelAndView("redirect:/dashboard");
		} else {
			return new ModelAndView(REDIRECT_LOGIN);
		}
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@RequestParam String email, @RequestParam String password,
			HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUserByEmailAndPassword(email, password);
			if (user != null) {
				session.setAttribute("loggedUser", user);
				return user.getRole() == 1 ? "redirect:/userlisting" : "redirect:/appointmentlisting";
			}
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "Invalid email or password");
			return REDIRECT_LOGIN;
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "An error occurred");
			return REDIRECT_LOGIN;
		}
	}

	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("register");
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user, @RequestParam String confirmPassword,
			RedirectAttributes redirectAttributes) {
		User existingUser = userService.getUserByEmail(user.getEmail());
		if (existingUser != null) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "Email already registered");
			return "redirect:/register";
		}

		if (!user.getPassword().equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "Passwords do not match");
			return "redirect:/register";
		}

		user.setRole(2); // Set default role to 2 (patient)

		try {
			userService.addUser(user);
			redirectAttributes.addFlashAttribute("message", "Registration successful");
			return "redirect:/login";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, e.getMessage());
			return "redirect:/register";
		}
	}

	@GetMapping("/signout")
	public String signOut(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
package com.hpms.controller;

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
import com.hpms.dto.UserDTO; // Ensure this exists in com.hpms.dto
import com.hpms.service.UserService;

@Controller
public class AuthController {

	private final UserService userService;
	private static final String REDIRECT_LOGIN = "redirect:/login";
	private static final String REDIRECT_REGISTER = "redirect:/register";
	private static final String ERROR_ATTRIBUTE = "error";

	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public ModelAndView checkUserStatus(HttpSession session) {
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
			HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUserByEmailAndPassword(email, password);
			if (user != null) {
				session.setAttribute("loggedUser", user);
				return user.getRole() == 1 ? "redirect:/userlisting" : "redirect:/appointmentlisting";
			}
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "Invalid email or password");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "An error occurred");
		}
		return REDIRECT_LOGIN;
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute UserDTO userDTO, @RequestParam String confirmPassword,
			RedirectAttributes redirectAttributes) {
		if (userService.findUserByEmail(userDTO.getEmail()) != null) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "Email already exists");
			return REDIRECT_REGISTER;
		}

		if (!userDTO.getPassword().equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, "Passwords do not match");
			return REDIRECT_REGISTER;
		}

		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRole(2); // Default role as Patient

		userService.addUser(user);
		redirectAttributes.addFlashAttribute("message", "Registration successful");
		return REDIRECT_LOGIN;
	}
}

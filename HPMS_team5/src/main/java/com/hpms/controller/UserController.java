package com.hpms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.model.User;
import com.hpms.service.UserService;

@Controller
public class UserController {
	private final UserService userService;
	private static final String ERROR_MESSAGE = "errorMessage";

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/add_user")
	public ModelAndView addUserForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (isUserLoggedIn(session)) {
			User loggedInUser = (User) session.getAttribute("loggedUser");
			if (loggedInUser.getRole() != 1) {
				return new ModelAndView("redirect:/login");
			}

			ModelAndView mv = new ModelAndView("layout");
			mv.addObject("currentPage", "userlisting");
			request.setAttribute("title", "Add User");
			request.setAttribute("content", "add_user_form");
			return mv;
		}
		return new ModelAndView("redirect:/login");
	}

	@PostMapping("/add_user")
	public ModelAndView addUser(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
		if (!isUserLoggedIn(session)) {
			return new ModelAndView("redirect:/login");
		}

		ModelAndView mv = new ModelAndView("layout");
		mv.addObject("currentPage", "userlisting");
		mv.addObject("title", "Edit User");
		mv.addObject("content", "edit_user_form");
		User user = userService.getUserById(id);
		mv.addObject("user", user);

		String phoneNumber = user.getPhoneNumber();
		if (phoneNumber == null || phoneNumber.length() < 4) {
			mv.addObject(ERROR_MESSAGE, "Invalid phone number");
			return mv;
		}

		user.setPassword(phoneNumber.substring(phoneNumber.length() - 4));

		if (userService.getUserByEmail(user.getEmail())) {
			mv.addObject(ERROR_MESSAGE, "Email already exists");
			return mv;
		}

		try {
			userService.addUser(user);
			redirectAttributes.addFlashAttribute("successMessage", "User added successfully");
			return new ModelAndView("redirect:/userlisting");
		} catch (Exception e) {
			mv.addObject(ERROR_MESSAGE, e.getMessage());
			return mv;
		}
	}

	@GetMapping("/edit_user/{id}")
	public ModelAndView editUserForm(@PathVariable Long id, HttpSession session, HttpServletRequest request) {
		User loggedInUser = (User) session.getAttribute("loggedUser");
		if (loggedInUser == null || loggedInUser.getRole() != 1) {
			return new ModelAndView("redirect:/login");
		}

		User user = userService.getUserById(id);
		if (user == null) {
			return new ModelAndView("redirect:/userlisting");
		}

		ModelAndView mv = new ModelAndView("layout");
		mv.addObject("currentPage", "userlisting");
		mv.addObject("user", user);
		request.setAttribute("title", "Edit User");
		request.setAttribute("content", "edit_user_form");
		return mv;
	}

	@PostMapping("/edit_user/{id}")
	public ModelAndView updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO, HttpSession session) {
		if (!isUserLoggedIn(session) || ((User) session.getAttribute("loggedUser")).getRole() != 1) {
			return new ModelAndView("redirect:/login");
		}

		ModelAndView mv = new ModelAndView("layout");
		mv.addObject("currentPage", "userlisting");
		mv.addObject("title", "Edit User");
		mv.addObject("content", "edit_user_form");
		mv.addObject("user", user);

		String phoneNumber = userDTO.getPhoneNumber();
		if (phoneNumber == null || phoneNumber.length() < 4) {
			mv.addObject("errorMessage", "Invalid phone number");
			return mv;
		}

		if (userService.getUserByEmail(userDTO.getEmail())) {
			mv.addObject("errorMessage", "Email already exists");
			return mv;
		}

		try {
			User user = new User();
			user.setId(id);
			user.setName(userDTO.getName());
			user.setEmail(userDTO.getEmail());
			user.setPhoneNumber(userDTO.getPhoneNumber());
			userService.updateUser(user);
			return new ModelAndView("redirect:/userlisting");
		} catch (Exception e) {
			mv.addObject("errorMessage", e.getMessage());
			return mv;
		}
	}

	@PostMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable Long id, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User loggedInUser = (User) session.getAttribute("loggedUser");
		if (loggedInUser == null || loggedInUser.getRole() != 1) {
			return "redirect:/login";
		}

		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/userlisting";
	}

	private boolean isUserLoggedIn(HttpSession session) {
		return session.getAttribute("loggedUser") != null;
	}
}
